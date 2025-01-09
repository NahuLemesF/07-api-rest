package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.interfaces.IObserver;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DishNotificationObserver implements IObserver<Dish> {

    @Override
    public void update(EventType eventType, Dish dish) {
        switch (eventType) {
            case CREATE -> log.info("Notificación: Nuevo plato añadido -> {}", dish.getName());
            case UPDATE -> log.info("Notificación: Plato actualizado -> {}", dish.getName());
            case DELETE -> log.info("Notificación: Plato eliminado -> {}", dish.getName());
        }
    }
}
