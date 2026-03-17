package com.tfgbe.modelo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateElementDto {
    @NotBlank(message = "El tipo de elemento es obligatorio")
    private String type;

    @NotNull(message = "La posición X es obligatoria")
    private Integer posX;

    @NotNull(message = "La posición Y es obligatoria")
    private Integer posY;

    private Integer width;
    private Integer height;
    private Integer rotation;
}
