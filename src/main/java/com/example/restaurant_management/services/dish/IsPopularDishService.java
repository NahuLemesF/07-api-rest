package com.example.restaurant_management.services.dish;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.observers.DishSubject;
import com.example.restaurant_management.repositories.DishRepository;
import com.example.restaurant_management.repositories.OrderRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IsPopularDishService implements ICommandParametrized<Void, List<Dish>> {

    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final DishSubject dishSubject;

    @Autowired
    public IsPopularDishService(DishRepository dishRepository, OrderRepository orderRepository, DishSubject dishSubject) {
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.dishSubject = dishSubject;
    }

    @Override
    public Void execute(List<Dish> dishes) {
        dishes.forEach(dish -> {
            long orderCount = orderRepository.countOrdersByDishId(dish.getId());
            if (orderCount > 100) {
                dish.setIsPopular(true);
                dish.setPrice(dish.getPrice() * 1.0573f);
                dishRepository.save(dish);
                dishSubject.notifyObservers(EventType.UPDATE, dish);
            }
        });
        return null;
    }
}
