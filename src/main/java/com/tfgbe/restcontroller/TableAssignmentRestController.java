package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.TableAssignment;
import com.tfgbe.modelo.services.TableAssignmentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/assignments")
public class TableAssignmentRestController {
	
	
	@Autowired
	private TableAssignmentService assignmentService; 
	
	@GetMapping
	public ResponseEntity<List<TableAssignment>> findAll(){
		return ResponseEntity.status(200).body(assignmentService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id){
		TableAssignment t = assignmentService.findById(id);
		if (t!= null) {
			return new ResponseEntity<TableAssignment>(t,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("TABLE ASSIGNMENT NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping
	public ResponseEntity<?> insertOne(@RequestBody TableAssignment tableAssignment){
		if (assignmentService.insertOne(tableAssignment)!= null) {
			return new ResponseEntity<TableAssignment>(tableAssignment,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("INSERT ERROR", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateOne(@PathVariable int id, @RequestBody TableAssignment tableAssignment){
		
		tableAssignment.setIdAssigment(id);
		
		if (assignmentService.updateOne(tableAssignment)!= null) {
			return new ResponseEntity<TableAssignment>(tableAssignment, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("TABLE ASSIGNMENT NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){
		switch (assignmentService.deleteOne(id)) {
		case 1:
			return new ResponseEntity<String>("DELETED", HttpStatus.OK);
		case 0:
			return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
		default:
			return new ResponseEntity<String>("CAN'T DELETE", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
}
