package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.modelo.entities.Employee;
import com.tfgbe.modelo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImplJpaMy8 implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Employee findById(Integer key) {
		return employeeRepository.findById(key).orElse(null);
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee insertOne(Employee entity) {
		try {
			return employeeRepository.save(entity);
		}catch(Exception e) {
			System.out.println("ERROR : " + e.getMessage());
			return null;
		}
	}

	@Override
	public Employee updateOne(Employee entity) {
		if (employeeRepository.existsById(entity.getIdUser())) {
			return employeeRepository.save(entity);
		}else
			return null;
	}

	@Override
	public int deleteOne(Integer key) {
		if(employeeRepository.existsById(key)) {
			try {
				employeeRepository.deleteById(key);
				return 1;
			}catch (Exception e){
				System.out.println("ERROR : " + e.getMessage());
				return -1;
			}
		}
		return 0;
	}



}
