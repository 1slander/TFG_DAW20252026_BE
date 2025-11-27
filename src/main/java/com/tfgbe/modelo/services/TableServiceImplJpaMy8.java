package com.tfgbe.modelo.services;

import java.util.List;
import java.util.Optional;
import com.tfgbe.modelo.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.modelo.entities.Table;
import com.tfgbe.modelo.repository.TableRepository;

@Service
public class TableServiceImplJpaMy8 implements TableService {
	
	@Autowired
	TableRepository tableRep;

	@Override
	public Table findById(Integer atributoId) {
		return tableRep.findById(atributoId).orElse(null);	
	}

	@Override
	public List<Table> findAll() {
		return tableRep.findAll();
	}

	@Override
	public Table insertOne(Table entity) {
		// TODO Auto-generated method stub
		return tableRep.save(entity);
	}

	@Override
	public Table updateOne(Table entity) {
		if (tableRep.existsById(entity.getIdTable()))
			return tableRep.save(entity);
		else
			return null;
	}

	@Override
	public int deleteOne(Integer key) {
		if(tableRep.existsById(key)) {
			try {
				tableRep.deleteById(key);
				return 1;
			}catch(Exception e) {
				return -1;
			}
		}
		else
			return 0;
	}
	@Override
	public List<Table> findByStatus(Status status) {
		return tableRep.findByStatus(status);
	}

	@Override
	public boolean updateTableStatus(Integer tableId, Status newStatus) {
		Optional<Table> tableUpt = tableRep.findById(tableId);
		
		if(tableUpt.isPresent()) {
			Table table = tableUpt.get();
			table.setStatus(newStatus);
			tableRep.save(table);
			return true;
		}
		return false;
	}

}
