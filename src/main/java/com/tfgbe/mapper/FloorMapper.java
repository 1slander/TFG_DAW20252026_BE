package com.tfgbe.mapper;

import com.tfgbe.modelo.dto.FloorResponseDto;
import com.tfgbe.modelo.entities.Floor;

public class FloorMapper {
    public static FloorResponseDto toDto(Floor floor) {
        if (floor == null) return null;
        return FloorResponseDto.builder()
            .idFloor(floor.getIdFloor())
            .name(floor.getName())
            .idRestaurant(floor.getRestaurant() != null ? floor.getRestaurant().getIdRestaurant() : null)
            .build();
    }
}
