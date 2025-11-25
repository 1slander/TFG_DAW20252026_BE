package com.tfgbe.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>  {

}
