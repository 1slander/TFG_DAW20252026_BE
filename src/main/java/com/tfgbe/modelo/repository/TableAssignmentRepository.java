package com.tfgbe.modelo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.TableAssignment;
import com.tfgbe.modelo.entities.TableEntity;

public interface TableAssignmentRepository extends JpaRepository<TableAssignment, Integer> {

    Optional<TableAssignment> findByTableAndEndTimeIsNull(
        TableEntity table
    );

     List<TableAssignment> findByTable(TableEntity table);
	
}
