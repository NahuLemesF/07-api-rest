package com.example.restaurant.services.order;

import com.example.restaurant.constants.ClientType;
import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Order;
import com.example.restaurant.observers.OrderSubject;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.handlers.OrderProcessingChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;

class UpdateOrderServiceTest {

    private IOrderRepository orderRepository;
    private OrderSubject orderSubject;
    private OrderProcessingChain orderProcessingChain;
    private UpdateOrderService updateOrderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(IOrderRepository.class);
        orderSubject = mock(OrderSubject.class);
        orderProcessingChain = mock(OrderProcessingChain.class);
        updateOrderService = new UpdateOrderService(orderRepository, orderSubject, orderProcessingChain);
    }

    @Test
    @DisplayName("Test UpdateOrderService execute method - Success")
    void testExecuteSuccess() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        Client client = new Client();
        client.setClientType(ClientType.COMMON);
        existingOrder.setClient(client);

        Order updatedOrder = new Order();
        updatedOrder.setClient(client);
        updatedOrder.setDishes(existingOrder.getDishes());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        Order result = updateOrderService.execute(1L, updatedOrder);

        assertEquals(existingOrder, result);
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(existingOrder);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderSubject).notifyObservers(eq(EventType.UPDATE), orderCaptor.capture());
        assertEquals(existingOrder, orderCaptor.getValue());
    }

    @Test
    @DisplayName("Test UpdateOrderService execute method - Order Not Found")
    void testExecuteOrderNotFound() {
        Order updatedOrder = new Order();

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> updateOrderService.execute(1L, updatedOrder));
        assertEquals("Orden con el id 1 no encontrada", exception.getMessage());

        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
        verify(orderSubject, never()).notifyObservers(any(), any());
    }
}