package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfgbe.modelo.dto.CreateElementDto;
import com.tfgbe.modelo.dto.ElementResponseDto;
import com.tfgbe.modelo.services.RestaurantElementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/elements")
public class ElementRestController {

    @Autowired
    private RestaurantElementService elementService;

    @PostMapping("/{idRestaurant}")
    public ResponseEntity<ElementResponseDto> createElement(
            @PathVariable Long idRestaurant,
            @Valid @RequestBody CreateElementDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(elementService.createElement(idRestaurant, dto));
    }

    @GetMapping("/restaurant/{idRestaurant}")
    public ResponseEntity<List<ElementResponseDto>> findByRestaurant(
            @PathVariable Long idRestaurant) {

        return ResponseEntity.ok(
            elementService.findByRestaurant(idRestaurant));
    }

    @DeleteMapping("/delete/{idElement}")
    public ResponseEntity<?> deleteElement(
            @PathVariable Long idElement) {

        switch (elementService.deleteElement(idElement)) {
            case 1:
                return ResponseEntity
                    .status(200)
                    .body("Elemento eliminado con éxito.");
            case 0:
                return ResponseEntity
                    .status(404)
                    .body("El elemento no existe.");
            default:
                return ResponseEntity
                    .status(500)
                    .body("Error interno al intentar eliminar el elemento.");
        }
    }

    @PutMapping("/update/position/{idElement}")
    public ResponseEntity<Void> updatePosition(
            @PathVariable Long idElement,
            @RequestParam Integer posX,
            @RequestParam Integer posY,
            @RequestParam(required = false, defaultValue = "0") Integer rotation) {

        elementService.updatePosition(idElement, posX, posY, rotation);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/rotation/{idElement}")
    public ResponseEntity<Void> updateRotation(
            @PathVariable Long idElement,
            @RequestParam Integer rotation) {

        // elementService.updateRotation(idElement, rotation); --> Eliminado en refactor, se hace en updatePosition ahora
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/dimensions/{idElement}")
    public ResponseEntity<ElementResponseDto> updateDimensions(
            @PathVariable Long idElement,
            @RequestBody com.tfgbe.modelo.dto.UpdateElementDimensionsDto dto) {
        
        return ResponseEntity.ok(
            elementService.updateDimensions(idElement, dto.getWidth(), dto.getHeight()));
    }
}
