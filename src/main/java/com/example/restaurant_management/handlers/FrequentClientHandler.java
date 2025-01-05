package com.example.restaurant_management.handlers;

import com.example.restaurant_management.handlers.interfaces.IOrderHandler;
import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FrequentClientHandler implements IOrderHandler {

    @Autowired
    private ClientService clientService;

    @Override
    public void handle(Order order) {
        clientService.checkAndMarkFrequent(order.getClient());
    }
}
