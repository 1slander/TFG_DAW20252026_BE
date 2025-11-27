package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.services.RestaurantService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/restaurant")
public class RestaurantRestController {
	
	
	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping("/")
	public ResponseEntity<List<Restaurant>> findAll(){
		return ResponseEntity.status(200).body(restaurantService.findAll());
		
	}
	
	@GetMapping("/{idRestaurant}")
    public ResponseEntity<Restaurant> findById(@PathVariable String idRestaurant){
        
        Restaurant restaurant = restaurantService.findById(idRestaurant);
        
        if (restaurant != null) {
            return ResponseEntity.status(200).body(restaurant); // 200 OK
        } else {
            return ResponseEntity.status(404).body(null); // 404 Not Found
        }
    }
	
	@PostMapping("/")
    public ResponseEntity<Restaurant> insertOne(@RequestBody Restaurant restaurant){
        
        Restaurant newRestaurant = restaurantService.insertOne(restaurant);
        
        if (newRestaurant != null) {
             return ResponseEntity.status(201).body(newRestaurant); 
        }
        return ResponseEntity.status(400).body(null); 
    }
	
	@PutMapping("/{idRestaurant}")
    public ResponseEntity<?> updateOne(@PathVariable String idRestaurant,
                                     @RequestBody Restaurant restaurant){
        
        restaurant.setIdRestaurant(idRestaurant); 
        
        if ( restaurantService.updateOne(restaurant) != null) {
            return ResponseEntity.status(200).body(restaurant); // 200 OK
        } else {
            return ResponseEntity.status(404).body("Restaurante no encontrado para actualizar"); // 404 Not Found
        }
    }
	
	@DeleteMapping("/{idRestaurant}")
    public ResponseEntity<String> deleteOne(@PathVariable String idRestaurant){
        
        switch(restaurantService.deleteOne(idRestaurant)) {
        case 1:
            return ResponseEntity.status(200).body("Restaurante eliminado con éxito.");
        case 0:
            return ResponseEntity.status(404).body("Restaurante no existe.");
        case -1:
            return ResponseEntity.status(400).body("Restaurante no se puede eliminar. Hay elementos asociados (mesas, pedidos, etc.)");
        default:
            return ResponseEntity.status(500).body("Error interno al intentar eliminar.");
        }
    }
	
	
	
	
}
