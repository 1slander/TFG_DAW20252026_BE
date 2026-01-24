package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.AlreadyExistsException;
import com.tfgbe.exceptions.DeleteRestrictionException;
import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.exceptions.UnauthorizedException;
import com.tfgbe.modelo.dto.AdminLoginResponseDto;
import com.tfgbe.modelo.dto.AdminResponseDto;
import com.tfgbe.modelo.dto.CreateUserDto;
import com.tfgbe.modelo.entities.Admin;
import com.tfgbe.modelo.repository.AdminRepository;
import com.tfgbe.security.JwtUtil;

@Service
public class AdminServiceImplJpaMy8 implements AdminService{

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	public AdminResponseDto findById(Integer idAdmin) {
		return adminRepository.findById(idAdmin).map(admin -> AdminResponseDto.convertirAdminDto(admin)).orElseThrow(()->new NotFoundException("No existe el admin con ID: " + idAdmin));
		
	}

	@Override
	public List<AdminResponseDto> findAll() {
		return adminRepository.findAll().stream().map(admin -> AdminResponseDto.convertirAdminDto(admin)).toList();
	}

	
	@Override
	public AdminResponseDto insertOne(CreateUserDto admin) {
		if(adminRepository.existsByEmail(admin.getEmail()))
				throw new AlreadyExistsException("Ya existe admin con ese email: " + admin.getEmail());
		// if(admin.getRoleName()==null)
		// 		admin.setRoleName("ROLE_ADMIN");
		try{
			Admin newAdmin = new Admin();
			newAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
			newAdmin.setEmail(admin.getEmail());
			newAdmin.setUsername(admin.getUsername());
			newAdmin.setRoleName("ROLE_ADMIN");
			adminRepository.save(newAdmin);;
			return AdminResponseDto.convertirAdminDto(newAdmin);
				
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

	@Override
	public AdminLoginResponseDto authenticateAdmin(CreateUserDto loginAdmin) {
		Admin exist = adminRepository.findByUsername(loginAdmin.getUsername()).orElseThrow(()-> new UnauthorizedException("Username o password incorrecta"));

		if(!passwordEncoder.matches(loginAdmin.getPassword(),exist.getPassword())){
			throw new UnauthorizedException("Username o password incorrecta");

		}
		System.out.println(exist);
		String token = jwtUtil.generateToken(exist.getUsername(), exist.getRoleName());
		return AdminLoginResponseDto.builder()
		.token(token)
		.username(exist.getUsername())
		.build();

	}
    
	


		
		
    
	

}
