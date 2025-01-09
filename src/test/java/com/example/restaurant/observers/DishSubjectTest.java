package com.example.restaurant.observers;

import com.example.restaurant.models.Dish;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DishSubjectTest {

    @Test
    void testDishSubjectInstantiation() {
        DishSubject dishSubject = new DishSubject();
        assertNotNull(dishSubject);
    }
}
