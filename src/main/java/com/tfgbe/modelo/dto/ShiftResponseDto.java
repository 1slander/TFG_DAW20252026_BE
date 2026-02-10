package com.tfgbe.modelo.dto;

import com.tfgbe.modelo.entities.ShiftType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShiftResponseDto {

    private int idShift;
    private ShiftType assignShift;
}