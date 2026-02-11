package com.tfgbe.modelo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.entities.Shift;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    Optional<Employee> findByDni(String dni);
    List<Employee> findByRestaurant(Restaurant restaurant);
    List<Employee> findByShift(Shift shift);
}
