package com.tfgbe.modelo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.ForbiddenException;
import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.exceptions.UnauthorizedException;
import com.tfgbe.mapper.TableAssignmentMapper;
import com.tfgbe.modelo.dto.CreateTableAssignmentDto;
import com.tfgbe.modelo.dto.TableAssignmentResponseDto;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.TableAssignment;
import com.tfgbe.modelo.entities.TableEntity;
import com.tfgbe.modelo.entities.TableStatus;
import com.tfgbe.modelo.repository.EmployeeRepository;
import com.tfgbe.modelo.repository.TableAssignmentRepository;
import com.tfgbe.modelo.repository.TableRepository;

import jakarta.transaction.Transactional;

@Service

public class TableAssignmentServiceImpl implements TableAssignmentService{
	 @Autowired
    private TableAssignmentRepository tableAssignmentRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TableService tableService;

    @Transactional
    @Override
    public TableAssignmentResponseDto createAssignment(
            CreateTableAssignmentDto dto) {

        Employee authEmployee = getAuthenticatedEmployee();

        TableEntity table = tableRepository.findById(dto.getIdTable())
            .orElseThrow(() -> 
                new NotFoundException("Mesa no encontrada"));

        Employee targetEmployee = employeeRepository
        .findById(dto.getIdEmployee())
        .orElseThrow(() ->
        new NotFoundException("Empleado no encontrado"));

        // Validar que pertenece al mismo restaurante
        if (authEmployee.getRestaurant() == null ||
            !authEmployee.getRestaurant().getIdRestaurant()
                .equals(table.getRestaurant().getIdRestaurant())) {

            throw new ForbiddenException(
                "No puedes asignar mesas de otro restaurante");
        }

        

        if (targetEmployee.getRestaurant() == null ||
    !targetEmployee.getRestaurant().getIdRestaurant()
        .equals(table.getRestaurant().getIdRestaurant())) {

    throw new ForbiddenException(
        "No puedes asignar una mesa a un empleado de otro restaurante");
}

    // Validar que no haya asignación activa
        tableAssignmentRepository
            .findByTableAndEndTimeIsNull(table)
            .ifPresent(a -> {
                throw new ForbiddenException(
                    "La mesa ya tiene una asignación activa");
            });
        

        TableAssignment assignment = new TableAssignment();
assignment.setTable(table);
assignment.setEmployee(targetEmployee);
assignment.setStartTime(dto.getStartTime());
assignment.setEndTime(null);

        tableAssignmentRepository.save(assignment);

        return TableAssignmentMapper
            .convertirTableAssignmentDto(assignment);
    }

    @Transactional
    @Override
    public TableAssignmentResponseDto closeAssignment(
            Integer idAssignment) {

        TableAssignment assignment =
            tableAssignmentRepository.findById(idAssignment)
                .orElseThrow(() -> 
                    new NotFoundException(
                        "Asignación no encontrada"));

        if (assignment.getEndTime() != null) {
            throw new ForbiddenException(
                "La asignación ya está cerrada");
        }

        Employee authEmployee = getAuthenticatedEmployee();

        if (authEmployee.getRestaurant() == null ||
            !authEmployee.getRestaurant().getIdRestaurant()
                .equals(
                    assignment.getTable()
                        .getRestaurant()
                        .getIdRestaurant()
                )) {

            throw new ForbiddenException(
                "No puedes cerrar asignaciones de otro restaurante");
        }

        assignment.setEndTime(LocalDateTime.now());
        tableAssignmentRepository.save(assignment);

        return TableAssignmentMapper
            .convertirTableAssignmentDto(assignment);
    }

    @Override
    public List<TableAssignmentResponseDto> findAssignmentsByTable(
            Integer idTable) {

        Employee authEmployee = getAuthenticatedEmployee();

        TableEntity table = tableRepository.findById(idTable)
            .orElseThrow(() -> 
                new NotFoundException("Mesa no encontrada"));

        if (authEmployee.getRestaurant() == null ||
            !authEmployee.getRestaurant().getIdRestaurant()
                .equals(table.getRestaurant().getIdRestaurant())) {

            throw new ForbiddenException(
                "No puedes ver asignaciones de otro restaurante");
        }

        return tableAssignmentRepository.findByTable(table)
            .stream()
            .map(TableAssignmentMapper::convertirTableAssignmentDto)
            .toList();
    }

        private Employee getAuthenticatedEmployee() {

        String dni = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

        return employeeRepository.findByDni(dni)
            .orElseThrow(() -> 
                new UnauthorizedException("Usuario no autenticado"));
    }
}
