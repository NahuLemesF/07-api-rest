package com.example.restaurant_management.handlers;

import com.example.restaurant_management.handlers.interfaces.IOrderHandler;
import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PopularDishHandler implements IOrderHandler {

    @Autowired
    private DishService dishService;

    @Override
    public void handle(Order order) {
        dishService.checkIsPopular(order.getDishes());
    }
}
