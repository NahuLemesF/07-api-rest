package com.example.restaurant_management.services;

import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final DishService dishService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientService clientService, DishService dishService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.dishService = dishService;
    }

    public Order createOrder(Long clientId, List<Long> dishIds) {
        Client client = clientService.getClientById(clientId);
        clientService.checkAndMarkFrequent(client);

        List<Dish> dishes = dishIds.stream()
                .map(dishService::getDishById)
                .collect(Collectors.toList());
        float totalPrice = calculateTotalPrice(dishes, client.getIsFrequent());

        Order order = new Order();
        order.setClient(client);
        order.setDishes(dishes);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden con el id " + id + " no encontrada"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);

        existingOrder.setClient(order.getClient());
        existingOrder.setDishes(order.getDishes());
        existingOrder.setTotalPrice(calculateTotalPrice(order.getDishes(), order.getClient().getIsFrequent()));

        return orderRepository.save(existingOrder);
    }

    private Float calculateTotalPrice(List<Dish> dishes, Boolean isFrequent) {
        float totalPrice = (float) dishes.stream()
                .mapToDouble(Dish::getPrice)
                .sum();

        if (Boolean.TRUE.equals(isFrequent)) {
            totalPrice *= 0.9762F;
        }
        return totalPrice;
    }


}
