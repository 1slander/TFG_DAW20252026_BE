package com.tfgbe.modelo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class CreateSignupRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 150)
    private String lastName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @Size(max = 30)
    private String phone;

    @Size(max = 255)
    private String restaurantName;

    private String message;
}
