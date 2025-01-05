package com.example.restaurant.services.dish;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.DishRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddDishService implements ICommandParametrized<Void, Dish> {

    private final DishRepository dishRepository;
    private final DishSubject dishSubject;

    @Autowired
    public AddDishService(DishRepository dishRepository, DishSubject dishSubject) {
        this.dishRepository = dishRepository;
        this.dishSubject = dishSubject;
    }

    @Override
    public Void execute(Dish dish) {
        dishRepository.save(dish);
        dishSubject.notifyObservers(EventType.CREATE, dish);
        return null;
    }
}
