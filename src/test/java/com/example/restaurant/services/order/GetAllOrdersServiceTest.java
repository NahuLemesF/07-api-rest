package com.example.restaurant.services.order;

import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllOrdersServiceTest {

    private IOrderRepository orderRepository;
    private GetAllOrdersService getAllOrdersService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(IOrderRepository.class);
        getAllOrdersService = new GetAllOrdersService(orderRepository);
    }

    @Test
    @DisplayName("Test GetAllOrdersService execute method - Success")
    void testExecuteSuccess() {
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = getAllOrdersService.execute();

        assertEquals(orders, result);
        verify(orderRepository).findAll();
    }
}