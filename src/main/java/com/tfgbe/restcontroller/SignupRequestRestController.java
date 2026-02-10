package com.tfgbe.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgbe.modelo.dto.CreateSignupRequestDto;
import com.tfgbe.modelo.dto.SignupRequestResponseDto;
import com.tfgbe.modelo.services.SignupResquestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class SignupRequestRestController {

     @Autowired
    private SignupResquestService signupRequestService;

    // ======================
    // PUBLIC
    // ======================

  
    @PostMapping("/signup")
    public ResponseEntity<SignupRequestResponseDto> createSignupRequest(
            @Valid @RequestBody CreateSignupRequestDto dto) {

        return new ResponseEntity<>(
            signupRequestService.createRequest(dto),
            HttpStatus.CREATED
        );
    }

    // ======================
    // ADMIN
    // ======================

  
    @GetMapping("/admin/signup-requests")
    public ResponseEntity<List<SignupRequestResponseDto>> findAll() {

        return ResponseEntity.ok(
            signupRequestService.findAll()
        );
    }

   
    @GetMapping("/admin/signup-requests/pending")
    public ResponseEntity<List<SignupRequestResponseDto>> findPending() {

        return ResponseEntity.ok(
            signupRequestService.findPending()
        );
    }

    
    @PostMapping("/admin/signup-requests/{idRequest}/approve")
    public ResponseEntity<SignupRequestResponseDto> approveRequest(
            @PathVariable int idRequest) {

        return ResponseEntity.ok(
            signupRequestService.approveRequest(idRequest)
        );
    }

   
    @PostMapping("/admin/signup-requests/{idRequest}/reject")
    public ResponseEntity<SignupRequestResponseDto> rejectRequest(
            @PathVariable int idRequest) {

        return ResponseEntity.ok(
            signupRequestService.rejectRequest(idRequest)
        );
    }

}
