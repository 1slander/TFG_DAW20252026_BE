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

import com.tfgbe.modelo.dto.CreateEmployeeDto;
import com.tfgbe.modelo.dto.EmployeeResponseDto;
import com.tfgbe.modelo.dto.UpdateEmployeeDto;
import com.tfgbe.modelo.repository.EmployeeRepository;
import com.tfgbe.modelo.services.EmployeeService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    private final EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService employeeService;

    EmployeeRestController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
	
	@GetMapping
	public ResponseEntity<?> getAllEmployees() {
		return new ResponseEntity<List<EmployeeResponseDto>>(employeeService.findAll(),HttpStatus.OK);
	}

	@GetMapping("/restaurant")
	public ResponseEntity<List<EmployeeResponseDto>> getRestaurantEmployees() {
    return ResponseEntity.ok(
        employeeService.findMyRestaurantEmployees()
    );
}


	@GetMapping("/{idEmployee}")
	public ResponseEntity<?> getEmployeeById(@PathVariable int idEmployee) {
		return new ResponseEntity<EmployeeResponseDto>(employeeService.findByIdDto(idEmployee),HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> postCreateEmployee(@RequestBody @Valid CreateEmployeeDto entity) {
		
		
		return new ResponseEntity<>(employeeService.insertOne(entity),HttpStatus.CREATED);
	}


	@PutMapping("/update/{idEmployee}")
	public ResponseEntity<?> updateOneEmployee(@PathVariable int idEmployee,@RequestBody @Valid UpdateEmployeeDto updateEmployee){
		return new ResponseEntity<>(employeeService.updateOne(idEmployee, updateEmployee),HttpStatus.OK);
	}

	@DeleteMapping("/delete/{idEmployee}")
	public ResponseEntity<?> deleteOneAdmin(@PathVariable int idEmployee){

		switch (employeeService.deleteOneEmployee(idEmployee)) {
			case 1:
				return new ResponseEntity<>("Eliminado correctamente",HttpStatus.OK);
				
			case 0:
				return new ResponseEntity<>("Empleado no existe",HttpStatus.NOT_FOUND);
				
		
			default:
				return new ResponseEntity<>("No se puede eliminar Empleado",HttpStatus.BAD_REQUEST);
		}
		
	}
	

	@PutMapping("/{idEmployee}/shift/{idShift}")
	public ResponseEntity<EmployeeResponseDto> assignShiftToEmployee(@PathVariable int idEmployee, @PathVariable int idShift){
		return new ResponseEntity<EmployeeResponseDto>(employeeService.assignShiftToEmployee(idEmployee, idShift),HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
}
