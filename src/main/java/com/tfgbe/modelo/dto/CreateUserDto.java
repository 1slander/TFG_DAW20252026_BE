package com.tfgbe.modelo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    @Size(max=20)
    @NotNull
    @NotBlank(message="Username no puede estar vacío")
    private String username;

    @NotNull
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    // @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$", message = "La contraseña debe ser de al menos 6 caracteres e incluir minúsculas y mayúsculas")
    // aqui puedo meter un @Pattern para validar que la password tie X caracters y mayus minus etc
    @NotNull
    @NotBlank
    private String password;

}
