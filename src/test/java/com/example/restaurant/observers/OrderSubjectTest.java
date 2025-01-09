package com.example.restaurant.observers;

import com.example.restaurant.models.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderSubjectTest {

    @Test
    void testOrderSubjectInstantiation() {
        OrderSubject orderSubject = new OrderSubject();
        assertNotNull(orderSubject);
    }
}
