package com.example.restaurant_management.services;

import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.repositories.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
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

    public void markAsPopular(Long id) {
        Dish dish = getDishById(id);
        dish.setIsPopular(true);
        dishRepository.save(dish);
    }

}
