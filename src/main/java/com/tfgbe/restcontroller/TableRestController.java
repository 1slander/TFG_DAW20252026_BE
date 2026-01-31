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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.dto.CreateTableDto;
import com.tfgbe.modelo.dto.TableResponseDto;
import com.tfgbe.modelo.dto.UpdateTableDto;
import com.tfgbe.modelo.entities.TableStatus;
import com.tfgbe.modelo.services.TableService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tables")
public class TableRestController {

    @Autowired
    private TableService tableService;

    /* =========================
       CREATE
       ========================= */

    @PostMapping("/{idRestaurant}")
    public ResponseEntity<TableResponseDto> createTable(
            @PathVariable Long idRestaurant,
            @Valid @RequestBody CreateTableDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(tableService.createTable(idRestaurant, dto));
    }

    /* =========================
       READ
       ========================= */

    @GetMapping
    public ResponseEntity<List<TableResponseDto>> findAll() {

        return ResponseEntity.ok(tableService.findAll());
    }

    @GetMapping("/{idTable}")
    public ResponseEntity<TableResponseDto> findById(
            @PathVariable int idTable) {

        return ResponseEntity.ok(tableService.findById(idTable));
    }

    @GetMapping("/restaurant/{idRestaurant}")
    public ResponseEntity<List<TableResponseDto>> findByRestaurant(
            @PathVariable Long idRestaurant) {

        return ResponseEntity.ok(
            tableService.findByRestaurant(idRestaurant));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TableResponseDto>> findByStatus(
            @PathVariable TableStatus status) {

        return ResponseEntity.ok(
            tableService.findByStatus(status));
    }

    /* =========================
       UPDATE
       ========================= */

    @PutMapping("/update/{idTable}")
    public ResponseEntity<TableResponseDto> updateTable(
            @PathVariable int idTable,
            @RequestBody UpdateTableDto dto) {

        return ResponseEntity.ok(
            tableService.updateTable(idTable, dto));
    }

    @PutMapping("/update/status/{idTable}")
    public ResponseEntity<Void> updateTableStatus(
            @PathVariable Integer idTable,
            @RequestParam TableStatus status) {

        tableService.updateTableStatus(idTable, status);
        return ResponseEntity.ok().build();
    }

    /* =========================
       DELETE
       ========================= */

    @DeleteMapping("/delete/{idTable}")
    public ResponseEntity<?> deleteTable(
            @PathVariable int idTable) {

    switch (tableService.deleteTable(idTable)) {

        case 1:
            return ResponseEntity
                .status(200)
                .body("Mesa eliminada con éxito.");

        case 0:
            return ResponseEntity
                .status(404)
                .body("Mesa no existe.");

        case -1:
            return ResponseEntity
                .status(400)
                .body("La mesa no se puede eliminar. Puede tener asignaciones activas.");

        default:
            return ResponseEntity
                .status(500)
                .body("Error interno al intentar eliminar la mesa.");
    }
}
}