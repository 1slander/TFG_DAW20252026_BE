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
import com.tfgbe.modelo.entities.User;
import com.tfgbe.modelo.services.UserService;



@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	UserService userService;

	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		return ResponseEntity.status(200).body(userService.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id){
		User u = userService.findById(id);
		if(u!=null) {
			return new ResponseEntity<User>(u,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("USER NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	@PostMapping("/insert")
	public ResponseEntity<?> insertOne(@RequestBody User user){
		if (userService.insertOne(user)!=null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("INSERT ERROR", HttpStatus.BAD_REQUEST);
		}
	}
	*/
	
	/*
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateOne(@RequestBody User user){
		if (userService.updateOne(user)!= null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("USER NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
	*/
	
	/*
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){
		switch (userService.deleteOne(id)) {
		case 1:
			return new ResponseEntity<String>("DELETED", HttpStatus.OK);
		case 0:
			return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
		default:
			return new ResponseEntity<String>("CAN'T DELETE", HttpStatus.BAD_REQUEST);
		}
	}
	*/
	
	
	
}
