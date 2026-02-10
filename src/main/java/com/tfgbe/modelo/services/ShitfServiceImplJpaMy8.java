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
        
    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    List<Shift> shifts;

    if (isAdmin) {
        shifts = shiftRepository.findAll();
    } else {
        if (authEmployee.getRestaurant() == null) {
            throw new ForbiddenException("No tienes restaurante asignado");
        }
        shifts = shiftRepository.findByRestaurant(
            authEmployee.getRestaurant()
        );
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
 Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    if (!isAdmin && !hasAuthority("ROLE_OWNER")) {
        throw new ForbiddenException(
            "No tienes permisos para crear turnos");
    }

    if (authEmployee.getRestaurant() == null) {
        throw new ForbiddenException(
            "No tienes restaurante asignado");
    }

    Restaurant restaurant = authEmployee.getRestaurant();

    if (shiftRepository.existsByAssignShiftAndRestaurant(
            dto.getAssignShift(), restaurant)) {

        throw new AlreadyExistsException(
            "El turno " + dto.getAssignShift()
            + " ya existe en este restaurante");
    }

    Shift shift = Shift.builder()
        .assignShift(dto.getAssignShift())
        .restaurant(restaurant)
        .build();

    return ShiftMapper.convertirShiftDto(
        shiftRepository.save(shift)
    );
}



	@Override
public ShiftResponseDto updateShift(int idShift, UpdateShiftDto dto) {

    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    if (!isAdmin && !hasAuthority("ROLE_OWNER")) {
        throw new ForbiddenException(
            "No tienes permisos para modificar turnos");
    }

    if (authEmployee.getRestaurant() == null) {
        throw new ForbiddenException(
            "No tienes restaurante asignado");
    }

    Shift shift = shiftRepository
        .findByIdShiftAndRestaurant(
            idShift,
            authEmployee.getRestaurant()
        )
        .orElseThrow(() ->
            new NotFoundException(
                "No existe el turno con id: " + idShift));

    if (!shift.getAssignShift().equals(dto.getAssignShift())
        && shiftRepository.existsByAssignShiftAndRestaurant(
            dto.getAssignShift(),
            authEmployee.getRestaurant())) {

        throw new AlreadyExistsException(
            "El turno " + dto.getAssignShift()
            + " ya existe en este restaurante");
    }

    shift.setAssignShift(dto.getAssignShift());

    return ShiftMapper.convertirShiftDto(
        shiftRepository.save(shift)
    );
}


    

    @Override
    public int deleteShift(int idShift) {


		if (!hasAuthority("ROLE_ADMIN") && !hasAuthority("ROLE_OWNER") && !hasAuthority("ROLE_MANAGER")) {
        throw new ForbiddenException("No tienes nivel de acceso necesario para poder eliminar turnos");
    }

	if(!shiftRepository.existsById(idShift))
		return 0;

		try{
			shiftRepository.deleteById(idShift);
			return 1;
		} catch (Exception e){
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
