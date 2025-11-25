package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfgbe.modelo.entities.Table;
import com.tfgbe.modelo.repository.TableRepository;

public class TableServiceImplJpaMy8 implements TableService {
	
	@Autowired
	TableRepository tableRep;

	@Override
	public Table findById(Integer atributoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Table> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table insertOne(Table entidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table updateOne(Table entidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteOne(Integer atributoId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
