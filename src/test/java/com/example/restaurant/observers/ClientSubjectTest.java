package com.example.restaurant.observers;

import com.example.restaurant.models.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientSubjectTest {

    @Test
    void testClientSubjectInstantiation() {
        ClientSubject clientSubject = new ClientSubject();
        assertNotNull(clientSubject);
    }
}
