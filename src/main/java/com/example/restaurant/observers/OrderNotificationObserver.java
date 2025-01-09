package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Order;
import com.example.restaurant.observers.interfaces.IObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderNotificationObserver implements IObserver<Order> {

    @Override
    public void update(EventType eventType, Order order) {
        switch (eventType) {
            case CREATE -> log.info("Notificación: Nueva orden creada para el cliente -> {}", order.getClient().getName());
            case UPDATE -> log.info("Notificación: Orden actualizada -> ID {}", order.getId());
            case DELETE -> log.info("Notificación: Orden eliminada -> ID {}", order.getId());
        }
    }
}
