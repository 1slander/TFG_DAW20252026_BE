package com.tfgbe.modelo.services;

import java.util.List;

import com.tfgbe.modelo.dto.CreateShiftDto;
import com.tfgbe.modelo.dto.UpdateShiftDto;
import com.tfgbe.modelo.entities.Shift;

public interface ShiftService{

    List<Shift> findAll();

    Shift findById(int idShift);

   Shift createShift(CreateShiftDto dto);

    Shift updateShift(int idShift, UpdateShiftDto dto);

    int deleteShift(int idShift);

}
