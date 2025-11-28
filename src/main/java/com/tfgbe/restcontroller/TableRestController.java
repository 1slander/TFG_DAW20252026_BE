package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.tfgbe.modelo.entities.Status;
import com.tfgbe.modelo.entities.Table;
import com.tfgbe.modelo.services.TableService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tables")

public class TableRestController {
	
	@Autowired
	private TableService tableSer;
	
	@GetMapping("/")
	public ResponseEntity<List<Table>> findAll(){
		return ResponseEntity.status(200).body(tableSer.findAll());
		
	}
	
	@GetMapping("/status/{statusName}")
    public ResponseEntity<List<Table>> findByStatus(@PathVariable String statusName){
        try {
            Status status = Status.valueOf(statusName.toUpperCase());
            
            List<Table> tables = tableSer.findByStatus(status);
            
            if (tables.isEmpty()) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.status(200).body(tables);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null); // 400 Bad Request
        }
    }
	
	@GetMapping("/{idTable}")
    public ResponseEntity<Table> findById(@PathVariable int idTable){
        
        Table table = tableSer.findById(idTable);
        
        if (table != null) {
            return ResponseEntity.status(200).body(table); // 200 OK
        } else {
            return ResponseEntity.status(404).body(null); // 404 Not Found
        }
    }
	
	@PostMapping("/")
	public ResponseEntity<Table> insertOne(@RequestBody Table table){
		Table newTable = tableSer.insertOne(table);
		
		if(tableSer.updateOne(table) != null) {
			return ResponseEntity.status(201).body(newTable);
		}
		return ResponseEntity.status(400).body(null);
	}
	
	@PutMapping("/{idTable}")
    public ResponseEntity<?> updateOne(@PathVariable Integer idTable,
                                         @RequestBody Table table){
        table.setIdTable(idTable); 
        
        if ( tableSer.updateOne(table) != null) {
            return ResponseEntity.status(200).body(table); 
        } else {
            return ResponseEntity.status(404).body("Mesa no encontrada para actualizar."); 
        }
    }
	
	@DeleteMapping("/{idTable}")
    public ResponseEntity<String> deleteTable(@PathVariable Integer idTable){
        
        switch(tableSer.deleteOne(idTable)) {
        case 1:
            return ResponseEntity.status(200).body("Mesa eliminada con éxito.");
        case 0:
            return ResponseEntity.status(404).body("Mesa no existe.");
        case -1:
            return ResponseEntity.status(400).body("Mesa no se puede eliminar. Hay pedidos o asignaciones asociadas.");
        default:
            return ResponseEntity.status(500).body("Error interno al intentar eliminar.");
        }
    }
	
	@PutMapping("/status/{idTable}/{newStatus}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer idTable,
                                          @PathVariable String newStatus){
        try {
       
            Status statusEnum = Status.valueOf(newStatus.toUpperCase());
            
            if (tableSer.updateTableStatus(idTable, statusEnum)) {
                return ResponseEntity.status(200).body("Estado de la mesa " + idTable + " actualizado a " + newStatus);
            } else {
                return ResponseEntity.status(404).body("Mesa no encontrada.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Estado '" + newStatus + "' es inválido."); 
        }
    }
}
