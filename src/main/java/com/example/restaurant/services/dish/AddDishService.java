package com.example.restaurant.services.dish;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddDishService implements ICommandParametrized<Dish, Dish> {

    private final IDishRepository dishRepository;
    private final DishSubject dishSubject;

    @Autowired
    public AddDishService(IDishRepository dishRepository, DishSubject dishSubject) {
        this.dishRepository = dishRepository;
        this.dishSubject = dishSubject;
    }

    @Override
    public Dish execute(Dish dish) {
        Dish savedDish = dishRepository.save(dish);
        dishSubject.notifyObservers(EventType.CREATE, savedDish);
        return savedDish;
    }
}
