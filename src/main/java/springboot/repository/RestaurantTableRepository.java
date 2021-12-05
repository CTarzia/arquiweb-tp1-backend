package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.model.RestaurantTable;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long>{
    List<RestaurantTable> findByRestaurantIdOrderByTableNumberAsc(long restaurantId);
}