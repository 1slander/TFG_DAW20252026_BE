package com.tfgbe.modelo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {

    @NotBlank(message = "Email no puede estar vacío")
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank(message = "DNI no puede estar vacío")
    @Size(max = 9)
    private String dni;

    @NotBlank(message = "Nombre no puede estar vacío")
    private String firstName;

    @NotBlank(message = "Apellidos no puede estar vacío")
    private String lastName;

    private String role;
    private Double hourlyWage;
}
