package com.tfgbe.modelo.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.AlreadyExistsException;
import com.tfgbe.exceptions.BadRequestException;
import com.tfgbe.exceptions.DeleteRestrictionException;
import com.tfgbe.exceptions.ForbiddenException;
import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.exceptions.UnauthorizedException;
import com.tfgbe.mapper.ShiftMapper;
import com.tfgbe.modelo.dto.CreateShiftDto;
import com.tfgbe.modelo.dto.ShiftResponseDto;
import com.tfgbe.modelo.dto.UpdateEmployeeDto;
import com.tfgbe.modelo.dto.UpdateShiftDto;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.entities.Shift;
import com.tfgbe.modelo.entities.ShiftType;
import com.tfgbe.modelo.repository.EmployeeRepository;
import com.tfgbe.modelo.repository.ShiftRepository;

@Service
public class ShitfServiceImplJpaMy8 implements ShiftService{

	@Autowired
	ShiftRepository shiftRepository;

    @Autowired
    EmployeeRepository employeeRepository;

	
    @Override
    public List<ShiftResponseDto> findAll() {
        boolean isAdmin = hasAuthority("ROLE_ADMIN");
        Employee authEmployee = null;
        if (!isAdmin) {
            authEmployee = getAuthenticatedEmployee();
        }

        List<Shift> shifts;
        if (isAdmin) {
            shifts = shiftRepository.findAll();
        } else {
            if (authEmployee.getRestaurant() == null) {
                throw new ForbiddenException("No tienes restaurante asignado");
            }
            shifts = shiftRepository.findByRestaurant(authEmployee.getRestaurant());
        }

        return shifts.stream()
            .map(ShiftMapper::convertirShiftDto)
            .toList();
    }

    @Override
    public ShiftResponseDto findById(int idShift) {
        Shift shift = shiftRepository.findById(idShift)
            .orElseThrow(() ->
                new NotFoundException("No existe el turno con id: " + idShift));

                return ShiftMapper.convertirShiftDto(shift);
    }

    @Override
    public ShiftResponseDto createShift(CreateShiftDto dto) {
        boolean isAdmin = hasAuthority("ROLE_ADMIN");
        if (!isAdmin && !hasAuthority("ROLE_OWNER") && !hasAuthority("ROLE_MANAGER") && !hasAuthority("ROLE_ASSISTANT_MANAGER")) {
            throw new ForbiddenException("No tienes permisos para crear turnos");
        }

        Restaurant restaurant;
        if (isAdmin) {
            // Un Admin puro quizás no pueda crear turnos sin asignar restaurante
            // Por ahora mantenemos la lógica de que el Admin ve TODO pero 
            // la creación requiere contexto de restaurante si no viene en el DTO.
            // Si el Admin no es empleado, fallará aquí si no tiene restaurante.
            throw new BadRequestException("Los administradores generales no pueden crear turnos directamente sin contexto de restaurante.");
        } else {
            Employee authEmployee = getAuthenticatedEmployee();
            if (authEmployee.getRestaurant() == null) {
                throw new ForbiddenException("No tienes restaurante asignado");
            }
            restaurant = authEmployee.getRestaurant();
        }

        if (shiftRepository.existsByAssignShiftAndRestaurant(dto.getAssignShift(), restaurant)) {
            throw new AlreadyExistsException("El turno " + dto.getAssignShift() + " ya existe en este restaurante");
        }

        Shift shift = Shift.builder()
            .assignShift(dto.getAssignShift())
            .restaurant(restaurant)
            .build();

        return ShiftMapper.convertirShiftDto(shiftRepository.save(shift));
    }



