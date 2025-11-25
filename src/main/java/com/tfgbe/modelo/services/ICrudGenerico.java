package com.tfgbe.modelo.services;

import java.util.List;

public interface ICrudGenerico<E,ID> {
	
	E findById(ID atributoId);
    List<E> findAll();
    E insertOne(E entidad);
    E updateOne(E entidad);
    int deleteOne(ID atributoId);

}
