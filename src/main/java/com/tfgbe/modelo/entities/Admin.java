package com.tfgbe.modelo.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.Table;


@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="admins")

public class Admin{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_admin")
	private Integer idAdmin;

	@Column(nullable = false,unique = true)
	private String username;
	@Column(nullable = false,unique = true)
	private String email;
	@Column(nullable = false)
	private String password;

	// @Builder.Default
	// @Column(name="role_name", nullable = false)
	// private String roleName ="ROLE_ADMIN";
	@Enumerated(EnumType.STRING)
	@Column(name="role_name", nullable = false)
	private AdminRole role;
	
	


	
}
