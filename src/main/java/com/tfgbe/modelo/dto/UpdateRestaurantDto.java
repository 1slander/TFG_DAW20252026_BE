package com.tfgbe.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRestaurantDto {

      private String restaurantName;
    private String address;
    private String country;
    private String phone;
    private Integer capacity;
    private Integer totalTables;
}
