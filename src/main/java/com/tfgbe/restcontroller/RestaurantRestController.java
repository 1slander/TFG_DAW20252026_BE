package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.tfgbe.modelo.dto.AssignEmployeeRestaurantDto;
import com.tfgbe.modelo.dto.CreateRestaurantDto;
import com.tfgbe.modelo.dto.RestaurantResponseDto;
import com.tfgbe.modelo.dto.UpdateRestaurantDto;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.services.RestaurantService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/restaurant")
public class RestaurantRestController {
	
	
	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return  new ResponseEntity<>(restaurantService.findAll(),HttpStatus.OK);
		
	}
	
	@GetMapping("/{idRestaurant}")
    public ResponseEntity<?> findById(@PathVariable Long idRestaurant){
        
        RestaurantResponseDto restaurant = restaurantService.findById(idRestaurant);
        
        if (restaurant != null) {
            return new ResponseEntity<>(restaurant,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping
    public ResponseEntity<?> insertOne(@RequestBody CreateRestaurantDto restaurant){
        
      return new ResponseEntity<>(restaurantService.createRestaurant(restaurant),HttpStatus.CREATED);
    }
	
	@PutMapping("update/{idRestaurant}")
    public ResponseEntity<?> updateOne(@PathVariable Long idRestaurant,
                                     @RequestBody UpdateRestaurantDto restaurant){
        
        return new ResponseEntity<>(restaurantService.updateRestaurant(idRestaurant, restaurant),HttpStatus.OK);
    }
	
	@DeleteMapping("delete/{idRestaurant}")
    public ResponseEntity<?> deleteOne(@PathVariable Long idRestaurant){
        
        switch(restaurantService.deleteRestaurant(idRestaurant)) {
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
	
	@GetMapping("/my")
public ResponseEntity<?> getMyRestaurant() {
    return new ResponseEntity<>(restaurantService.findMyRestaurant(), HttpStatus.OK);
}
	

@PutMapping("/assign")
public ResponseEntity<?> assignEmployeeToRestaurant(
        @RequestBody AssignEmployeeRestaurantDto dto) {

    return new ResponseEntity<>(
        restaurantService.assignEmployeeToRestaurant(dto),
        HttpStatus.OK
    );
}
	
}
