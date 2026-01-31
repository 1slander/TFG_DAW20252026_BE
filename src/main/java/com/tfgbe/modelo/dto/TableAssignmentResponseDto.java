package com.tfgbe.modelo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TableAssignmentResponseDto {

    private int idAssignment;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int idTable;
    private int tableNumber;

    private int idEmployee;
    private String employeeName;
}