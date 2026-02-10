package com.tfgbe.modelo.services;

import java.util.List;

import com.tfgbe.modelo.dto.CreateTableAssignmentDto;
import com.tfgbe.modelo.dto.TableAssignmentResponseDto;
import com.tfgbe.modelo.entities.TableAssignment;

public interface TableAssignmentService  {

     TableAssignmentResponseDto createAssignment(
        CreateTableAssignmentDto dto
    );

    TableAssignmentResponseDto closeAssignment(
        Integer idAssignment
    );

    List<TableAssignmentResponseDto> findAssignmentsByTable(
        Integer idTable
    );
}
