package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.dto.CreateShiftDto;
import com.tfgbe.modelo.dto.UpdateShiftDto;
import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.entities.Shift;
import com.tfgbe.modelo.services.ShiftService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/shifts")
public class ShiftRestController {

	@Autowired
	ShiftService shiftService;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(shiftService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{idShift}")
	public ResponseEntity<?> findById(@PathVariable int idShift){
		
			return new ResponseEntity<Shift>(shiftService.findById(idShift),HttpStatus.OK);
		
	}
	
	
	@PostMapping
	public ResponseEntity<?> postCreateShift(@Valid @RequestBody CreateShiftDto assignShift){
		Shift created = shiftService.createShift(assignShift);
	
			return new ResponseEntity<>(created, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/update/{idShift}")
	public ResponseEntity<?> updateOne(@PathVariable int idShift, @Valid @RequestBody UpdateShiftDto assignShift){
		
		return new ResponseEntity<>(shiftService.updateShift(idShift, assignShift),HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/delete/{idShift}")
	public ResponseEntity<?> deleteOne(@PathVariable int idShift){
		switch (shiftService.deleteShift(idShift)) {
		case 1:
			return new ResponseEntity<String>("DELETED", HttpStatus.OK);
		case 0:
			return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
		default:
			return new ResponseEntity<String>("CAN'T DELETE", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
	
	
}
