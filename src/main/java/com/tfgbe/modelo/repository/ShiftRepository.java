package com.tfgbe.modelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Shift;

public interface ShiftRepository  extends JpaRepository<Shift, Integer>{


    boolean existsByAssignShift(String assignShift);

    Optional<Shift> findByAssignShift(String assignShift);
}
