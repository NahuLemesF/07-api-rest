package com.example.restaurant.observers;

import com.example.restaurant.models.Menu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MenuSubjectTest {

    @Test
    void testMenuSubjectInstantiation() {
        MenuSubject menuSubject = new MenuSubject();
        assertNotNull(menuSubject);
    }
}
