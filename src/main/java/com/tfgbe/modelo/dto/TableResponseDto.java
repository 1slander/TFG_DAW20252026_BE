package com.tfgbe.modelo.dto;

import com.tfgbe.modelo.entities.TableStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TableResponseDto {

    private int idTable;
    private int tableNumber;
    private int tableCapacity;
    private TableStatus status;

    private Long idRestaurant;
    private String restaurantName;

    private Integer posX;
    private Integer posY;
}