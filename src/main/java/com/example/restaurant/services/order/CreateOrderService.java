package com.example.restaurant.services.order;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.constants.ClientType;
import com.example.restaurant.constants.DishType;
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
import java.util.Map;
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

        float totalPrice = calculateTotalPrice(dishes, client.getClientType());
        order.setTotalPrice(totalPrice);

        Order createdOrder = orderRepository.save(order);
        orderSubject.notifyObservers(EventType.CREATE, createdOrder);
        return createdOrder;
    }

    private float calculateTotalPrice(List<Dish> dishes, ClientType clientType) {
        float totalPrice = (float) dishes.stream()
                .mapToDouble(this::priceBasedOnDishType)
                .sum();
        totalPrice = priceBasedOnClientType(clientType, totalPrice);

        return totalPrice;
    }

    private static final Map<DishType, Float> DISH_TYPE_MULTIPLIER = Map.of(
            DishType.POPULAR, 1.0573F,
            DishType.COMMON, 1.0F
    );

    private float priceBasedOnDishType(Dish dish) {
        return dish.getPrice() * DISH_TYPE_MULTIPLIER.getOrDefault(dish.getDishType(), 1.0F);
    }


    private static final Map<ClientType, Float> CLIENT_TYPE_DISCOUNT = Map.of(
            ClientType.FREQUENT, 0.9762F,
            ClientType.COMMON, 1.0F
    );

    private float priceBasedOnClientType(ClientType clientType, float totalPrice) {
        return totalPrice * CLIENT_TYPE_DISCOUNT.getOrDefault(clientType, 1.0F);
    }

}
