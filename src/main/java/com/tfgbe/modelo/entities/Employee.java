package com.tfgbe.modelo.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Table;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="employees")

@PrimaryKeyJoinColumn(name="id_user")
public class Employee extends User  {

	
	@Column(nullable = false,unique = true,length = 9)
	private String dni;
	
	@Column(name="hourly_wage")
	private double hourlyWage;
	
	@Column(name="hire_date")
	private LocalDate hireDate;

	
	@ManyToOne
	@JoinColumn(name="id_role")
	private Role role;
	

	
	@ManyToOne
	@JoinColumn(name="id_restaurant")
	private Restaurant restaurant;
	
	
	@ManyToOne
	@JoinColumn(name="id_shift")
	private Shift shift;
	
}
