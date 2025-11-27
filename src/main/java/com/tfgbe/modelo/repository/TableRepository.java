package com.tfgbe.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Status;
import com.tfgbe.modelo.entities.Table;

public interface TableRepository extends JpaRepository<Table, Integer> {
	
	List <Table> findByStatus(Status status);
	
}
