package com.tfgbe.modelo.services;

import com.tfgbe.modelo.entities.Role;


public interface RoleService extends ICrudGenerico<Role,Integer> {

    boolean existsByRoleName(String roleName);

}
