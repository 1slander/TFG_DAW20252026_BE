package com.tfgbe.modelo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateRestaurantDto {

    @Size(max = 9)
    @NotBlank(message = "CIF no puede estar vacío")
    private String cif;

    @NotBlank(message = "Nombre no puede estar vacío")
    private String restaurantName;

    @NotBlank(message = "Dirección no puede estar vacío")
    private String address;

    @NotBlank(message = "País no puede estar vacío")
    private String country;

    @NotBlank(message = "Teléfono no puede estar vacío")
    private String phone;
    
    // Todo: Add capacity, total tables?

}
