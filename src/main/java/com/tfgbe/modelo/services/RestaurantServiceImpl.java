package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.repository.RestaurantRepository;

public class RestaurantServiceImpl implements RestaurantService{
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	
	@Override
	public Restaurant findById(String atributoId) {
		return restaurantRepository.findById(atributoId).orElse(null);
	}

	@Override
	public List<Restaurant> findAll() {
		return restaurantRepository.findAll();
	}

	@Override
	public Restaurant insertOne(Restaurant entidad) {
		
		return restaurantRepository.save(entidad);
	}

	@Override
	public Restaurant updateOne(Restaurant entidad) {
		if (restaurantRepository.existsById(entidad.getIdRestaurant()))
			return restaurantRepository.save(entidad);
		else
			return null;
	}

	@Override
	public int deleteOne(String atributoId) {
		if(restaurantRepository.existsById(atributoId)) {
			try {
				restaurantRepository.deleteById(atributoId);
				return 1;
			}catch(Exception e) {
				return -1;
			}
		}
		else
			return 0;
	}
	
}
