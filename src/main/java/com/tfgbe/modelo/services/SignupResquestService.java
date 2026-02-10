package com.tfgbe.modelo.services;

import java.util.List;

import com.tfgbe.modelo.dto.CreateSignupRequestDto;
import com.tfgbe.modelo.dto.SignupRequestResponseDto;

public interface SignupResquestService {
    
    SignupRequestResponseDto createRequest(CreateSignupRequestDto dto);

    List<SignupRequestResponseDto> findAll();

    List<SignupRequestResponseDto> findPending();

    SignupRequestResponseDto approveRequest(int idRequest);

    SignupRequestResponseDto rejectRequest(int idRequest);
}

