package com.tfgbe.modelo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.tfgbe.modelo.entities.Table;

@Getter
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity(name = "table_assignment")

public class TableAssignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ASSIGMENT")
	private int idAssigment;
	
	@Column(name = "START_TIME", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime; // Puede ser NULL si el turno está activo
	
	@ManyToOne
    @JoinColumn(name = "ID_TABLE", nullable = false)
    private Table table;

    
    @ManyToOne
    @JoinColumn(name = "ID_EMPLOYEE", nullable = false)
    private Employee employee;
}
