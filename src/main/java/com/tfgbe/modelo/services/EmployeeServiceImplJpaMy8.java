package com.tfgbe.modelo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.AlreadyExistsException;
import com.tfgbe.exceptions.BadRequestException;
import com.tfgbe.exceptions.DeleteRestrictionException;
import com.tfgbe.exceptions.ForbiddenException;
import com.tfgbe.exceptions.NoRoleException;
import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.exceptions.UnauthorizedException;
import com.tfgbe.mapper.EmployeeMapper;
import com.tfgbe.modelo.dto.CreateEmployeeDto;
import com.tfgbe.modelo.dto.EmployeeResponseDto;
import com.tfgbe.modelo.dto.LoginResponseDto;
import com.tfgbe.modelo.dto.UpdateEmployeeDto;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.Role;
import com.tfgbe.modelo.entities.Shift;
import com.tfgbe.modelo.repository.EmployeeRepository;
import com.tfgbe.modelo.repository.RoleRepository;
import com.tfgbe.modelo.repository.ShiftRepository;
import com.tfgbe.security.JwtUtil;
import com.tfgbe.util.RoleUtils;
import com.tfgbe.util.RolesEnum;
import com.tfgbe.modelo.dto.ChangePasswordDto;

@Service
public class EmployeeServiceImplJpaMy8 implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	ShiftRepository shiftRepository;

	@Autowired
	RoleRepository roleRepo;

		@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtil jwtUtil;

	

	@Override
	public List<EmployeeResponseDto> findAll() {
		
		return employeeRepository.findAll().stream().map(employee -> EmployeeMapper.convertirEmployeeDto(employee)).toList();
	}

	@Override
