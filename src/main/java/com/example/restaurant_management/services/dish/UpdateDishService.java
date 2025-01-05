package com.example.restaurant_management.services.dish;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.observers.DishSubject;
import com.example.restaurant_management.repositories.DishRepository;
import com.example.restaurant_management.services.interfaces.ICommandModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateDishService implements ICommandModifier<Dish, Dish> {

    private final DishRepository dishRepository;
    private final DishSubject dishSubject;

    @Autowired
    public UpdateDishService(DishRepository dishRepository, DishSubject dishSubject) {
        this.dishRepository = dishRepository;
        this.dishSubject = dishSubject;
    }

    @Override
    public Dish execute(Long id, Dish dish) {
        Dish existingDish = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato con el id " + id + " no encontrado"));

        existingDish.setName(dish.getName());
        existingDish.setPrice(dish.getPrice());
        Dish updatedDish = dishRepository.save(existingDish);

        dishSubject.notifyObservers(EventType.UPDATE, updatedDish);
        return updatedDish;
    }
}
