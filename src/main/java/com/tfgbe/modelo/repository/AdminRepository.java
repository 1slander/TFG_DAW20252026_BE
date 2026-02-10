package com.tfgbe.modelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

    boolean existsByEmail(String email);
   Optional<Admin> findByUsername(String username);

}
