package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.ForbiddenException;
import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.exceptions.UnauthorizedException;
import com.tfgbe.mapper.TableMapper;
import com.tfgbe.modelo.dto.CreateTableDto;
import com.tfgbe.modelo.dto.TableResponseDto;
import com.tfgbe.modelo.dto.UpdateTableDto;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.entities.TableEntity;
import com.tfgbe.modelo.entities.TableStatus;
import com.tfgbe.modelo.repository.EmployeeRepository;
import com.tfgbe.modelo.repository.RestaurantRepository;
import com.tfgbe.modelo.repository.FloorRepository;
import com.tfgbe.modelo.repository.TableAssignmentRepository;
import com.tfgbe.modelo.repository.TableRepository;
import com.tfgbe.modelo.entities.Floor;

@Service
public class TableServiceImplJpaMy8 implements TableService {
	
	 @Autowired
    private TableRepository tableRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TableAssignmentRepository tableAssignmentRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Override
    public TableResponseDto createTable(Long idRestaurant, CreateTableDto dto) {

    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    Restaurant restaurant = restaurantRepository.findById(idRestaurant)
        .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));

    // Si no es admin, debe pertenecer al restaurante
    if (!isAdmin) {
        checkEmployeeBelongsToRestaurant(authEmployee, restaurant);
    }

    TableEntity table = new TableEntity();
    table.setTableNumber(dto.getTableNumber());
    table.setTableCapacity(dto.getTableCapacity());
    table.setRestaurant(restaurant);
    table.setStatus(TableStatus.NOT_BOOKED);
    table.setPosX(dto.getPosX() != null ? dto.getPosX() : 0);
    table.setPosY(dto.getPosY() != null ? dto.getPosY() : 0);

    if (dto.getIdFloor() != null) {
        Floor floor = floorRepository.findById(dto.getIdFloor())
            .orElseThrow(() -> new NotFoundException("Planta no encontrada"));
        table.setFloor(floor);
    }

    tableRepository.save(table);

    TableResponseDto responseDto = TableMapper.convertirTableDto(table);
    enrichWithAssignment(table, responseDto);
    return responseDto;
    }

    @Override
    public TableResponseDto updateTable(int idTable, UpdateTableDto dto) {
  TableEntity table = tableRepository.findById(idTable)
        .orElseThrow(() -> new NotFoundException("Mesa no encontrada"));

    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    if (!isAdmin) {
        checkEmployeeBelongsToRestaurant(
            authEmployee,
            table.getRestaurant()
        );
    }

    if (dto.getTableNumber() != null)
        table.setTableNumber(dto.getTableNumber());

    if (dto.getTableCapacity() != null)
        table.setTableCapacity(dto.getTableCapacity());

    if (dto.getStatus() != null)
        table.setStatus(dto.getStatus());

    if (dto.getIdFloor() != null) {
        Floor floor = floorRepository.findById(dto.getIdFloor())
            .orElseThrow(() -> new NotFoundException("Planta no encontrada"));
        table.setFloor(floor);
    }

    tableRepository.save(table);

    TableResponseDto responseDto = TableMapper.convertirTableDto(table);
    enrichWithAssignment(table, responseDto);
    return responseDto;
    }

    @Override
    public TableResponseDto findById(int idTable) {
        TableEntity table = tableRepository.findById(idTable)
            .orElseThrow(() -> new NotFoundException("Mesa no encontrada"));

        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        if (!isAdmin) {
            checkEmployeeBelongsToRestaurant(authEmployee, table.getRestaurant());
        }

        TableResponseDto dto = TableMapper.convertirTableDto(table);
        enrichWithAssignment(table, dto);
        return dto;
    }

    @Override
    public List<TableResponseDto> findAll() {
        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        List<TableEntity> tables;
        if (isAdmin) {
            tables = tableRepository.findAll();
        } else {
            if (authEmployee.getRestaurant() == null) {
                return List.of();
            }
            tables = tableRepository.findByRestaurant(authEmployee.getRestaurant());
        }

        return tables.stream()
            .map(table -> {
                TableResponseDto dto = TableMapper.convertirTableDto(table);
                enrichWithAssignment(table, dto);
                return dto;
            })
            .toList();
    }

    @Override
    public List<TableResponseDto> findByFloor(Integer idFloor) {
        Floor floor = floorRepository.findById(idFloor)
            .orElseThrow(() -> new NotFoundException("Planta no encontrada"));
            
        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");
        if (!isAdmin && (authEmployee.getRestaurant() == null || 
            !authEmployee.getRestaurant().getIdRestaurant().equals(floor.getRestaurant().getIdRestaurant()))) {
            throw new ForbiddenException("No tienes acceso a esta planta");
        }

        return tableRepository.findByFloor(floor)
            .stream()
            .map(table -> {
                TableResponseDto dto = TableMapper.convertirTableDto(table);
                enrichWithAssignment(table, dto);
                return dto;
            })
            .toList();
    }

    @Override
    public List<TableResponseDto> findByRestaurant(Long idRestaurant) {

       
    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    Restaurant restaurant = restaurantRepository.findById(idRestaurant)
        .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));

    if (!isAdmin) {
        checkEmployeeBelongsToRestaurant(authEmployee, restaurant);
    }

    return tableRepository.findByRestaurant(restaurant)
        .stream()
        .map(table -> {
            TableResponseDto dto = TableMapper.convertirTableDto(table);
            enrichWithAssignment(table, dto);
            return dto;
        })
        .toList();
    }

    @Override
    public int deleteTable(int idTable) {

    TableEntity table = tableRepository.findById(idTable)
        .orElse(null);

    if (table == null) {
        return 0;
    }

    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    if (!isAdmin) {
        checkEmployeeBelongsToRestaurant(
            authEmployee,
            table.getRestaurant()
        );
    }

    try {
        tableRepository.delete(table);
        return 1;
    } catch (Exception e) {
        
        return -1;
    }
    }

   

    private Employee getAuthenticatedEmployee() {

        String dni = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

        return employeeRepository.findByDni(dni)
            .orElseThrow(() -> new UnauthorizedException("Usuario no autenticado"));
    }

    private boolean hasAuthority(String role) {

        return SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .anyMatch(a -> a.getAuthority().equals(role));
    }

	@Override
	public List<TableResponseDto> findByStatus(TableStatus status) {
		
    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    List<TableEntity> tables;

    if (isAdmin) {
        tables = tableRepository.findByStatus(status);
    } else {

        if (authEmployee.getRestaurant() == null) {
            throw new ForbiddenException(
                "El empleado no tiene restaurante asignado");
        }

        tables = tableRepository.findByStatus(status)
            .stream()
            .filter(table ->
                table.getRestaurant().getIdRestaurant()
                    .equals(authEmployee.getRestaurant().getIdRestaurant()))
            .toList();
    }

    return tables.stream()
        .map(table -> {
            TableResponseDto dto = TableMapper.convertirTableDto(table);
            enrichWithAssignment(table, dto);
            return dto;
        })
        .toList();
	}

	@Override
	public boolean updateTableStatus(Integer tableId, TableStatus newStatus) {
		 TableEntity table = tableRepository.findById(tableId)
        .orElseThrow(() -> new NotFoundException("Mesa no encontrada"));

    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    if (!isAdmin) {
        if (authEmployee.getRestaurant() == null ||
            !authEmployee.getRestaurant().getIdRestaurant()
                .equals(table.getRestaurant().getIdRestaurant())) {

            throw new ForbiddenException(
                "No puedes modificar mesas de otro restaurante");
        }
    }

    table.setStatus(newStatus);
    tableRepository.save(table);

    return true;
	}

    private void checkEmployeeBelongsToRestaurant(
        Employee employee,
        Restaurant restaurant) {

    if (employee.getRestaurant() == null ||
        !employee.getRestaurant().getIdRestaurant()
            .equals(restaurant.getIdRestaurant())) {

        throw new ForbiddenException(
            "No puedes operar sobre mesas de otro restaurante");
    }
    }

    @Override
    public void updatePosition(Integer tableId, Integer posX, Integer posY) {
        TableEntity table = tableRepository.findById(tableId)
            .orElseThrow(() -> new NotFoundException("Mesa no encontrada"));

        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        if (!isAdmin) {
            checkEmployeeBelongsToRestaurant(authEmployee, table.getRestaurant());
        }

        table.setPosX(posX);
        table.setPosY(posY);

        tableRepository.save(table);
    }

    private void enrichWithAssignment(TableEntity table, TableResponseDto dto) {
    tableAssignmentRepository.findByTableAndEndTimeIsNull(table).ifPresent(assignment -> {
        dto.setIdAssignment(assignment.getIdAssignment());
        dto.setIdEmployee(assignment.getEmployee().getIdUser());
        dto.setEmployeeName(assignment.getEmployee().getFirstName() + " " + assignment.getEmployee().getLastName());
    });
    }

}
