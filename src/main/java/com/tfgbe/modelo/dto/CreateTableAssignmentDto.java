package com.tfgbe.modelo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTableAssignmentDto {

    @NotNull(message = "El id de la mesa es obligatorio")
    private Integer idTable;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime startTime;
}