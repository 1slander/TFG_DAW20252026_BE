package com.tfgbe.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, String>{
	
}
