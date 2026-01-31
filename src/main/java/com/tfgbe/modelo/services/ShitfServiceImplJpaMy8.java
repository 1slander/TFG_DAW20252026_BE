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
import com.tfgbe.modelo.dto.CreateShiftDto;
import com.tfgbe.modelo.dto.UpdateEmployeeDto;
import com.tfgbe.modelo.dto.UpdateShiftDto;
import com.tfgbe.modelo.entities.Shift;
import com.tfgbe.modelo.entities.ShiftType;
import com.tfgbe.modelo.repository.ShiftRepository;

@Service
public class ShitfServiceImplJpaMy8 implements ShiftService{

	@Autowired
	ShiftRepository shiftRepository;

	
    @Override
    public List<Shift> findAll() {
        return shiftRepository.findAll();
    }

    @Override
    public Shift findById(int idShift) {
        return shiftRepository.findById(idShift)
            .orElseThrow(() ->
                new NotFoundException("No existe el turno con id: " + idShift));
    }

    @Override
    public Shift createShift(CreateShiftDto assignShift) {

		if (!hasAuthority("ROLE_ADMIN") && !hasAuthority("ROLE_OWNER")) {
        throw new ForbiddenException("No tienes permisos para crear turnos");
    }

    ShiftType shiftType;
    try {
        shiftType = ShiftType.valueOf(assignShift.getAssignShift().toUpperCase().trim());
    } catch (IllegalArgumentException e) {
        throw new BadRequestException("Turno inválido: " + assignShift.getAssignShift());
    }

    if (shiftRepository.existsByAssignShift(shiftType.name())) {
        throw new AlreadyExistsException("El turno " + shiftType + " ya existe");
    }

    Shift shift = Shift.builder()
        .assignShift(shiftType)
        .build();

    return shiftRepository.save(shift);
    }



	@Override
	public Shift updateShift(int idShift, UpdateShiftDto assignShift) {
		if (!hasAuthority("ROLE_ADMIN") && !hasAuthority("ROLE_OWNER")) {
        throw new ForbiddenException("No tienes permisos para modificar turnos");
    }

    Shift shift = findById(idShift);

    ShiftType shiftType;
    try {
        shiftType = ShiftType.valueOf(assignShift.getAssignShift().toUpperCase().trim());
    } catch (IllegalArgumentException e) {
        throw new BadRequestException("Turno inválido: " + assignShift.getAssignShift());
    }

    if (!shift.getAssignShift().equals(shiftType)
            && shiftRepository.existsByAssignShift(shiftType.name())) {
        throw new AlreadyExistsException("El turno " + shiftType + " ya existe");
    }

    shift.setAssignShift(shiftType);
    return shiftRepository.save(shift);
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

	
}
