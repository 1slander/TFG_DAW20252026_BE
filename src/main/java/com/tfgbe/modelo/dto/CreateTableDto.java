package com.tfgbe.modelo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTableDto {

    @NotNull(message = "El número de mesa es obligatorio")
    @Min(value = 1, message = "El número de mesa debe ser mayor que 0")
    private Integer tableNumber;

    @NotNull(message = "La capacidad de la mesa es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser mayor que 0")
    private Integer tableCapacity;

    private Integer posX;
    private Integer posY;
    private Integer idFloor;
}