package com.tfgbe.modelo.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.tfgbe.exceptions.ForbiddenException;
import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.exceptions.UnauthorizedException;
import com.tfgbe.mapper.FloorMapper;
import com.tfgbe.modelo.dto.FloorResponseDto;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.Floor;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.repository.EmployeeRepository;
import com.tfgbe.modelo.repository.FloorRepository;
import com.tfgbe.modelo.repository.RestaurantRepository;

@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<FloorResponseDto> findByRestaurant(Long idRestaurant) {
        Restaurant restaurant = restaurantRepository.findById(idRestaurant)
            .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
        
        checkPermission(restaurant);
        
        List<Floor> floors = floorRepository.findByRestaurant(restaurant);
        
        if (floors.isEmpty()) {
            Floor defaultFloor = Floor.builder()
                .name("Planta Principal")
                .restaurant(restaurant)
                .build();
            floorRepository.save(defaultFloor);
            return List.of(FloorMapper.toDto(defaultFloor));
        }
        
        return floors.stream()
            .map(FloorMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public FloorResponseDto createFloor(Long idRestaurant, String name) {
        Restaurant restaurant = restaurantRepository.findById(idRestaurant)
            .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
            
        checkPermission(restaurant);
        
        Floor floor = Floor.builder()
            .name(name)
            .restaurant(restaurant)
            .build();
            
        return FloorMapper.toDto(floorRepository.save(floor));
    }

    @Override
    public FloorResponseDto updateFloor(Integer idFloor, String name) {
        Floor floor = floorRepository.findById(idFloor)
            .orElseThrow(() -> new NotFoundException("Planta no encontrada"));
            
        checkPermission(floor.getRestaurant());
        
        floor.setName(name);
        return FloorMapper.toDto(floorRepository.save(floor));
    }

    @Override
    public void deleteFloor(Integer idFloor) {
        Floor floor = floorRepository.findById(idFloor)
            .orElseThrow(() -> new NotFoundException("Planta no encontrada"));
            
        checkPermission(floor.getRestaurant());
        
        floorRepository.delete(floor);
    }

    @Override
    public FloorResponseDto findById(Integer idFloor) {
        Floor floor = floorRepository.findById(idFloor)
            .orElseThrow(() -> new NotFoundException("Planta no encontrada"));
            
        checkPermission(floor.getRestaurant());
        
        return FloorMapper.toDto(floor);
    }

    private void checkPermission(Restaurant restaurant) {
        String dni = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeRepository.findByDni(dni)
            .orElseThrow(() -> new UnauthorizedException("Usuario no autenticado"));
            
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
        if (!isAdmin && (employee.getRestaurant() == null || 
            !employee.getRestaurant().getIdRestaurant().equals(restaurant.getIdRestaurant()))) {
            throw new ForbiddenException("No tienes permiso sobre este restaurante");
        }
    }
}
