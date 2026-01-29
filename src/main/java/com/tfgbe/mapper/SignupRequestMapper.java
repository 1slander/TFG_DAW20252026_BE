package com.tfgbe.mapper;

import com.tfgbe.modelo.dto.SignupRequestResponseDto;
import com.tfgbe.modelo.entities.SignupRequest;

public class SignupRequestMapper {

     public static SignupRequestResponseDto toDto(SignupRequest request) {
        return SignupRequestResponseDto.builder()
            .idRequest(request.getIdRequest())
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .restaurantName(request.getRestaurantName())
            .message(request.getMessage())
            .status(request.getStatus())
            .createdAt(request.getCreatedAt())
            .resolvedAt(request.getResolvedAt())
            .resolvedByAdmin(
                request.getResolvedBy() != null
                    ? request.getResolvedBy().getUsername()
                    : null
            )
            .build();
    }
}
