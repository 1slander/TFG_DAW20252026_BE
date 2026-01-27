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
import com.tfgbe.modelo.repository.EmployeeRepository;
import com.tfgbe.modelo.repository.RoleRepository;
import com.tfgbe.security.JwtUtil;
import com.tfgbe.util.RoleUtils;
import com.tfgbe.util.RolesEnum;

@Service
public class EmployeeServiceImplJpaMy8 implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepository;

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
		return employeeRepository.findById(idEmployee).map(employee -> EmployeeMapper.convertirEmployeeDto(employee)).orElseThrow(()->new NotFoundException("No existe el Empleado con ID: " + idEmployee));
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

		if(isAdmin || rolSolicitado.getNivel() < rolCreador.getNivel()){

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

	@Override
	public EmployeeResponseDto insertOne(CreateEmployeeDto employee) {

   
    	if (employeeRepository.existsByDni(employee.getDni())) {
        	throw new AlreadyExistsException(
            	"El empleado con DNI: " + employee.getDni() + " ya existe."
        );
    }

   
    RolesEnum rolSolicitado = RoleUtils.roleNormalizer(employee.getRole());

    
    boolean isAdmin = SecurityContextHolder.getContext()
        .getAuthentication()
        .getAuthorities()
        .stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

   
    if (!isAdmin) {

       
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

			newEmployee.setRole(roleEntity);
		}

        employeeRepository.save(newEmployee);
        return EmployeeMapper.convertirEmployeeDto(newEmployee);

    } catch (Exception e) {
        throw new RuntimeException("Error técnico al guardar el empleado", e);
    }


}



	@Override
	public EmployeeResponseDto updateOne(int idEmployee,UpdateEmployeeDto updateEmployeeDto) {
		Employee employeeUpdate = employeeRepository.findById(idEmployee).orElse(null);
		if(employeeUpdate==null){
			throw new NotFoundException("Empleado con id: " + idEmployee + " no existe.");
		}

		 boolean isAdmin = SecurityContextHolder.getContext()
        .getAuthentication()
        .getAuthorities()
        .stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
		
		

       
        RolesEnum rolCreador = SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .map(auth -> auth.getAuthority())
            .findFirst()
            .map(RoleUtils::roleNormalizer)
            .orElseThrow(() -> new NoRoleException("Usuario sin rol"));

		RolesEnum rolSolicitado = RoleUtils.roleNormalizer(employeeUpdate.getRole().getRoleName());

		if (!isAdmin && rolSolicitado.getNivel() >= rolCreador.getNivel()) {
            throw new ForbiddenException(
                "No puedes actualizar un empleado con el rol " + rolSolicitado + " tu nivel es menor que el del rol solicitado."
            );
        }

		

		
			if(updateEmployeeDto.getFirstName()!=null ){
				employeeUpdate.setFirstName(updateEmployeeDto.getFirstName());
			}
			if(updateEmployeeDto.getLastName()!=null){
				employeeUpdate.setLastName(updateEmployeeDto.getLastName());
			}
			if(updateEmployeeDto.getEmail()!=null){
				employeeUpdate.setEmail(updateEmployeeDto.getEmail());
			}
		

			if(updateEmployeeDto.getHourlyWage()!=null){
				
					if(isAdmin || rolCreador==RolesEnum.OWNER || rolCreador==RolesEnum.MANAGER){
						employeeUpdate.setHourlyWage(updateEmployeeDto.getHourlyWage());

					} else {
						throw new ForbiddenException("No puedes insertar salario.");
					}

			}
			
			if(updateEmployeeDto.getIsActive() != null){
				employeeUpdate.setActive(updateEmployeeDto.getIsActive());
			} 

			employeeUpdate.setUpdatedAt(LocalDate.now());

			employeeRepository.save(employeeUpdate);

			

			return EmployeeMapper.convertirEmployeeDto(employeeUpdate);


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

}
