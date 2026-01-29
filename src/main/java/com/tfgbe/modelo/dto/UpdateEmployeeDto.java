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


private String firstName;

private String lastName; 
@Email(message = "Email no válido")
private String email;

private Boolean isActive;  

private Double hourlyWage;    
private String role;


}
