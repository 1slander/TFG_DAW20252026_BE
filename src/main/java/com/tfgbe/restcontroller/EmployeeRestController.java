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

import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.services.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/all")
	public List<Employee> findAll(){
		return employeeService.findAll();
	}
	
	@GetMapping("/byId/{id}")
	public ResponseEntity<?> findById(@PathVariable String id){
		Employee e = employeeService.findById(id);
		if (e!= null) {
			return new ResponseEntity<Employee>(e,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("USER NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insertOne(@RequestBody Employee employee){
		if (employeeService.insertOne(employee)!= null) {
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("INSERT ERROR", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateOne(@RequestBody Employee employee){
		if (employeeService.updateOne(employee)!= null) {
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("USER NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable String id){
		switch (employeeService.deleteOne(id)) {
		case 1:
			return new ResponseEntity<String>("DELETED", HttpStatus.OK);
		case 0:
			return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
		default:
			return new ResponseEntity<String>("CAN'T DELETE", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
	
	
}
