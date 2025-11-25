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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name="tables")
public class Table {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name="id_table")
	private int idTable;
	
	@Column(name="table_number")
	private int tableNumber;
	
	private int capacity;
	
	
	@ManyToOne
	@JoinColumn(name="id_restaurant")
	private Restaurant idRestaurant;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	

}
