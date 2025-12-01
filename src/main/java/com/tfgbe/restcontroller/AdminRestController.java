package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.entities.Admin;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

	@Autowired
	AdminService adminService;
	
	
	// Aqui tendriamos que decidir si queremos que se vean o no
	@GetMapping
	public List<Admin> findAll(){
		return adminService.findAll();
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id){
		Admin a = adminService.findById(id);
		if (a!= null) {
			return new ResponseEntity<Admin>(a,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("ADMIN NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertOne(@RequestBody Admin admin){
		  if (adminService.updateOne(admin)!= null) {
			  return new ResponseEntity<Admin>(admin,HttpStatus.OK);
		  } else {
			  return new ResponseEntity<String>("INSERT ERROR", HttpStatus.BAD_REQUEST);
		  }
	}
	
	@PutMapping ("/update/{id}")
	public ResponseEntity<?> updateOne(@PathVariable int id, @RequestBody Admin admin){
		
		admin.setIdUser(id);
		
		if(adminService.updateOne(admin)!= null) {
			return new ResponseEntity<Admin>(admin, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("USER NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){
		switch (adminService.deleteOne(id)) {
		case 1:
			return new ResponseEntity<String>("DELETED", HttpStatus.OK);
		case 0:
			return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
		default:
			return new ResponseEntity<String>("CAN'T DELETE", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
}
