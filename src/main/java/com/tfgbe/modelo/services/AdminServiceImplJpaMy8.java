package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.modelo.entities.Admin;
import com.tfgbe.modelo.repository.AdminRepository;

@Service
public class AdminServiceImplJpaMy8 implements AdminService{

	@Autowired
	AdminRepository adminRepository;

	
	@Override
	public Admin findById(Integer key) {
		//return adminRepository.findById(key).orElse(null);
		return null;
	}

	@Override
	public List<Admin> findAll() {
		return adminRepository.findAll();
	}

	@Override
	public Admin insertOne(Admin entity) {
		try {
			return adminRepository.save(entity);
		}catch (Exception e) {
			System.out.println("ERROR : " + e.getMessage());
			return null;
		}
	}

	@Override
	public Admin updateOne(Admin entity) {
		if (adminRepository.existsById(entity.getUser().getIdUser())) {
			return adminRepository.save(entity);
		}else
			return null;
	}

	@Override
	public int deleteOne(Integer key) {
		/*
		if(adminRepository.existsById(key)) {
			try {
				adminRepository.deleteById(key);
				return 1;
			} catch (Exception e) {
				System.out.println("ERROR : " + e.getMessage());
				return -1;
			}
		}
		return 0;
		*/
		return 0;
	}
}
