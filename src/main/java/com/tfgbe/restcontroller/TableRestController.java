package com.tfgbe.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.services.TableService;

@RestController
@RequestMapping("/tables")

public class TableRestController {
	
	@Autowired
	TableService tableSer;
	
	

}
