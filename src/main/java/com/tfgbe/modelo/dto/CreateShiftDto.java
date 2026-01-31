package com.tfgbe.modelo.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateShiftDto {
    
    @NotBlank(message = "El turno no puede estar vacío")
    private String assignShift;

}
