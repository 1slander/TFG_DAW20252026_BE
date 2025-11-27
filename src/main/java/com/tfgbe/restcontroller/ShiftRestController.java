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

import com.tfgbe.modelo.entities.Shift;
import com.tfgbe.modelo.services.ShiftService;

@RestController
@RequestMapping("/shift")
public class ShiftRestController {

	@Autowired
	ShiftService shiftService;
	
	@GetMapping("/all")
	public List<Shift> findAll(){
		return shiftService.findAll();
	}
	
	
	@GetMapping("/byId/{id}")
	public ResponseEntity<?> findById(@PathVariable int id){
		Shift s = shiftService.findById(id);
		if (s != null) {
			return new ResponseEntity<Shift>(s,HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("SHIFT NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping("/insert")
	public ResponseEntity<?> insertOne(@RequestBody Shift shift){
		if (shiftService.insertOne(shift)!=null) {
			return new ResponseEntity<Shift>(shift, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("INSERT ERROR", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> updateOne(@RequestBody Shift shift){
		if(shiftService.updateOne(shift)!= null) {
			return new ResponseEntity<Shift>(shift,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("INSERT ERROR", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){
		switch (shiftService.deleteOne(id)) {
		case 1:
			return new ResponseEntity<String>("DELETED", HttpStatus.OK);
		case 0:
			return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
		default:
			return new ResponseEntity<String>("CAN'T DELETE", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
	
	
}
