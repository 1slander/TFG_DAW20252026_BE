package com.tfgbe.modelo.services;

import java.util.List;
import com.tfgbe.restcontroller.UserRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.modelo.entities.User;
import com.tfgbe.modelo.repository.UserRepository;

@Service
public class UserServiceImplJpaMy8 implements UserService{

	
	@Autowired
	UserRepository userRepository;

	
	@Override
	public User findById(Integer key) {
		return userRepository.findById(key).orElse(null);
	}


	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}


	@Override
	public User insertOne(User entity) {
		try {
			entity.setIdUser(0);
			return userRepository.save(entity);
		}catch(Exception e) {
			System.out.println("ERROR : " + e.getMessage());
			return null;
		}
		
	}


	@Override
	public User updateOne(User entity) {
		if (userRepository.existsById(entity.getIdUser())) {
			return userRepository.save(entity);
		}else
			return null;
	}


	@Override
	public int deleteOne(Integer key) {
		if (userRepository.existsById(key)) {
			try {
				userRepository.deleteById(key);
				return 1;
			}catch (Exception e) {
				System.out.println("ERROR : " + e.getMessage());
				return -1;
			}
		}
		return 0;
	}



}
