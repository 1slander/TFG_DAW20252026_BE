package com.tfgbe.modelo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ElementResponseDto {
    private Long idElement;
    private String type;
    private Integer posX;
    private Integer posY;
    private Integer width;
    private Integer height;
    private Integer rotation;
    private Long idRestaurant;
}
