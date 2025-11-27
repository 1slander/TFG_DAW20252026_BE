package com.tfgbe.modelo.entities;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="shifts")

public class Shift {

	@Id
	@Column(name="id_shift")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int idUser;
	
	@Column(name="assing_shift")
	public String assignShift;
}