public EmployeeResponseDto findByIdDto(int idEmployee) {

    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");
    
    Employee employee = employeeRepository.findById(idEmployee)
        .orElseThrow(() ->
            new NotFoundException(
                "No existe el Empleado con ID: " + idEmployee));

    if (!isAdmin) {
        if (authEmployee.getRestaurant() == null ||
            employee.getRestaurant() == null ||
            !authEmployee.getRestaurant().getIdRestaurant()
                .equals(employee.getRestaurant().getIdRestaurant())) {

            throw new ForbiddenException(
                "No puedes ver empleados de otro restaurante");
        }
    }

    return EmployeeMapper.convertirEmployeeDto(employee);
}

	@Override
	public int deleteOneEmployee(int idEmployee) {
		Employee employee = employeeRepository.findById(idEmployee).orElse(null);
		if(employee==null){
			return 0;

		}
		boolean isAdmin = SecurityContextHolder.getContext()
        .getAuthentication()
        .getAuthorities()
        .stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

				
		RolesEnum rolSolicitado = RoleUtils.roleNormalizer(employee.getRole().getRoleName());
		RolesEnum rolCreador = SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .map(auth -> auth.getAuthority())
            .findFirst()
            .map(RoleUtils::roleNormalizer)
            .orElseThrow(() -> new NoRoleException("Usuario sin rol"));

		if(isAdmin || (rolSolicitado.getNivel() < rolCreador.getNivel()&& workSameRestaurant(employee) )){

			try{
				employeeRepository.deleteById(idEmployee);
				return 1;
			} catch(Exception e){
				throw new DeleteRestrictionException("No se puede eliminar el empleado con ID: " + idEmployee);
			}
		} else {
			
			throw new ForbiddenException("No puedes borrar a ese usuario, ya que su nivel es igual o superior al tuyo.");
		}

	}


    //////////////////////
    ///  CREATE        ///
    ///              ///
    /// ////////////////

	@Override
	public EmployeeResponseDto insertOne(CreateEmployeeDto employee) {

   
    	if (employeeRepository.existsByDni(employee.getDni())) {
        	throw new AlreadyExistsException(
            	"El empleado con DNI: " + employee.getDni() + " ya existe."
        );
    }

   
    RolesEnum rolSolicitado = RoleUtils.roleNormalizer(employee.getRole());
    Employee main = null;
    boolean isAdmin=hasAuthority("ROLE_ADMIN");
    
 

        
   
    if (!isAdmin) {

        main = getAuthenticatedEmployee();

        if(main.getRestaurant()==null){
            throw new NotFoundException("El usuario no tiene un restaurante asignado: " + main.getDni());
        }
       
        RolesEnum rolCreador = SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .map(auth -> auth.getAuthority())
            .findFirst()
            .map(RoleUtils::roleNormalizer)
            .orElseThrow(() -> new NoRoleException("Usuario sin rol"));

        if (rolSolicitado.getNivel() >= rolCreador.getNivel()) {
            throw new ForbiddenException(
                "No puedes crear un empleado con el rol " + rolSolicitado + " tu nivel es menor que el del rol solicitado."
            );
        }
    }

    
    String roleBd = "ROLE_" + rolSolicitado.name();
    Role roleEntity = roleRepo.findByRoleName(roleBd);

	if(roleEntity==null){
		throw new NoRoleException("El rol no existe: " + roleEntity);
	}

   
    try {
                

        Employee newEmployee = new Employee();
        newEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
        newEmployee.setDni(employee.getDni());
        newEmployee.setEmail(employee.getEmail());
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());
		if(isAdmin){
			Role roleOwner = roleRepo.findByRoleName("ROLE_OWNER");
			newEmployee.setRole(roleOwner);

		} else {
            newEmployee.setRestaurant(main.getRestaurant());
			newEmployee.setRole(roleEntity);
		}
		newEmployee.setCreatedAt(LocalDate.now());
		newEmployee.setHireDate(LocalDate.now());
        employeeRepository.save(newEmployee);
        return EmployeeMapper.convertirEmployeeDto(newEmployee);

    } catch (Exception e) {
        throw new RuntimeException("Error técnico al guardar el empleado", e);
    }


}

 // UPDATE

	@Override
	public EmployeeResponseDto updateOne(int idEmployee,UpdateEmployeeDto updateEmployeeDto) {
		
    Employee employeeUpdate = employeeRepository.findById(idEmployee)
            .orElseThrow(() ->
                    new NotFoundException("Empleado con id: " + idEmployee + " no existe.")
            );

   
    boolean isAdmin = SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

    
    RolesEnum rolCreador = null;
    if (!isAdmin) {
        rolCreador = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .findFirst()
                .map(RoleUtils::roleNormalizer)
                .orElseThrow(() -> new NoRoleException("Usuario sin rol"));
    }

    
    String dniAuth = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    Employee authEmployee = employeeRepository.findByDni(dniAuth)
            .orElseThrow(() ->
                    new NotFoundException("Empleado autenticado no existe")
            );

    boolean isSelfUpdate = authEmployee.getDni().equals(employeeUpdate.getDni());

    
    RolesEnum rolEmpleado = RoleUtils.roleNormalizer(
            employeeUpdate.getRole().getRoleName()
    );

    
    if (!isAdmin && rolEmpleado.getNivel() >= rolCreador.getNivel()) {
        throw new ForbiddenException(
                "No puedes actualizar un empleado con el rol " + rolEmpleado +
                " tu nivel es menor o igual"
        );
    }



    if (updateEmployeeDto.getFirstName() != null) {
        employeeUpdate.setFirstName(updateEmployeeDto.getFirstName());
    }

    if (updateEmployeeDto.getLastName() != null) {
        employeeUpdate.setLastName(updateEmployeeDto.getLastName());
    }

    if (updateEmployeeDto.getEmail() != null) {
        employeeUpdate.setEmail(updateEmployeeDto.getEmail());
    }

    if (updateEmployeeDto.getIsActive() != null) {
        employeeUpdate.setActive(updateEmployeeDto.getIsActive());
    }

   
    if (updateEmployeeDto.getHourlyWage() != null) {

        if (isAdmin ||
            rolCreador == RolesEnum.OWNER ||
            rolCreador == RolesEnum.MANAGER) {

            employeeUpdate.setHourlyWage(updateEmployeeDto.getHourlyWage());

        } else {
            throw new ForbiddenException("No puedes modificar el salario");
        }
    }


    if (updateEmployeeDto.getRole() != null) {

        
        if (isSelfUpdate && !isAdmin) {
            throw new ForbiddenException("No puedes cambiar tu propio rol");
        }

      
        if (!isAdmin && rolCreador != RolesEnum.OWNER) {
            throw new ForbiddenException("Solo ADMIN u OWNER pueden cambiar el rol");
        }

        RolesEnum nuevoRol = RoleUtils.roleNormalizer(updateEmployeeDto.getRole());

       
        if (!isAdmin && nuevoRol.getNivel() >= rolCreador.getNivel()) {
            throw new ForbiddenException(
                    "No puedes asignar el rol " + nuevoRol +
                    " porque es igual o superior al tuyo"
            );
        }

        String roleBd = "ROLE_" + nuevoRol.name();

        Role roleEntity = roleRepo.findByRoleName(roleBd);

        if (roleEntity == null) {
            throw new NotFoundException("No existe el role: " + nuevoRol);
        }

        employeeUpdate.setRole(roleEntity);
    }

    
    employeeUpdate.setUpdatedAt(LocalDate.now());
    employeeRepository.save(employeeUpdate);

    return EmployeeMapper.convertirEmployeeDto(employeeUpdate);
}





