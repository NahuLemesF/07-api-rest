package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Menu;
import com.example.restaurant.observers.interfaces.IObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MenuNotificationObserver implements IObserver<Menu> {

    @Override
    public void update(EventType eventType, Menu menu) {
        switch (eventType) {
            case CREATE -> log.info("Notificación: Nuevo menú creado -> {}", menu.getName());
            case UPDATE -> log.info("Notificación: Menú actualizado -> {}", menu.getName());
            case DELETE -> log.info("Notificación: Menú eliminado -> {}", menu.getName());
        }
    }
}
