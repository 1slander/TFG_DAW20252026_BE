package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.modelo.dto.AdminResponseDto;
import com.tfgbe.modelo.entities.Admin;
import com.tfgbe.modelo.repository.AdminRepository;

@Service
public class AdminServiceImplJpaMy8 implements AdminService{

	@Autowired
	AdminRepository adminRepository;

	
	@Override
	public AdminResponseDto findById(Integer idAdmin) {
		return adminRepository.findById(idAdmin).map(admin -> AdminResponseDto.convertirAdminDto(admin)).orElseThrow(()->new NotFoundException("No existe el admin con ID: " + idAdmin));
		
	}

	@Override
	public List<AdminResponseDto> findAll() {
		return adminRepository.findAll().stream().map(admin -> AdminResponseDto.convertirAdminDto(admin)).toList();
	}

	
	@Override
	public AdminResponseDto insertOne(AdminResponseDto entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'insertOne'");
	}

	@Override
	public AdminResponseDto updateOne(AdminResponseDto entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateOne'");
	}

	@Override
	public int deleteOne(Integer key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteOne'");
	}

}
