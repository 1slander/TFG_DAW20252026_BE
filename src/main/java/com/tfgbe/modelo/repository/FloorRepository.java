package com.tfgbe.modelo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tfgbe.modelo.entities.Floor;
import com.tfgbe.modelo.entities.Restaurant;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Integer> {
    List<Floor> findByRestaurant(Restaurant restaurant);
}
