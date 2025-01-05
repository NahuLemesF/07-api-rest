package com.example.restaurant.services.dish;

import com.example.restaurant.constants.DishType;
import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.DishRepository;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
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
            markIsPopular(dish, orderCount);
        });
        return null;
    }

    private void markIsPopular(Dish dish, long orderCount) {
        if (orderCount >= 100) {
            dish.setDishType(DishType.POPULAR);
            dishRepository.save(dish);
            dishSubject.notifyObservers(EventType.UPDATE, dish);
        }
    }
}
