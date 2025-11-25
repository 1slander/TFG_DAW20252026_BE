package com.tfgbe.modelo.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_user")
	private int idUser;
	
	@Column(name="user_name")
	private String userName;
	
	private String surname;
	
	private String email;
	
	private String password;
	
	@Column(name="contracted_date")
	private LocalDate contractedDate;
	
	private boolean active;
	
	@Column(name="employee_type")
	private String employeeType;
	
	private Role role;
}
