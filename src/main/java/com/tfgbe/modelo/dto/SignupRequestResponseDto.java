package com.tfgbe.modelo.dto;

import java.time.LocalDateTime;

import com.tfgbe.modelo.entities.SignupRequestStatus;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class SignupRequestResponseDto {

    
    private Integer idRequest;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String restaurantName;
    private String message;

    private SignupRequestStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    private String resolvedByAdmin;
}
