package com.tfgbe.mapper;

import com.tfgbe.modelo.dto.EmployeeResponseDto;
import com.tfgbe.modelo.entities.Employee;

public class EmployeeMapper {


    public static EmployeeResponseDto convertirEmployeeDto(Employee employee){
        return EmployeeResponseDto.builder()
                .idEmployee(employee.getIdUser())
                .email(employee.getEmail())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .dni(employee.getDni())
                .role(employee.getRole().getRoleName())
                .hourlyWage(employee.getHourlyWage())
                .restaurant(
                employee.getRestaurant() != null
                    ? employee.getRestaurant().getRestaurantName()
                    : null
            )
                .shift(employee.getShift()!=null ? employee.getShift().getAssignShift().name():"SIN ASIGNAR")
                .build();
    }

}
