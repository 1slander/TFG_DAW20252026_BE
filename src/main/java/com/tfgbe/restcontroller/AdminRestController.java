package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.modelo.dto.AdminResponseDto;
import com.tfgbe.modelo.dto.CreateUserDto;
import com.tfgbe.modelo.entities.Admin;

import com.tfgbe.modelo.entities.Role;
import com.tfgbe.modelo.services.AdminService;
import com.tfgbe.modelo.services.RoleService;

import jakarta.validation.Valid;
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

	@PostMapping("/signup")
	public ResponseEntity<?> createAdmin(@RequestBody @Valid CreateUserDto admin){
		return new ResponseEntity<AdminResponseDto>(adminService.insertOne(admin),HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody CreateUserDto admin){
		
		return new ResponseEntity<>(adminService.authenticateAdmin(admin),HttpStatus.OK);
	}
	

	@DeleteMapping("/delete-admin/{id}")
	public ResponseEntity<?> deleteOneAdmin(@PathVariable int id){

		switch (adminService.deleteOne(id)) {
			case 1:
				return new ResponseEntity<>("Eliminado correctamente",HttpStatus.OK);
				
			case 0:
				return new ResponseEntity<>("Admin no existe",HttpStatus.NOT_FOUND);
				
		
			default:
				return new ResponseEntity<>("No se puede eliminar Admin",HttpStatus.BAD_REQUEST);
		}
		
	}
	

//* ROLES *//

	// GET ALL

	@GetMapping("/roles")
	public ResponseEntity<?> getAllRoles() {
		return new ResponseEntity<List<Role>>(roleService.findAll(),HttpStatus.OK);
	}

	// GET BY ID

	@GetMapping("/roles/{idRole}")
	public ResponseEntity<?> getOneById(@PathVariable int idRole){
		Role role = roleService.findById(idRole);
		if(role==null){
			throw new NotFoundException("No se ha encontrado role con esa id: " + idRole);
		}
		return new ResponseEntity<Role>(role,HttpStatus.OK);
	}
	

	// CREACION DE ROLES

	
	
	@PostMapping("/crear-role")
	public ResponseEntity<?> insertOneRole(@RequestBody Role role){
		return new ResponseEntity<Role>(roleService.insertOne(role),HttpStatus.CREATED);
	}




// DELETE ROLE

@DeleteMapping("/roles/{idRole}")
	public ResponseEntity<?> deleteOneRole(@PathVariable int idRole){
		
		switch(roleService.deleteOne(idRole)){
			case 1:
				return new ResponseEntity<>("Eliminado correctamente",HttpStatus.OK);
				
			case 0:
				return new ResponseEntity<>("Role no existe",HttpStatus.NOT_FOUND);
				
		
			default:
				return new ResponseEntity<>("No se puede eliminar Role",HttpStatus.BAD_REQUEST);

		}
	}
}