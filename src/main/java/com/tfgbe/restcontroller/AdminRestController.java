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

import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.modelo.dto.AdminResponseDto;
import com.tfgbe.modelo.entities.Admin;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.Role;
import com.tfgbe.modelo.services.AdminService;
import com.tfgbe.modelo.services.RoleService;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin")
public class AdminRestController {

	@Autowired
	AdminService adminService;

	@Autowired
	RoleService roleService;	
	
	
	@GetMapping
	public ResponseEntity<?> findAll(){

		return new ResponseEntity<List<AdminResponseDto>>(adminService.findAll(),HttpStatus.OK);
	}


	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id){
		AdminResponseDto admin = adminService.findById(id);
		if(admin == null){
			throw new NotFoundException("No se encontro admin con la ID: " + id);
		}

		return new ResponseEntity<AdminResponseDto>(admin,HttpStatus.OK);
	}
	
	// CREACION DE ROLES
	
	@PostMapping("/crear-role")
	public ResponseEntity<?> insertOneRole(@RequestBody Role role){
		return new ResponseEntity<Role>(roleService.insertOne(role),HttpStatus.CREATED);
	}
}
