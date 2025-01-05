package com.example.restaurant_management.services;

import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.repositories.DishRepository;
import com.example.restaurant_management.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public DishService(DishRepository dishRepository, OrderRepository orderRepository) {
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
    }

    public void addDish(Dish dish) {
        dishRepository.save(dish);
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
        return dishRepository.save(existingDish);
    }

    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }

    public void checkIsPopular(List<Dish> dishes) {
        dishes.forEach(dish -> {
            long orderCount = orderRepository.countOrdersByDishId(dish.getId());

            markIsPopular(dish, orderCount);
            dishRepository.save(dish);
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
