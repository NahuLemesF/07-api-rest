package com.example.restaurant_management.services;

import com.example.restaurant_management.handlers.OrderProcessingChain;
import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.observers.OrderSubject;
import com.example.restaurant_management.constants.EventType;
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
    private final OrderProcessingChain orderProcessingChain;
    private final OrderSubject orderSubject;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientService clientService, DishService dishService, OrderProcessingChain orderProcessingChain, OrderSubject orderSubject) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.dishService = dishService;
        this.orderProcessingChain = orderProcessingChain;
        this.orderSubject = orderSubject;
    }

    public Order createOrder(Long clientId, List<Long> dishIds) {
        Client client = clientService.getClientById(clientId);

        List<Dish> dishes = dishIds.stream()
                .map(dishService::getDishById)
                .collect(Collectors.toList());

        Order order = new Order();
        order.setClient(client);
        order.setDishes(dishes);
        order.setOrderDate(LocalDateTime.now());

        orderProcessingChain.process(order);

        float totalPrice = calculateTotalPrice(order.getDishes(), order.getClient().getIsFrequent());
        order.setTotalPrice(totalPrice);

        Order createdOrder = orderRepository.save(order);
        orderSubject.notifyObservers(EventType.CREATE, createdOrder);
        return createdOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden con el id " + id + " no encontrada"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        Order orderToDelete = getOrderById(id);
        orderRepository.deleteById(id);
        orderSubject.notifyObservers(EventType.DELETE, orderToDelete);
    }

    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);

        existingOrder.setClient(order.getClient());
        existingOrder.setDishes(order.getDishes());
        existingOrder.setTotalPrice(calculateTotalPrice(order.getDishes(), order.getClient().getIsFrequent()));

        Order updatedOrder = orderRepository.save(existingOrder);
        orderSubject.notifyObservers(EventType.UPDATE, updatedOrder);
        return updatedOrder;
    }

    private Float calculateTotalPrice(List<Dish> dishes, Boolean isFrequent) {
        float totalPrice = (float) dishes.stream()
                .mapToDouble(dish -> {
                    float price = dish.getPrice();
                    return checkIsPopular(dish, price);
                })
                .sum();

        totalPrice = checkIsFrequent(isFrequent, totalPrice);
        return totalPrice;
    }

    private static float checkIsFrequent(Boolean isFrequent, float totalPrice) {
        if (Boolean.TRUE.equals(isFrequent)) {
            totalPrice *= 0.9762F;
        }
        return totalPrice;
    }

    private static float checkIsPopular(Dish dish, float price) {
        return Boolean.TRUE.equals(dish.getIsPopular()) ? price * 1.0573F : price;
    }
}
