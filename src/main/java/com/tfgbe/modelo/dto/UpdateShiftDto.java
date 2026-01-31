package com.tfgbe.modelo.dto;

import com.tfgbe.modelo.entities.ShiftType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShiftDto {

    @NotNull(message = "El turno es obligatorio")
    private ShiftType assignShift;

}
