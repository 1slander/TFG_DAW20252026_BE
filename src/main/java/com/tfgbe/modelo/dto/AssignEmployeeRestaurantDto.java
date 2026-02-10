package com.tfgbe.modelo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignEmployeeRestaurantDto {

     @NotBlank
    private String dniEmployee;

    @NotNull
    private Long idRestaurant;

}
