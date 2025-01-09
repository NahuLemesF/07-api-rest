package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DishNotificationObserverTest {

    private DishNotificationObserver dishNotificationObserver;

    @BeforeEach
    void setUp() {
        dishNotificationObserver = new DishNotificationObserver();
    }

    @Test
    void testUpdateCreate() {
        Dish dish = new Dish();
        dish.setName("Spaghetti");

        assertDoesNotThrow(() -> dishNotificationObserver.update(EventType.CREATE, dish));
    }

    @Test
    void testUpdateUpdate() {
        Dish dish = new Dish();
        dish.setName("Spaghetti");

        assertDoesNotThrow(() -> dishNotificationObserver.update(EventType.UPDATE, dish));
    }

    @Test
    void testUpdateDelete() {
        Dish dish = new Dish();
        dish.setName("Spaghetti");

        assertDoesNotThrow(() -> dishNotificationObserver.update(EventType.DELETE, dish));
    }
}
