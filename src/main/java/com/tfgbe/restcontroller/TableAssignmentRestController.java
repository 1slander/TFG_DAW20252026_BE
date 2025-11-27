package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.services.TableAssignmentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/assignments")
public class TableAssignmentRestController {
	
	
	@Autowired
	private TableAssignmentService assignmentService; 
	
}
