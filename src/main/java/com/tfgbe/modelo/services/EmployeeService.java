package com.tfgbe.modelo.services;

import java.util.List;
import java.util.Optional;

import com.tfgbe.modelo.dto.CreateEmployeeDto;
import com.tfgbe.modelo.dto.EmployeeResponseDto;
import com.tfgbe.modelo.dto.LoginResponseDto;
import com.tfgbe.modelo.entities.Employee;

public interface EmployeeService {
    List<EmployeeResponseDto> findAll();
   EmployeeResponseDto findById(int idEmployee);
    int deleteOneEmployee(int idEmployee);

    // TODO: Update employee

    // Login & Signup
    EmployeeResponseDto insertOne (CreateEmployeeDto employee);
    LoginResponseDto authenticateEmployee(CreateEmployeeDto employee);


    

}
