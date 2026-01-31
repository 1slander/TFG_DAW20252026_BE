package com.tfgbe.modelo.services;

import java.util.List;

import com.tfgbe.modelo.dto.CreateShiftDto;
import com.tfgbe.modelo.dto.ShiftResponseDto;
import com.tfgbe.modelo.dto.UpdateShiftDto;
import com.tfgbe.modelo.entities.Shift;

public interface ShiftService{
 List<ShiftResponseDto> findAll();

    ShiftResponseDto findById(int idShift);

    ShiftResponseDto createShift(CreateShiftDto dto);

    ShiftResponseDto updateShift(int idShift, UpdateShiftDto dto);

    int deleteShift(int idShift);

}
