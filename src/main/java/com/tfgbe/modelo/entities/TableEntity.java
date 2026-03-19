package com.tfgbe.modelo.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "tables")
public class TableEntity {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name="id_table")
	private int idTable;
	
	@Column(name="table_number")
	private int tableNumber;
	
	@Column(name="table_capacity")
	private int tableCapacity;
	
	
	@ManyToOne
	@JoinColumn(name="id_restaurant", nullable = false)
	private Restaurant restaurant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_floor")
	private Floor floor;
	
	@Enumerated(EnumType.STRING)
	private TableStatus status;

	@Column(name = "pos_x")
	private Integer posX;

	@Column(name = "pos_y")
	private Integer posY;

	@OneToMany(mappedBy = "table", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
	private java.util.List<TableAssignment> assignments;
}
