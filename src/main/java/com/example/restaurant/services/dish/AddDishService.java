package com.example.restaurant.services.dish;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddDishService implements ICommandParametrized<Void, Dish> {

    private final IDishRepository IDishRepository;
    private final DishSubject dishSubject;

    @Autowired
    public AddDishService(IDishRepository IDishRepository, DishSubject dishSubject) {
        this.IDishRepository = IDishRepository;
        this.dishSubject = dishSubject;
    }

    @Override
    public Void execute(Dish dish) {
        IDishRepository.save(dish);
        dishSubject.notifyObservers(EventType.CREATE, dish);
        return null;
    }
}
