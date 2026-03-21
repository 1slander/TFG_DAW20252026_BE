package com.tfgbe.modelo.dto;

import java.time.LocalDate;

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
    private String role;
    private double hourlyWage;
    private String restaurant;
    private String shift;
    private LocalDate hireDate;
    private LocalDate createdAt;
    private boolean isActive;
    // Todo: Add Restaurant y Shift


}
