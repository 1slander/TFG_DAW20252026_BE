package com.tfgbe.modelo.services;

import java.util.List;

import com.tfgbe.modelo.dto.AssignEmployeeRestaurantDto;
import com.tfgbe.modelo.dto.CreateRestaurantDto;
import com.tfgbe.modelo.dto.EmployeeResponseDto;
import com.tfgbe.modelo.dto.RestaurantResponseDto;
import com.tfgbe.modelo.dto.UpdateRestaurantDto;
import com.tfgbe.modelo.entities.Restaurant;

public interface RestaurantService {
    
    RestaurantResponseDto createRestaurant(CreateRestaurantDto dto);

    RestaurantResponseDto updateRestaurant(Long idRestaurant, UpdateRestaurantDto dto);

    RestaurantResponseDto findById(Long idRestaurant);

    List<RestaurantResponseDto> findAll();

    int deleteRestaurant(Long idRestaurant);

    RestaurantResponseDto findMyRestaurant();
    EmployeeResponseDto assignEmployeeToRestaurant(AssignEmployeeRestaurantDto dto);
	
}
