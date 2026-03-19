package com.tfgbe.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tfgbe.modelo.dto.FloorResponseDto;
import com.tfgbe.modelo.services.FloorService;

@RestController
@RequestMapping("/floors")
public class FloorRestController {

    @Autowired
    private FloorService floorService;

    @GetMapping("/restaurant/{idRestaurant}")
    public ResponseEntity<List<FloorResponseDto>> findByRestaurant(@PathVariable Long idRestaurant) {
        return ResponseEntity.ok(floorService.findByRestaurant(idRestaurant));
    }

    @PostMapping("/{idRestaurant}")
    public ResponseEntity<FloorResponseDto> createFloor(
            @PathVariable Long idRestaurant,
            @RequestParam String name) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(floorService.createFloor(idRestaurant, name));
    }

    @PutMapping("/{idFloor}")
    public ResponseEntity<FloorResponseDto> updateFloor(
            @PathVariable Integer idFloor,
            @RequestParam String name) {
        return ResponseEntity.ok(floorService.updateFloor(idFloor, name));
    }

    @DeleteMapping("/{idFloor}")
    public ResponseEntity<Void> deleteFloor(@PathVariable Integer idFloor) {
        floorService.deleteFloor(idFloor);
        return ResponseEntity.noContent().build();
    }
}
