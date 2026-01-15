package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.AlreadyExistsException;
import com.tfgbe.exceptions.DeleteRestrictionException;
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
	public AdminResponseDto insertOne(Admin entity) {
		if(adminRepository.existsByEmail(entity.getEmail()))
				throw new AlreadyExistsException("Ya existe admin con ese email: " + entity.getEmail());
		if(entity.getRoleName()==null)
				entity.setRoleName("ROLE_ADMIN");
		try{
		 Admin adminSave =	adminRepository.save(entity);
		 return AdminResponseDto.convertirAdminDto(entity);
			
		
		} catch(Exception e){
			throw new RuntimeException("Error técnico al guardar el administrador",e);
		}


      }

	@Override
	public int deleteOne(int idAdmin) {
		if(!adminRepository.existsById(idAdmin))
			return 0;
		try {
				adminRepository.deleteById(idAdmin);
			return 1;
		} catch (Exception e){
			throw new DeleteRestrictionException("No se puede eliminar el administrador con id: " + idAdmin + " porque tiene datos vinculados que lo impiden.");

				
		}
	}	
    
	


		
		
    
	

}
