package com.example.restaurant_management.services;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.observers.DishSubject;
import com.example.restaurant_management.repositories.DishRepository;
import com.example.restaurant_management.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final DishSubject dishSubject;

    @Autowired
    public DishService(DishRepository dishRepository, OrderRepository orderRepository, DishSubject dishSubject) {
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.dishSubject = dishSubject;
    }

    public void addDish(Dish dish) {
        dishRepository.save(dish);
        dishSubject.notifyObservers(EventType.CREATE, dish);
    }

    public Dish getDishById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato con el id " + id + " no encontrado"));
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Dish updateDish(Long id, Dish dish) {
        Dish existingDish = getDishById(id);
        existingDish.setName(dish.getName());
        existingDish.setPrice(dish.getPrice());
        Dish updatedDish = dishRepository.save(existingDish);
        dishSubject.notifyObservers(EventType.UPDATE, updatedDish);
        return updatedDish;
    }

    public void deleteDish(Long id) {
        Dish dishToDelete = getDishById(id);
        dishRepository.deleteById(id);
        dishSubject.notifyObservers(EventType.DELETE, dishToDelete);
    }

    public void checkIsPopular(List<Dish> dishes) {
        dishes.forEach(dish -> {
            long orderCount = orderRepository.countOrdersByDishId(dish.getId());

            markIsPopular(dish, orderCount);
            dishRepository.save(dish);
            if (dish.getIsPopular()) {
                dishSubject.notifyObservers(EventType.UPDATE, dish);
            }
        });
    }

    private void markIsPopular(Dish dish, long orderCount) {
        if (orderCount > 100) {
            dish.setIsPopular(true);
            float newPrice = dish.getPrice() * 1.0573f;
            dish.setPrice(newPrice);
        }
    }

}
