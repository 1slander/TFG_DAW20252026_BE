package com.tfgbe.modelo.dto;

import com.tfgbe.modelo.entities.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AdminResponseDto {

    private int idAdmin;
    private String username;
    private String roleName;

    public static AdminResponseDto convertirAdminDto(Admin admin) {
        return AdminResponseDto.builder()
                .idAdmin(admin.getIdAdmin())
                .username(admin.getUsername())
                .roleName(admin.getRoleName())
                .build();
    }



}
