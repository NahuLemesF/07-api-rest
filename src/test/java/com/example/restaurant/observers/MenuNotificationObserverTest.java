package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuNotificationObserverTest {

    private MenuNotificationObserver menuNotificationObserver;

    @BeforeEach
    void setUp() {
        menuNotificationObserver = new MenuNotificationObserver();
    }

    @Test
    void testUpdateCreate() {
        Menu menu = new Menu();
        menu.setName("Lunch Specials");

        assertDoesNotThrow(() -> menuNotificationObserver.update(EventType.CREATE, menu));
    }

    @Test
    void testUpdateUpdate() {
        Menu menu = new Menu();
        menu.setName("Lunch Specials");

        assertDoesNotThrow(() -> menuNotificationObserver.update(EventType.UPDATE, menu));
    }

    @Test
    void testUpdateDelete() {
        Menu menu = new Menu();
        menu.setName("Lunch Specials");

        assertDoesNotThrow(() -> menuNotificationObserver.update(EventType.DELETE, menu));
    }
}
