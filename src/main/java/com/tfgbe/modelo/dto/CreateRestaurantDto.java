package com.tfgbe.modelo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantDto {

    @Size(max = 9)
    @NotBlank(message = "CIF no puede estar vacío")
    private String cif;

  @NotBlank(message = "El nombre del restaurante es obligatorio")
    @Size(max = 255)
    private String restaurantName;

    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    @NotBlank(message = "El país es obligatorio")
    private String country;

    @NotBlank(message = "El teléfono es obligatorio")
    private String phone;

    @Min(value = 1, message = "La capacidad debe ser mayor que 0")
    private Integer capacity;

    @Min(value = 1, message = "Debe haber al menos una mesa")
    private Integer totalTables;

}
