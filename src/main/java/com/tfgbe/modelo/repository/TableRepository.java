package com.tfgbe.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfgbe.modelo.entities.Floor;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.entities.TableEntity;
import com.tfgbe.modelo.entities.TableStatus;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Integer> {
	
	List<TableEntity> findByStatus(TableStatus status);
	List<TableEntity> findByRestaurant(Restaurant restaurant);
	List<TableEntity> findByFloor(Floor floor);
	
}
