package com.example.restaurant.services.dish;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.services.interfaces.ICommandModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateDishService implements ICommandModifier<Dish, Dish> {

    private final IDishRepository IDishRepository;
    private final DishSubject dishSubject;

    @Autowired
    public UpdateDishService(IDishRepository IDishRepository, DishSubject dishSubject) {
        this.IDishRepository = IDishRepository;
        this.dishSubject = dishSubject;
    }

    @Override
    public Dish execute(Long id, Dish dish) {
        Dish existingDish = IDishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato con el id " + id + " no encontrado"));

        existingDish.setName(dish.getName());
        existingDish.setDescription(dish.getDescription());
        existingDish.setPrice(dish.getPrice());
        existingDish.setMenu(dish.getMenu());

        Dish updatedDish = IDishRepository.save(existingDish);

        dishSubject.notifyObservers(EventType.UPDATE, updatedDish);
        return updatedDish;
    }

}
