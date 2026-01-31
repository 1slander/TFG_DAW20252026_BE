package com.tfgbe.mapper;

import com.tfgbe.modelo.dto.TableAssignmentResponseDto;
import com.tfgbe.modelo.entities.TableAssignment;

public class TableAssignmentMapper {

    public static TableAssignmentResponseDto convertirTableAssignmentDto(
            TableAssignment assignment) {

        return TableAssignmentResponseDto.builder()
            .idAssignment(assignment.getIdAssignment())
            .startTime(assignment.getStartTime())
            .endTime(assignment.getEndTime())
            .idTable(
                assignment.getTable() != null
                    ? assignment.getTable().getIdTable()
                    : 0
            )
            .tableNumber(
                assignment.getTable() != null
                    ? assignment.getTable().getTableNumber()
                    : 0
            )
            .idEmployee(
                assignment.getEmployee() != null
                    ? assignment.getEmployee().getIdUser()
                    : 0
            )
            .employeeName(
                assignment.getEmployee() != null
                    ? assignment.getEmployee().getFirstName()
                        + " " + assignment.getEmployee().getLastName()
                    : null
            )
            .build();
    }

}
