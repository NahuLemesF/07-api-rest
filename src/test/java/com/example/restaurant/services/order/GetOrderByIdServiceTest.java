package com.example.restaurant.services.order;

import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetOrderByIdServiceTest {

    private IOrderRepository orderRepository;
    private GetOrderByIdService getOrderByIdService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(IOrderRepository.class);
        getOrderByIdService = new GetOrderByIdService(orderRepository);
    }

    @Test
    @DisplayName("Test GetOrderByIdService execute method - Success")
    void testExecuteSuccess() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = getOrderByIdService.execute(1L);

        assertEquals(order, result);
        verify(orderRepository).findById(1L);
    }

    @Test
    @DisplayName("Test GetOrderByIdService execute method - Order Not Found")
    void testExecuteOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> getOrderByIdService.execute(1L));
        assertEquals("Orden con el id 1 no encontrada", exception.getMessage());

        verify(orderRepository).findById(1L);
    }
}