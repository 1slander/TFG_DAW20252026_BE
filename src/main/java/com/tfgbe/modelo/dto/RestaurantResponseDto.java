package com.tfgbe.modelo.dto;

import com.tfgbe.modelo.entities.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RestaurantResponseDto {

    private Long idRestaurant;
    private String cif;
    private String restaurantName;   
    private String address;    
    private String country;    
    private String phone;
    private Integer capacity;
    private Integer totalTables;
    private int idOwner;
    private String ownerName;

   

}
