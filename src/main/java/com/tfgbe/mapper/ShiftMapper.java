package com.tfgbe.mapper;

import com.tfgbe.modelo.dto.ShiftResponseDto;
import com.tfgbe.modelo.entities.Shift;

public class ShiftMapper {

    public static ShiftResponseDto convertirShiftDto(Shift shift) {
        return ShiftResponseDto.builder()
            .idShift(shift.getIdShift())
            .assignShift(shift.getAssignShift())
            .build();
    }
}
