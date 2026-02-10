package com.tfgbe.modelo.services;

import java.util.List;

import com.tfgbe.modelo.dto.LoginResponseDto;
import com.tfgbe.modelo.dto.AdminResponseDto;
import com.tfgbe.modelo.dto.CreateAdminDto;
import com.tfgbe.modelo.entities.Admin;

public interface AdminService {

    List<AdminResponseDto> findAll();    
    AdminResponseDto findById(Integer idAdmin);
    AdminResponseDto insertOne(CreateAdminDto admin);
    int deleteOne(int idAdmin);
    LoginResponseDto authenticateAdmin(CreateAdminDto admin);

}
