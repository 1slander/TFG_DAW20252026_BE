package com.tfgbe.modelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    Optional<Employee> findByDni(String dni);
}
