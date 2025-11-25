package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.entities.User;
import com.tfgbe.modelo.services.UserService;



@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	UserService userService;

	
	@GetMapping("/all")
	public List<User> findAll(){
		return userService.findAll();
	}
	
	@GetMapping("/byId/{id}")
	public ResponseEntity<?> findById(@PathVariable int id){
		User u = userService.findById(id);
		if(u!=null) {
			return new ResponseEntity<User>(u,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("USER NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
}
