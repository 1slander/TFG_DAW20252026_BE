package com.tfgbe.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloorResponseDto {
    private Integer idFloor;
    private String name;
    private Long idRestaurant;
}
