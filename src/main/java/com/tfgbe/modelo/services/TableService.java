package com.tfgbe.modelo.services;

import java.util.List;
import com.tfgbe.modelo.entities.TableStatus;
import com.tfgbe.modelo.dto.CreateTableDto;
import com.tfgbe.modelo.dto.TableResponseDto;
import com.tfgbe.modelo.dto.UpdateTableDto;
import com.tfgbe.modelo.entities.TableEntity;

public interface TableService {
	
	
	List <TableResponseDto> findByStatus(TableStatus status);
	
	boolean updateTableStatus(Integer tableId, TableStatus newStatus);

	 TableResponseDto createTable(Long idRestaurant, CreateTableDto dto);

    TableResponseDto findById(int idTable);

    List<TableResponseDto> findAll();

    List<TableResponseDto> findByRestaurant(Long idRestaurant);

    TableResponseDto updateTable(int idTable, UpdateTableDto dto);

    int deleteTable(int idTable);

    void updatePosition(Integer tableId, Integer posX, Integer posY);
}
