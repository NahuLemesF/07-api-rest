package com.example.restaurant_management.services.order;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.observers.OrderSubject;
import com.example.restaurant_management.repositories.OrderRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteOrderService implements ICommandParametrized<Void, Long> {

    private final OrderRepository orderRepository;
    private final OrderSubject orderSubject;

    @Autowired
    public DeleteOrderService(OrderRepository orderRepository, OrderSubject orderSubject) {
        this.orderRepository = orderRepository;
        this.orderSubject = orderSubject;
    }

    @Override
    public Void execute(Long id) {
        Order orderToDelete = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden con el id " + id + " no encontrada"));

        orderRepository.deleteById(id);
        orderSubject.notifyObservers(EventType.DELETE, orderToDelete);
        return null;
    }
}
