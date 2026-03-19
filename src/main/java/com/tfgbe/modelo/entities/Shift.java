package com.tfgbe.modelo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="shifts", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"assign_shift", "id_restaurant"})
})

public class Shift {

	@Id
	@Column(name="id_shift")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int idShift;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "assign_shift", nullable = false)
    private ShiftType assignShift;

	@ManyToOne(optional = false)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;
}
