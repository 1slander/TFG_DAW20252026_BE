package com.tfgbe.modelo.dto;

import com.tfgbe.modelo.entities.TableStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTableDto {

    private Integer tableNumber;
    private Integer tableCapacity;
    private TableStatus status;
}