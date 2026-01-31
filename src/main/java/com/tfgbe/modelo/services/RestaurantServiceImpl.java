package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.ForbiddenException;
import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.exceptions.UnauthorizedException;
import com.tfgbe.mapper.EmployeeMapper;
import com.tfgbe.mapper.RestaurantMapper;
import com.tfgbe.modelo.dto.AssignEmployeeRestaurantDto;
import com.tfgbe.modelo.dto.CreateRestaurantDto;
import com.tfgbe.modelo.dto.EmployeeResponseDto;
import com.tfgbe.modelo.dto.RestaurantResponseDto;
import com.tfgbe.modelo.dto.UpdateRestaurantDto;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.repository.EmployeeRepository;
import com.tfgbe.modelo.repository.RestaurantRepository;
import com.tfgbe.util.RoleUtils;
import com.tfgbe.util.RolesEnum;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
    private EmployeeRepository employeeRepository;


	@Override
	public RestaurantResponseDto createRestaurant(CreateRestaurantDto dto) {
		 Employee owner = getAuthenticatedEmployee();

        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        // Si NO es admin, solo OWNER puede crear restaurante
        if (!isAdmin && RoleUtils.roleNormalizer(owner.getRole().getRoleName()) != RolesEnum.OWNER) {
            throw new ForbiddenException("Solo un OWNER puede crear un restaurante");
        }

        // Un owner solo puede tener un restaurante
        if (!isAdmin && restaurantRepository.existsByOwner(owner)) {
            throw new ForbiddenException("Este owner ya tiene un restaurante asignado");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setCif(dto.getCif());
        restaurant.setRestaurantName(dto.getRestaurantName());
        restaurant.setAddress(dto.getAddress());
        restaurant.setCountry(dto.getCountry());
        restaurant.setPhone(dto.getPhone());
        restaurant.setCapacity(dto.getCapacity());
        restaurant.setTotalTables(dto.getTotalTables());
        restaurant.setOwner(owner);

        restaurantRepository.save(restaurant);

        return RestaurantMapper.convertirRestaurantDto(restaurant);
	}

	@Override
	public RestaurantResponseDto updateRestaurant(Long idRestaurant, UpdateRestaurantDto dto) {
		Restaurant restaurant = restaurantRepository.findById(idRestaurant)
            .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));

        Employee authEmployee = getAuthenticatedEmployee();
        boolean isAdmin = hasAuthority("ROLE_ADMIN");

        // Solo admin o owner del restaurante
        if (!isAdmin && restaurant.getOwner().getIdUser()!=(authEmployee.getIdUser())) {
            throw new ForbiddenException("No puedes modificar este restaurante");
        }

        if (dto.getRestaurantName() != null)
            restaurant.setRestaurantName(dto.getRestaurantName());

        if (dto.getAddress() != null)
            restaurant.setAddress(dto.getAddress());

        if (dto.getCountry() != null)
            restaurant.setCountry(dto.getCountry());

        if (dto.getPhone() != null)
            restaurant.setPhone(dto.getPhone());

        if (dto.getCapacity() != null)
            restaurant.setCapacity(dto.getCapacity());

        if (dto.getTotalTables() != null)
            restaurant.setTotalTables(dto.getTotalTables());

        restaurantRepository.save(restaurant);

        return RestaurantMapper.convertirRestaurantDto(restaurant);
	}

	@Override
	public RestaurantResponseDto findById(Long idRestaurant) {
		return restaurantRepository.findById(idRestaurant)
            .map(RestaurantMapper::convertirRestaurantDto)
            .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
	}

	@Override
	public List<RestaurantResponseDto> findAll() {
		return restaurantRepository.findAll()
            .stream()
            .map(RestaurantMapper::convertirRestaurantDto)
            .toList();
	}

	@Override
	public int deleteRestaurant(Long idRestaurant) {
		
        Restaurant restaurant = restaurantRepository.findById(idRestaurant)
            .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));

        if (!hasAuthority("ROLE_ADMIN")) {
            throw new ForbiddenException("Solo un ADMIN puede eliminar restaurantes");
        }

        restaurantRepository.deleteById(idRestaurant);
        return 1;
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
public RestaurantResponseDto findMyRestaurant() {

    Employee employee = getAuthenticatedEmployee();

    Restaurant restaurant = restaurantRepository.findByOwner(employee)
        .orElseThrow(() -> new NotFoundException("Este usuario no tiene restaurante asignado"));

    return RestaurantMapper.convertirRestaurantDto(restaurant);
}

@Override
public EmployeeResponseDto assignEmployeeToRestaurant(AssignEmployeeRestaurantDto dto) {

    Employee authEmployee = getAuthenticatedEmployee();
    boolean isAdmin = hasAuthority("ROLE_ADMIN");

    Employee employee = employeeRepository.findByDni(dto.getDniEmployee())
        .orElseThrow(() -> new NotFoundException(
            "Empleado con DNI " + dto.getDniEmployee() + " no existe"));

    Restaurant restaurant = restaurantRepository.findById(dto.getIdRestaurant())
        .orElseThrow(() -> new NotFoundException(
            "Restaurante con id " + dto.getIdRestaurant() + " no existe"));

    // Si no es admin, debe ser owner del restaurante
    if (!isAdmin) {
        if (authEmployee.getRestaurant() == null ||
            !restaurant.getOwner().getDni()
                .equals(authEmployee.getDni())) {

            throw new ForbiddenException(
                "No puedes asignar empleados a un restaurante que no es tuyo");
        }
    }

    employee.setRestaurant(restaurant);
    employeeRepository.save(employee);

    return EmployeeMapper.convertirEmployeeDto(employee);
}
	
	
}
