package com.tfgbe.modelo.services;

import java.util.List;
import com.tfgbe.modelo.dto.FloorResponseDto;

public interface FloorService {
    List<FloorResponseDto> findByRestaurant(Long idRestaurant);
    FloorResponseDto createFloor(Long idRestaurant, String name);
    FloorResponseDto updateFloor(Integer idFloor, String name);
    void deleteFloor(Integer idFloor);
    FloorResponseDto findById(Integer idFloor);
}