@Override
public List<EmployeeResponseDto> findMyRestaurantEmployees() {

    Employee authEmployee = getAuthenticatedEmployee();

    if (authEmployee.getRestaurant() == null) {
        throw new ForbiddenException(
            "El empleado no tiene restaurante asignado");
    }

    return employeeRepository
        .findByRestaurant(authEmployee.getRestaurant())
        .stream()
        .map(EmployeeMapper::convertirEmployeeDto)
        .toList();
}
	

	





	@Override
	public LoginResponseDto authenticateEmployee(CreateEmployeeDto loginEmployee) {
		Employee exist = employeeRepository.findByDni(loginEmployee.getDni()).orElseThrow(()->new NotFoundException("No se encontró empleado con DNI: "+ loginEmployee.getDni()));

		if(!passwordEncoder.matches(loginEmployee.getPassword(),exist.getPassword())){
			throw new UnauthorizedException("Usuario o password incorrecto");
		}

		String token = jwtUtil.generateToken(exist.getDni(), exist.getRole().getRoleName());
		return LoginResponseDto.builder()
		.token(token)
		.username(exist.getDni())
		.build();


}

  private Employee getAuthenticatedEmployee() {

        String dni = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

        return employeeRepository.findByDni(dni)
            .orElseThrow(() -> new UnauthorizedException("Usuario no autenticado"));
    }

      private boolean hasAuthority(String role) {

       return SecurityContextHolder.getContext()
        .getAuthentication()
        .getAuthorities()
        .stream()
        .anyMatch(auth -> auth.getAuthority().equals(role));

    //    SecurityContextHolder.getContext()
    //         .getAuthentication()
    //         .getAuthorities()
    //         .stream()
    //         .anyMatch(a -> a.getAuthority().equals(role));
    }


    private boolean workSameRestaurant(Employee employee){
        Employee main = getAuthenticatedEmployee();

        return main.getRestaurant().getIdRestaurant().equals(employee.getRestaurant().getIdRestaurant());

    }


    @Override
public EmployeeResponseDto getMyProfile() {
    return EmployeeMapper.convertirEmployeeDto(getAuthenticatedEmployee());
}

@Override
public void changeMyPassword(ChangePasswordDto changePasswordDto) {
    Employee employee = getAuthenticatedEmployee();

    if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), employee.getPassword())) {
        throw new UnauthorizedException("La contraseña actual es incorrecta");
    }

    employee.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
    employee.setUpdatedAt(LocalDate.now());
    employeeRepository.save(employee);
}

    @Override
    public EmployeeResponseDto assignShiftToEmployee(int employee, int shift) {

         Employee employeeToAssign = employeeRepository.findById(employee)
        .orElseThrow(() ->
            new NotFoundException(
                "No existe el Empleado con ID: " + employee));

         Shift shiftToAssign = shiftRepository.findById(shift).orElseThrow(()->
                new NotFoundException("No existe Shift con ID: " + shift)
        );    

        Employee employeeLogged = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        if(employeeLogged.getIdUser() == employeeToAssign.getIdUser())
            throw new ForbiddenException("No puedes cambiar tu propio shift");

        if(employeeToAssign.getRestaurant()==null || shiftToAssign.getRestaurant()==null){
            throw new ForbiddenException("Empleado o turno sin restaraunte asignado");
        }

        if(!employeeToAssign.getRestaurant().getIdRestaurant().equals(shiftToAssign.getRestaurant().getIdRestaurant()))
                throw new BadRequestException("Empleado y shift no pertenecen al mismo restaurante.");

         
        RolesEnum rolCreador = null;
        if (!isAdmin) {

            if(employeeLogged.getRestaurant()==null){
                throw new ForbiddenException("No tienes restaurante asignado");
            }



            rolCreador = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .findFirst()
                .map(RoleUtils::roleNormalizer)
                .orElseThrow(() -> new NoRoleException("Usuario sin rol"));


             RolesEnum rolEmpleado = RoleUtils.roleNormalizer(
                employeeToAssign.getRole().getRoleName());


                if (rolEmpleado.getNivel() >= rolCreador.getNivel()) {
                throw new ForbiddenException(
                        "No puedes actualizar un empleado con el rol " + rolEmpleado +
                        " tu nivel es menor o igual"
                        );
                    }

             }

    

     
            employeeToAssign.setShift(shiftToAssign);
            employeeRepository.save(employeeToAssign);
            return EmployeeMapper.convertirEmployeeDto(employeeLogged);
            
            }
        
        
        

}
