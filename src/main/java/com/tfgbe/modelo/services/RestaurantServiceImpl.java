package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	
	@Override
	public Restaurant findById(String key) {
		return restaurantRepository.findById(key).orElse(null);
	}

	@Override
	public List<Restaurant> findAll() {
		return restaurantRepository.findAll();
	}

	@Override
	public Restaurant insertOne(Restaurant entity) {
		
		return restaurantRepository.save(entity);
	}

	@Override
	public Restaurant updateOne(Restaurant entity) {
		if (restaurantRepository.existsById(entity.getIdRestaurant()))
			return restaurantRepository.save(entity);
		else
			return null;
	}

	@Override
	public int deleteOne(String key) {
		if(restaurantRepository.existsById(key)) {
			try {
				restaurantRepository.deleteById(key);
				return 1;
			}catch(Exception e) {
				return -1;
			}
		}
		else
			return 0;
	}
	
}
