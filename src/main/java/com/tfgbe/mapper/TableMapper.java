package com.tfgbe.mapper;

import com.tfgbe.modelo.dto.TableResponseDto;
import com.tfgbe.modelo.entities.TableEntity;

public class TableMapper {



    public static TableResponseDto convertirTableDto(TableEntity table) {

        return TableResponseDto.builder()
            .idTable(table.getIdTable())
            .tableNumber(table.getTableNumber())
            .tableCapacity(table.getTableCapacity())
            .status(table.getStatus())
            .idRestaurant(
                table.getRestaurant() != null
                    ? table.getRestaurant().getIdRestaurant()
                    : null
            )
            .restaurantName(
                table.getRestaurant() != null
                    ? table.getRestaurant().getRestaurantName()
                    : null
            )
            .build();
    }

}
