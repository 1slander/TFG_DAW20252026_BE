package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.modelo.entities.User;
import com.tfgbe.modelo.repository.UserRepository;

@Service
public class UserServiceImplJpaMy8 implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public User findById(Integer atributoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User insertOne(User entidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateOne(User entidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteOne(Integer atributoId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
