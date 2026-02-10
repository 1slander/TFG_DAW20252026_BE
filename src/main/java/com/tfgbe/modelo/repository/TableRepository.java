package com.tfgbe.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.TableStatus;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.entities.TableEntity;

public interface TableRepository extends JpaRepository<TableEntity, Integer> {
	
	List <TableEntity> findByStatus(TableStatus status);
	List<TableEntity> findByRestaurant(Restaurant restaurant);
	
}
