package com.tfgbe.util;

import com.tfgbe.exceptions.NoRoleException;

public class RoleUtils {

    public static RolesEnum roleNormalizer(String role){
        if(role==null || role.isBlank())
            return RolesEnum.EMPLOYEE;
    
        String roleLimpio = role.trim().toUpperCase().replaceAll("\\s+", "_");
        if(roleLimpio.startsWith("ROLE_"))
            roleLimpio=roleLimpio.substring(5);
        try{
            return RolesEnum.valueOf(roleLimpio);

        } catch (IllegalArgumentException e){
            throw new NoRoleException("No existe ese role: "+ role);
        }
    }
    
}
