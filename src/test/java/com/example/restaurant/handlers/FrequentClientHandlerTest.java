package com.example.restaurant.handlers;

import com.example.restaurant.models.Client;
import com.example.restaurant.models.Order;
import com.example.restaurant.services.client.IsFrequentClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class FrequentClientHandlerTest {

    private FrequentClientHandler frequentClientHandler;
    private IsFrequentClientService isFrequentClientService;

    @BeforeEach
    void setUp() {
        isFrequentClientService = mock(IsFrequentClientService.class);
        frequentClientHandler = new FrequentClientHandler(isFrequentClientService);
    }

    @Test
    void testHandle() {
        Client client = new Client();
        Order order = new Order();
        order.setClient(client);

        frequentClientHandler.handle(order);

        verify(isFrequentClientService, times(1)).execute(client);
    }
}
