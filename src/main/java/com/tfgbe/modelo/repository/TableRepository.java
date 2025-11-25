package com.tfgbe.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Table;

public interface TableRepository extends JpaRepository<Table, Integer> {

}
