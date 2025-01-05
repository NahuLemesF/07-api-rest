package com.example.restaurant_management.services.order;

import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.repositories.OrderRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetOrderByIdService implements ICommandParametrized<Order, Long> {

    private final OrderRepository orderRepository;

    @Autowired
    public GetOrderByIdService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden con el id " + id + " no encontrada"));
    }
}
