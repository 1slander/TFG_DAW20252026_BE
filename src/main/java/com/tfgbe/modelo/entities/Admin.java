package com.tfgbe.modelo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="admins")
public class Admin {

	@Column(name="can_create_admins")
	private boolean canCreateAdmins;
	
	//DUDAS
	@OneToOne
	private User user;
	
	//NO SE SI LO HEMOS HECHO
	@OneToMany
	private Restaurant restaurant;
}
