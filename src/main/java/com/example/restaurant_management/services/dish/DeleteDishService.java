package com.example.restaurant_management.services.dish;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.observers.DishSubject;
import com.example.restaurant_management.repositories.DishRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteDishService implements ICommandParametrized<Void, Long> {

    private final DishRepository dishRepository;
    private final DishSubject dishSubject;

    @Autowired
    public DeleteDishService(DishRepository dishRepository, DishSubject dishSubject) {
        this.dishRepository = dishRepository;
        this.dishSubject = dishSubject;
    }

    @Override
    public Void execute(Long id) {
        Dish dishToDelete = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato con el id " + id + " no encontrado"));

        dishRepository.deleteById(id);
        dishSubject.notifyObservers(EventType.DELETE, dishToDelete);
        return null;
    }
}
