package com.example.restaurant_management.services.order;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.observers.OrderSubject;
import com.example.restaurant_management.repositories.OrderRepository;
import com.example.restaurant_management.services.interfaces.ICommandModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderService implements ICommandModifier<Order, Order> {

    private final OrderRepository orderRepository;
    private final OrderSubject orderSubject;

    @Autowired
    public UpdateOrderService(OrderRepository orderRepository, OrderSubject orderSubject) {
        this.orderRepository = orderRepository;
        this.orderSubject = orderSubject;
    }

    @Override
    public Order execute(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden con el id " + id + " no encontrada"));

        existingOrder.setClient(updatedOrder.getClient());
        existingOrder.setDishes(updatedOrder.getDishes());
        existingOrder.setTotalPrice(updatedOrder.getTotalPrice());

        Order savedOrder = orderRepository.save(existingOrder);
        orderSubject.notifyObservers(EventType.UPDATE, savedOrder);

        return savedOrder;
    }
}
