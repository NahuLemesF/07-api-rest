package com.example.restaurant_management.services.dish;

import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.repositories.DishRepository;
import com.example.restaurant_management.services.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllDishesService implements ICommand<List<Dish>> {

    private final DishRepository dishRepository;

    @Autowired
    public GetAllDishesService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> execute() {
        return dishRepository.findAll();
    }
}
