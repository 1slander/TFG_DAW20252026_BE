package com.tfgbe.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfgbe.modelo.entities.SignupRequest;
import com.tfgbe.modelo.entities.SignupRequestStatus;

public interface SignupRequestRepository extends JpaRepository<SignupRequest,Integer>{

    List<SignupRequest> findByStatus(SignupRequestStatus status);
    List<SignupRequest> findAllByOrderByCreatedAtDesc();
}
