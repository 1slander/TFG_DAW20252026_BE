package com.tfgbe.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{

}