	@Override
    public ShiftResponseDto updateShift(int idShift, UpdateShiftDto dto) {
    boolean isAdmin = hasAuthority("ROLE_ADMIN");
    if (!isAdmin && !hasAuthority("ROLE_OWNER") && !hasAuthority("ROLE_MANAGER") && !hasAuthority("ROLE_ASSISTANT_MANAGER")) {
        throw new ForbiddenException("No tienes permisos para modificar turnos");
    }

    Restaurant restaurant;
    if (isAdmin) {
        // Un Admin podría editar cualquier turno, pero necesitamos saber de qué restaurante es el turno
        // Aquí shiftRepository.findById ya lo encontrará, pero la lógica de validación de restaurante 
        // necesita el contexto del turno encontrado.
        restaurant = null; // No lo usaremos para filtrar el findById si somos admin
    } else {
        Employee authEmployee = getAuthenticatedEmployee();
        if (authEmployee.getRestaurant() == null) {
            throw new ForbiddenException("No tienes restaurante asignado");
        }
        restaurant = authEmployee.getRestaurant();
    }

    Shift shift;
    if (isAdmin) {
        shift = shiftRepository.findById(idShift)
            .orElseThrow(() -> new NotFoundException("No existe el turno con id: " + idShift));
        restaurant = shift.getRestaurant();
    } else {
        shift = shiftRepository.findByIdShiftAndRestaurant(idShift, restaurant)
            .orElseThrow(() -> new NotFoundException("No existe el turno con id: " + idShift + " en tu restaurante"));
    }

    if (!shift.getAssignShift().equals(dto.getAssignShift())
        && shiftRepository.existsByAssignShiftAndRestaurant(dto.getAssignShift(), restaurant)) {
        throw new AlreadyExistsException("El turno " + dto.getAssignShift() + " ya existe en este restaurante");
    }

    shift.setAssignShift(dto.getAssignShift());

    return ShiftMapper.convertirShiftDto(
        shiftRepository.save(shift)
    );
}


    

    @Override
    public int deleteShift(int idShift) {


		if (!hasAuthority("ROLE_ADMIN") && !hasAuthority("ROLE_OWNER") && !hasAuthority("ROLE_MANAGER") && !hasAuthority("ROLE_ASSISTANT_MANAGER")) {
        throw new ForbiddenException("No tienes nivel de acceso necesario para poder eliminar turnos");
    }

        Shift shift;
        boolean isAdmin = hasAuthority("ROLE_ADMIN");
        
        if (isAdmin) {
            shift = shiftRepository.findById(idShift)
                .orElseThrow(() -> new NotFoundException("No existe el turno con id: " + idShift));
        } else {
            Employee authEmployee = getAuthenticatedEmployee();
            if (authEmployee.getRestaurant() == null) {
                throw new ForbiddenException("No tienes restaurante asignado");
            }
            shift = shiftRepository.findByIdShiftAndRestaurant(idShift, authEmployee.getRestaurant())
                .orElseThrow(() -> new NotFoundException("No existe el turno con id: " + idShift + " en tu restaurante"));
        }

        // Desasignar a todos los empleados antes de borrar para evitar FK errors
        List<Employee> employeesInShift = employeeRepository.findByShift(shift);
        for (Employee emp : employeesInShift) {
            emp.setShift(null);
            employeeRepository.save(emp);
        }

        try {
            shiftRepository.delete(shift);
            return 1;
        } catch (Exception e) {
            throw new DeleteRestrictionException("No se puede eliminar el shift: " + idShift);
        }

       
	}
        
    

	private boolean hasAuthority(String role) {
    return SecurityContextHolder.getContext()
        .getAuthentication()
        .getAuthorities()
        .stream()
        .anyMatch(a -> a.getAuthority().equals(role));
}
private Shift findShiftEntityById(int idShift) {
    return shiftRepository.findById(idShift)
        .orElseThrow(() ->
            new NotFoundException("No existe el turno con id: " + idShift));
}

private Employee getAuthenticatedEmployee() {
    String dni = SecurityContextHolder.getContext()
        .getAuthentication()
        .getName();

    return employeeRepository.findByDni(dni)
        .orElseThrow(() ->
            new UnauthorizedException("Usuario no autenticado"));
}
	
}
