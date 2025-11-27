package com.tfgbe.modelo.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="employees")

public class Employee {

	@Id
	@Column(name="id_employee")
	private String idEmployee;
	
	@Column(name="hourly_wage")
	private double hourlyWage;
	
	@Column(name="hire_date")
	private LocalDate hireDate;
	
	private EmployeeType employeeType;
	
	@OneToOne //MUCHAS DUDAS SOBRE ESTO
	private User user;
	
	@ManyToOne
	@JoinColumn(name="id_restaurant")
	private Restaurant restaurant;
	
	/*
	@ManyToOne
	@JoinColumn(name="id_shift")
	private Shift shift;
	*/
}
