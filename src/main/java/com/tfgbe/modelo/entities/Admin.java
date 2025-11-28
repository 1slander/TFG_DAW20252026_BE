package com.tfgbe.modelo.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name="admins")
@PrimaryKeyJoinColumn(name="id_user")
public class Admin extends User {

	@Column(name="can_create_admins")
	private boolean canCreateAdmins;
	
	//DUDAS => Aqui no hace falta porque por herencia extendemos de USER.
	//@OneToOne
	//private User user;
	
	//NO SE SI LO HEMOS HECHO
	// Aqui si un Admin tiene muchos restaurante tiene que ser una Lista
	@OneToMany(mappedBy="admin")
	@JsonIgnore
	private List<Restaurant> restaurants;
}
