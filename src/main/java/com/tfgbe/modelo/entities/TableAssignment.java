package com.tfgbe.modelo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.tfgbe.modelo.entities.TableEntity;


@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity
@Table(name = "table_assignment")

public class TableAssignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ASSIGNMENT")
	private int idAssignment;
	
	@Column(name = "START_TIME", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime; 
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "ID_TABLE")
    private TableEntity table;

    
     @ManyToOne(optional = false)
    @JoinColumn(name = "ID_EMPLOYEE", nullable = false)
    private Employee employee;
}
