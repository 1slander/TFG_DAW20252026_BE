package com.tfgbe.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.TableAssignment;

public interface TableAssignmentRepository extends JpaRepository<TableAssignment, Integer> {
	
}
