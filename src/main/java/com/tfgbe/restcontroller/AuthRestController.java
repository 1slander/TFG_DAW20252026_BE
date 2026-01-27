package com.tfgbe.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.dto.CreateAdminDto;
import com.tfgbe.modelo.dto.CreateEmployeeDto;
import com.tfgbe.modelo.services.AdminService;
import com.tfgbe.modelo.services.EmployeeService;

@RestController
@RequestMapping("/")
public class AuthRestController {

@Autowired
	AdminService adminService;

@Autowired
EmployeeService employeeService;


@PostMapping("/admin/login")
	public ResponseEntity<?> loginAdmin(@RequestBody CreateAdminDto admin){
		
		return new ResponseEntity<>(adminService.authenticateAdmin(admin),HttpStatus.OK);
	}



@PostMapping("/login")
	public ResponseEntity<?> loginEmployee(@RequestBody CreateEmployeeDto employee){
		
		return new ResponseEntity<>(employeeService.authenticateEmployee(employee),HttpStatus.OK);
	}

}
