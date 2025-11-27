package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.modelo.entities.Shift;
import com.tfgbe.modelo.repository.ShiftRepository;

@Service
public class ShitfServiceImplJpaMy8 implements ShiftService{

	@Autowired
	ShiftRepository shiftRepository;

	@Override
	public Shift findById(Integer key) {
		return shiftRepository.findById(key).orElse(null);
	}

	@Override
	public List<Shift> findAll() {
		return shiftRepository.findAll();
	}

	@Override
	public Shift insertOne(Shift entity) {
		try {
			return shiftRepository.save(entity);
		}catch(Exception e) {
			System.out.println("ERROR : " + e.getMessage());
			return null;
		}
	}

	@Override
	public Shift updateOne(Shift entity) {
		if (shiftRepository.existsById(entity.getIdShift())) {
			return shiftRepository.save(entity);
		}else
			return null;
	}

	@Override
	public int deleteOne(Integer key) {
		if(shiftRepository.existsById(key)) {
			try {
				shiftRepository.deleteById(key);
				return 1;
			}catch (Exception e) {
				System.out.println("ERROR : " + e.getMessage());
				return -1;
			}
		}
		return 0;
	}
	
}
