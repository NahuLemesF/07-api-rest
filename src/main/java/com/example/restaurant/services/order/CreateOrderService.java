package com.example.restaurant.services.order;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.handlers.OrderProcessingChain;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.observers.OrderSubject;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.services.client.GetClientByIdService;
import com.example.restaurant.services.dish.GetDishByIdService;
import com.example.restaurant.services.interfaces.ICommandModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateOrderService implements ICommandModifier<Order, List<Long>> {

    private final OrderRepository orderRepository;
    private final GetClientByIdService getClientByIdService;
    private final GetDishByIdService getDishByIdService;
    private final OrderProcessingChain orderProcessingChain;
    private final OrderSubject orderSubject;

    @Autowired
    public CreateOrderService(OrderRepository orderRepository, GetClientByIdService getClientByIdService, GetDishByIdService getDishByIdService, OrderProcessingChain orderProcessingChain, OrderSubject orderSubject) {
        this.orderRepository = orderRepository;
        this.getClientByIdService = getClientByIdService;
        this.getDishByIdService = getDishByIdService;
        this.orderProcessingChain = orderProcessingChain;
        this.orderSubject = orderSubject;
    }

    @Override
    public Order execute(Long clientId, List<Long> dishIds) {
        Client client = getClientByIdService.execute(clientId);

        List<Dish> dishes = dishIds.stream()
                .map(getDishByIdService::execute)
                .collect(Collectors.toList());

        Order order = new Order();
        order.setClient(client);
        order.setDishes(dishes);
        order.setOrderDate(LocalDateTime.now());

        orderProcessingChain.process(order);

        float totalPrice = calculateTotalPrice(dishes, client.getIsFrequent());
        order.setTotalPrice(totalPrice);

        Order createdOrder = orderRepository.save(order);
        orderSubject.notifyObservers(EventType.CREATE, createdOrder);
        return createdOrder;
    }

    private float calculateTotalPrice(List<Dish> dishes, Boolean isFrequent) {
        float totalPrice = (float) dishes.stream()
                .mapToDouble(dish -> pricePopular(dish))
                .sum();
        totalPrice = priceFrequent(isFrequent, totalPrice);

        return totalPrice;
    }

    private static float pricePopular(Dish dish) {
        return Boolean.TRUE.equals(dish.getIsPopular()) ? dish.getPrice() * 1.0573F : dish.getPrice();
    }

    private static float priceFrequent(Boolean isFrequent, float totalPrice) {
        if (Boolean.TRUE.equals(isFrequent)) {
            totalPrice *= 0.9762F;
        }
        return totalPrice;
    }
}
