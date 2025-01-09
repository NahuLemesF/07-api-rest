package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderNotificationObserverTest {

    private OrderNotificationObserver orderNotificationObserver;

    @BeforeEach
    void setUp() {
        orderNotificationObserver = new OrderNotificationObserver();
    }

    @Test
    void testUpdateCreate() {
        Client client = new Client();
        client.setName("John Doe");

        Order order = new Order();
        order.setClient(client);

        assertDoesNotThrow(() -> orderNotificationObserver.update(EventType.CREATE, order));
    }

    @Test
    void testUpdateUpdate() {
        Order order = new Order();
        order.setId(123L);

        assertDoesNotThrow(() -> orderNotificationObserver.update(EventType.UPDATE, order));
    }

    @Test
    void testUpdateDelete() {
        Order order = new Order();
        order.setId(123L);

        assertDoesNotThrow(() -> orderNotificationObserver.update(EventType.DELETE, order));
    }
}
