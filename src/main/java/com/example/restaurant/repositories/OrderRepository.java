package com.example.restaurant.repositories;

import com.example.restaurant.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COUNT(o) FROM Order o JOIN o.dishes d WHERE d.id = :dishId")
    long countOrdersByDishId(@Param("dishId") Long dishId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.client.id = :clientId")
    long countOrdersByClientId(@Param("clientId") Long clientId);

}
