package com.tfgbe.modelo.dto;

import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.entities.Role;
import com.tfgbe.modelo.entities.Shift;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEmployeeDto {

@NotBlank(message = "Nombre no puede estar vacío")
private String firstName;
@NotBlank(message = "Apellidos no pueden estar vacío")
private String lastName; 
@Email
private String email;

private Boolean isActive;  

private Double hourlyWage;    


}
