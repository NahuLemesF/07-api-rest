package com.example.restaurant_management.services.dish;

import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.repositories.DishRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetDishByIdService implements ICommandParametrized<Dish, Long> {

    private final DishRepository dishRepository;

    @Autowired
    public GetDishByIdService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish execute(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato con el id " + id + " no encontrado"));
    }
}
