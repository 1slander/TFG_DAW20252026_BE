package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfgbe.modelo.entities.TableAssignment;

import com.tfgbe.modelo.repository.TableAssignmentRepository;

public class TableAssignmentServiceImpl implements TableAssignmentService{
	
	@Autowired
    private TableAssignmentRepository tableAssignmentRep; 

    @Override
    public TableAssignment findById(Integer id) {
        return tableAssignmentRep.findById(id).orElse(null);
    }

    @Override
    public List<TableAssignment> findAll() {
        return tableAssignmentRep.findAll();
    }

    @Override
    public TableAssignment insertOne(TableAssignment entity) {
        
        return tableAssignmentRep.save(entity);
    }

    @Override
    public TableAssignment updateOne(TableAssignment entity) {
       
        if (tableAssignmentRep.existsById(entity.getIdAssigment())) {
            return tableAssignmentRep.save(entity);
        }
        return null; 
    }

    @Override
    public int deleteOne(Integer id) {
        if (tableAssignmentRep.existsById(id)) {
            try {
                tableAssignmentRep.deleteById(id);
                return 1; 
            } catch (Exception e) {
                
                return -1; 
            }
        }
        return 0; 
    }
}