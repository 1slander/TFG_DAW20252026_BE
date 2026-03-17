package com.tfgbe.mapper;

import org.springframework.stereotype.Component;

import com.tfgbe.modelo.dto.ElementResponseDto;
import com.tfgbe.modelo.entities.RestaurantElement;

@Component
public class ElementMapper {

    public static ElementResponseDto convertirElementDto(RestaurantElement element) {
        if (element == null) return null;

        return ElementResponseDto.builder()
                .idElement(element.getIdElement())
                .type(element.getType())
                .posX(element.getPosX())
                .posY(element.getPosY())
                .width(element.getWidth())
                .height(element.getHeight())
                .rotation(element.getRotation())
                .idRestaurant(element.getRestaurant().getIdRestaurant())
                .build();
    }
}
