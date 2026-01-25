package com.tfgbe.modelo.dto;

import com.tfgbe.modelo.entities.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class EmployeeResponseDto {

    private int idEmployee;
    private String email;
    private String firstName;
    private String lastName;
    private String dni;
    // Si necesitamos el role para logica, lo idea seria cambiar a Role role
    private String role;

    // Todo: Add Restaurant y Shift


}
