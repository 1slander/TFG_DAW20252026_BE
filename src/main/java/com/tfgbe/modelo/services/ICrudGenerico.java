package com.tfgbe.modelo.services;

import java.util.List;

public interface ICrudGenerico<E,ID> {
	
	E findById(ID key);
    List<E> findAll();
    E insertOne(E entity);
    E updateOne(E entity);
    int deleteOne(ID key);

}
