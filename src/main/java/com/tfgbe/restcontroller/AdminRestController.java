package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.entities.Admin;
import com.tfgbe.modelo.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

	@Autowired
	AdminService adminService;
	
	@GetMapping("/all")
	public List<Admin> findAll(){
		return adminService.findAll();
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insertOne(@RequestBody Admin admin){
		  if (adminService.updateOne(admin)!= null) {
			  return new ResponseEntity<Admin>(admin,HttpStatus.OK);
		  } else {
			  return new ResponseEntity<String>("INSERT ERROR", HttpStatus.BAD_REQUEST);
		  }
	}
	
	@PutMapping ("/update/{id}")
	public ResponseEntity<?> updateOne(@RequestBody Admin admin){
		if(adminService.updateOne(admin)!= null) {
			return new ResponseEntity<Admin>(admin, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("USER NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
}
