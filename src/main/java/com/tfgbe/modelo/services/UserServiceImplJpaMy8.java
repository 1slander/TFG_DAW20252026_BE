package com.tfgbe.modelo.services;

import java.util.List;
import com.tfgbe.restcontroller.UserRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.modelo.entities.User;
import com.tfgbe.modelo.repository.UserRepository;

@Service
public class UserServiceImplJpaMy8 implements UserService{

    private final UserRestController userRestController;
	
	@Autowired
	UserRepository userRepository;


    UserServiceImplJpaMy8(UserRestController userRestController) {
        this.userRestController = userRestController;
    }
	
	
	@Override
	public User findById(Integer atributoId) {
		return userRepository.findById(atributoId).orElse(null);
	}


	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}


	@Override
	public User insertOne(User entidad) {
		try {
			entidad.setIdUser(0);
			return userRepository.save(entidad);
		}catch(Exception e) {
			System.out.println("ERROR : " + e.getMessage());
			return null;
		}
		
	}


	@Override
	public User updateOne(User entidad) {
		if (userRepository.existsById(entidad.getIdUser())) {
			return userRepository.save(entidad);
		}else
			return null;
	}


	@Override
	public int deleteOne(Integer atributoId) {
		if (userRepository.existsById(atributoId)) {
			try {
				userRepository.deleteById(atributoId);
				return 1;
			}catch (Exception e) {
				System.out.println("ERROR : " + e.getMessage());
				return -1;
			}
		}
		return 0;
	}



}
