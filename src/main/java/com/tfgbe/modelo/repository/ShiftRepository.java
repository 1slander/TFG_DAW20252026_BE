package com.tfgbe.modelo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Restaurant;
import com.tfgbe.modelo.entities.Shift;
import com.tfgbe.modelo.entities.ShiftType;

public interface ShiftRepository  extends JpaRepository<Shift, Integer>{


   boolean existsByAssignShiftAndRestaurant(
    ShiftType assignShift,
    Restaurant restaurant
);

    List<Shift> findByRestaurant(Restaurant restaurant);

    Optional<Shift> findByIdShiftAndRestaurant(int idShift,Restaurant restaurant);
}
