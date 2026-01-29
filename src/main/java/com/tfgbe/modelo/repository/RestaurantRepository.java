package com.tfgbe.modelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
    boolean existsByOwner(Employee owner);
    Optional<Restaurant> findByOwner(Employee owner);
	
}
