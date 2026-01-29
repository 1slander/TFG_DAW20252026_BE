package com.tfgbe.mapper;

import com.tfgbe.modelo.dto.RestaurantResponseDto;
import com.tfgbe.modelo.entities.Restaurant;

public class RestaurantMapper {

     public static RestaurantResponseDto convertirRestaurantDto(Restaurant restaurant){
         return RestaurantResponseDto.builder()
            .idRestaurant(restaurant.getIdRestaurant())
            .cif(restaurant.getCif())
            .restaurantName(restaurant.getRestaurantName())
            .address(restaurant.getAddress())
            .country(restaurant.getCountry())
            .phone(restaurant.getPhone())
            .capacity(restaurant.getCapacity())
            .totalTables(restaurant.getTotalTables())
            .idOwner(
                restaurant.getOwner() != null 
                    ? restaurant.getOwner().getIdUser() 
                    : null
            )
            .ownerName(
                restaurant.getOwner() != null
                    ? restaurant.getOwner().getFirstName() + " " + restaurant.getOwner().getLastName()
                    : null
            )
            .build();
    }

}
