package com.tfgbe.modelo.services;

import java.util.List;
import com.tfgbe.modelo.dto.CreateElementDto;
import com.tfgbe.modelo.dto.ElementResponseDto;

public interface RestaurantElementService {
    ElementResponseDto createElement(Long idRestaurant, CreateElementDto dto);
    List<ElementResponseDto> findByRestaurant(Long idRestaurant);
    List<ElementResponseDto> findByFloor(Integer idFloor);
    void updatePosition(Long idElement, Integer posX, Integer posY, Integer rotation);
    ElementResponseDto updateDimensions(Long idElement, Integer width, Integer height);
    int deleteElement(Long idElement);
}
