package com.tfgbe.modelo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfgbe.exceptions.AlreadyExistsException;
import com.tfgbe.exceptions.NotFoundException;
import com.tfgbe.modelo.entities.Role;
import com.tfgbe.modelo.repository.RoleRepository;

@Service
public class RoleServiceImplJpaMy8 implements RoleService{

    @Autowired
    RoleRepository roleRepo;

    @Override
    public Role findById(Integer idInteger) {
        return roleRepo.findById(idInteger).orElseThrow(()-> new NotFoundException("Role con id: " + idInteger + "no encontrado."));
    }

    @Override
    public List<Role> findAll() {

       return roleRepo.findAll();
    }

    @Override
    public Role insertOne(Role entity) {
        
        entity.setRoleName(entity.getRoleName().toUpperCase());
        if(roleRepo.existsByRoleName(entity.getRoleName())){
           throw new AlreadyExistsException("El rol ya existe");
        }
        
       
        return roleRepo.save(entity);

    }

    @Override
    public Role updateOne(Role entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOne'");
    }

    @Override
    public int deleteOne(Integer idInteger) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOne'");
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return roleRepo.existsByRoleName(roleName);
    }

}
