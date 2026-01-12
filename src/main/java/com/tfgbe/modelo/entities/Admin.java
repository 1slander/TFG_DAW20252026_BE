package com.tfgbe.modelo.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import jakarta.persistence.criteria.CriteriaBuilder.In;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name="admins")

public class Admin{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_admin")
	private int idAdmin;

	private String username;
	
	private String password;

	@Column(name="role_name")
	private String roleName;
	
	


	
}
