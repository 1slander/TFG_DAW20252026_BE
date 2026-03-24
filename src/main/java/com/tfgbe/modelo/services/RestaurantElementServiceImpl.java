package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.ForbiddenException;
import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.exceptions.UnauthorizedException;
import com.tfgbe.mapper.ElementMapper;
import com.tfgbe.modelo.dto.CreateElementDto;
import com.tfgbe.modelo.dto.ElementResponseDto;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.Floor;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.entities.RestaurantElement;
import com.tfgbe.modelo.repository.EmployeeRepository;
import com.tfgbe.modelo.repository.FloorRepository;
import com.tfgbe.modelo.repository.RestaurantElementRepository;
import com.tfgbe.modelo.repository.RestaurantRepository;
import com.tfgbe.util.RoleUtils;
import com.tfgbe.util.RolesEnum;

@Service
public class RestaurantElementServiceImpl implements RestaurantElementService {

    @Autowired
    private RestaurantElementRepository elementRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Override
    public ElementResponseDto createElement(Long idRestaurant, CreateElementDto dto) {
        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        Restaurant restaurant = restaurantRepository.findById(idRestaurant)
            .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));

        if (!isAdmin) {
            checkEmployeeBelongsToRestaurant(authEmployee, restaurant);

            RolesEnum rol = RoleUtils.roleNormalizer(authEmployee.getRole().getRoleName());
            if (rol.getNivel() < 4) {
                throw new ForbiddenException("Solo el OWNER puede crear elementos decorativos");
            }
        }

        RestaurantElement element = new RestaurantElement();
        element.setType(dto.getType());
        element.setPosX(dto.getPosX());
        element.setPosY(dto.getPosY());
        element.setWidth(dto.getWidth());
        element.setHeight(dto.getHeight());
        element.setRotation(dto.getRotation() != null ? dto.getRotation() : 0);
        element.setRestaurant(restaurant);

        if (dto.getIdFloor() != null) {
            Floor floor = floorRepository.findById(dto.getIdFloor())
                .orElseThrow(() -> new NotFoundException("Planta no encontrada"));
            element.setFloor(floor);
        }

        elementRepository.save(element);

        return ElementMapper.convertirElementDto(element);
    }

    @Override
    public List<ElementResponseDto> findByRestaurant(Long idRestaurant) {
        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        Restaurant restaurant = restaurantRepository.findById(idRestaurant)
            .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));

        if (!isAdmin) {
            checkEmployeeBelongsToRestaurant(authEmployee, restaurant);
        }

        return elementRepository.findByRestaurant(restaurant)
            .stream()
            .map(ElementMapper::convertirElementDto)
            .toList();
    }

    @Override
    public List<ElementResponseDto> findByFloor(Integer idFloor) {
        Floor floor = floorRepository.findById(idFloor)
            .orElseThrow(() -> new NotFoundException("Planta no encontrada"));
            
        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");
        if (!isAdmin && (authEmployee.getRestaurant() == null || 
            !authEmployee.getRestaurant().getIdRestaurant().equals(floor.getRestaurant().getIdRestaurant()))) {
            throw new ForbiddenException("No tienes acceso a esta planta");
        }

        return elementRepository.findByFloor(floor)
            .stream()
            .map(ElementMapper::convertirElementDto)
            .toList();
    }

    @Override
    public int deleteElement(Long idElement) {
        RestaurantElement element = elementRepository.findById(idElement).orElse(null);

        if (element == null) return 0;

        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        if (!isAdmin) {
            checkEmployeeBelongsToRestaurant(authEmployee, element.getRestaurant());

            RolesEnum rol = RoleUtils.roleNormalizer(authEmployee.getRole().getRoleName());
            if (rol.getNivel() < 4) {
                throw new ForbiddenException("Solo el OWNER puede eliminar elementos decorativos");
            }
        }

        try {
            elementRepository.delete(element);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void updatePosition(Long idElement, Integer posX, Integer posY, Integer rotation) {
        RestaurantElement element = elementRepository.findById(idElement)
            .orElseThrow(() -> new NotFoundException("Elemento no encontrado"));

        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        if (!isAdmin) {
            checkEmployeeBelongsToRestaurant(authEmployee, element.getRestaurant());

            RolesEnum rol = RoleUtils.roleNormalizer(authEmployee.getRole().getRoleName());
            if (rol.getNivel() < 4) {
                throw new ForbiddenException("Solo el OWNER puede mover elementos decorativos");
            }
        }

        element.setPosX(posX);
        element.setPosY(posY);
        if (rotation != null) {
            element.setRotation(rotation);
        }

        elementRepository.save(element);
    }


    @Override
    public ElementResponseDto updateDimensions(Long idElement, Integer width, Integer height) {
        RestaurantElement element = elementRepository.findById(idElement)
            .orElseThrow(() -> new NotFoundException("Elemento no encontrado"));

        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        if (!isAdmin) {
            checkEmployeeBelongsToRestaurant(authEmployee, element.getRestaurant());

            RolesEnum rol = RoleUtils.roleNormalizer(authEmployee.getRole().getRoleName());
            if (rol.getNivel() < 4) {
                throw new ForbiddenException("Solo el OWNER puede redimensionar elementos");
            }
        }

        element.setWidth(width);
        element.setHeight(height);

        elementRepository.save(element);

        return ElementMapper.convertirElementDto(element);
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

    private void checkEmployeeBelongsToRestaurant(Employee employee, Restaurant restaurant) {
        if (employee.getRestaurant() == null ||
            !employee.getRestaurant().getIdRestaurant().equals(restaurant.getIdRestaurant())) {
            throw new ForbiddenException("No puedes operar sobre elementos de otro restaurante");
        }
    }
}
