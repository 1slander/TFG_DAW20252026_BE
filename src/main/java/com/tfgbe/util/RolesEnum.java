package com.tfgbe.util;

public enum RolesEnum {
    EMPLOYEE(0),
    TEAM_LEADER(1),
    ASSISTANT_MANAGER(2),
    MANAGER(3),
    OWNER(4);

    private final int nivel;

    RolesEnum(int nivel){
        this.nivel=nivel;
    }

    public int getNivel(){
        return nivel;
    }
}
