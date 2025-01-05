package com.example.restaurant_management.services.order;

import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.repositories.OrderRepository;
import com.example.restaurant_management.services.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllOrdersService implements ICommand<List<Order>> {

    private final OrderRepository orderRepository;

    @Autowired
    public GetAllOrdersService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> execute() {
        return orderRepository.findAll();
    }
}
