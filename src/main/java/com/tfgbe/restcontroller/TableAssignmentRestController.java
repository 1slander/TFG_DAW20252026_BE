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

import com.tfgbe.modelo.dto.CreateTableAssignmentDto;
import com.tfgbe.modelo.dto.TableAssignmentResponseDto;
import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.entities.TableAssignment;
import com.tfgbe.modelo.services.TableAssignmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/table-assignment")
public class TableAssignmentRestController {
	
	
    @Autowired
    private TableAssignmentService tableAssignmentService;

  

    @PostMapping
    public ResponseEntity<TableAssignmentResponseDto> createAssignment(
            @Valid @RequestBody CreateTableAssignmentDto dto) {

        return new ResponseEntity<>(
            tableAssignmentService.createAssignment(dto),
            HttpStatus.CREATED
        );
    }

    

    @PutMapping("/close/{idAssignment}")
    public ResponseEntity<TableAssignmentResponseDto> closeAssignment(
            @PathVariable Integer idAssignment) {

        return ResponseEntity.ok(
            tableAssignmentService.closeAssignment(idAssignment)
        );
    }

  

    @GetMapping("/table/{idTable}")
    public ResponseEntity<List<TableAssignmentResponseDto>> 
            findAssignmentsByTable(
                @PathVariable Integer idTable) {

        return ResponseEntity.ok(
            tableAssignmentService.findAssignmentsByTable(idTable)
        );
    }
	
	
	
	
}
