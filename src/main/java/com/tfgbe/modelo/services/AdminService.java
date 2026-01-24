package com.tfgbe.modelo.services;

import java.util.List;

import com.tfgbe.modelo.dto.AdminLoginResponseDto;
import com.tfgbe.modelo.dto.AdminResponseDto;
import com.tfgbe.modelo.dto.CreateUserDto;
import com.tfgbe.modelo.entities.Admin;

public interface AdminService {

    List<AdminResponseDto> findAll();    
    AdminResponseDto findById(Integer idAdmin);
    AdminResponseDto insertOne(CreateUserDto admin);
    int deleteOne(int idAdmin);
    AdminLoginResponseDto authenticateAdmin(CreateUserDto admin);

}
