package com.tfgbe.modelo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.mapper.SignupRequestMapper;
import com.tfgbe.modelo.dto.CreateSignupRequestDto;
import com.tfgbe.modelo.dto.SignupRequestResponseDto;
import com.tfgbe.modelo.entities.Admin;
import com.tfgbe.modelo.entities.SignupRequest;
import com.tfgbe.modelo.entities.SignupRequestStatus;
import com.tfgbe.modelo.repository.AdminRepository;
import com.tfgbe.modelo.repository.SignupRequestRepository;

@Service
public class SignupRequestImplMy8 implements SignupResquestService {
 @Autowired
    private SignupRequestRepository signupRequestRepository;

    @Autowired
    private AdminRepository adminRepository;

    
    @Override
    public SignupRequestResponseDto createRequest(CreateSignupRequestDto dto) {

        SignupRequest request = SignupRequest.builder()
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .email(dto.getEmail())
            .phone(dto.getPhone())
            .restaurantName(dto.getRestaurantName())
            .message(dto.getMessage())
            .status(SignupRequestStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .build();

        signupRequestRepository.save(request);

        return SignupRequestMapper.toDto(request);
    }

  
    @Override
    public List<SignupRequestResponseDto> findAll() {
        return signupRequestRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(SignupRequestMapper::toDto)
            .toList();
    }

   
    @Override
    public List<SignupRequestResponseDto> findPending() {
        return signupRequestRepository.findByStatus(SignupRequestStatus.PENDING)
            .stream()
            .map(SignupRequestMapper::toDto)
            .toList();
    }

   
    @Override
    public SignupRequestResponseDto approveRequest(int idRequest) {

        SignupRequest request = signupRequestRepository.findById(idRequest)
            .orElseThrow(() ->
                new NotFoundException("No existe la solicitud con id: " + idRequest)
            );

        if (request.getStatus() != SignupRequestStatus.PENDING) {
            throw new IllegalStateException("La solicitud ya ha sido resuelta");
        }

        Admin admin = getAuthenticatedAdmin();

        request.setStatus(SignupRequestStatus.APPROVED);
        request.setResolvedAt(LocalDateTime.now());
        request.setResolvedBy(admin);

        signupRequestRepository.save(request);

   

        return SignupRequestMapper.toDto(request);
    }

   
    @Override
    public SignupRequestResponseDto rejectRequest(int idRequest) {

        SignupRequest request = signupRequestRepository.findById(idRequest)
            .orElseThrow(() ->
                new NotFoundException("No existe la solicitud con id: " + idRequest)
            );

        if (request.getStatus() != SignupRequestStatus.PENDING) {
            throw new IllegalStateException("La solicitud ya ha sido resuelta");
        }

        Admin admin = getAuthenticatedAdmin();

        request.setStatus(SignupRequestStatus.REJECTED);
        request.setResolvedAt(LocalDateTime.now());
        request.setResolvedBy(admin);

        signupRequestRepository.save(request);

        return SignupRequestMapper.toDto(request);
    }

   
    private Admin getAuthenticatedAdmin() {
        String username = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

        return adminRepository.findByUsername(username)
            .orElseThrow(() ->
                new NotFoundException("Admin autenticado no encontrado")
            );
    }
}
