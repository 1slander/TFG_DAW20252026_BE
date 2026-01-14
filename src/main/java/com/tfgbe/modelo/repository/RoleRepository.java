package com.tfgbe.modelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Integer>{

 boolean existsByRoleName(String roleName);

}
