package com.tfgbe.modelo.services;

import java.util.List;
import com.tfgbe.modelo.entities.Status;
import com.tfgbe.modelo.entities.Table;

public interface TableService extends ICrudGenerico<Table, Integer> {
	
	
	List <Table> findByStatus(Status status);
	
	boolean updateTableStatus(Integer tableId, Status newStatus);
}
