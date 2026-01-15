package com.tfgbe.modelo.services;

import java.util.List;

import com.tfgbe.modelo.dto.AdminResponseDto;

import com.tfgbe.modelo.entities.Admin;

public interface AdminService {

    List<AdminResponseDto> findAll();    
    AdminResponseDto findById(Integer idAdmin);
    AdminResponseDto insertOne(Admin entity);
    int deleteOne(int idAdmin);

}
