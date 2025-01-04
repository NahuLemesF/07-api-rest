package com.example.restaurant_management.utils;

import com.example.restaurant_management.dto.Order.OrderRequestDTO;
import com.example.restaurant_management.dto.Order.OrderResponseDTO;
import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.models.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDtoConverter {

    public static OrderResponseDTO convertToDto(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setClientId(order.getClient().getId());
        dto.setDishIds(order.getDishes().stream().map(Dish::getId).collect(Collectors.toList()));
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }

    public static Order convertToEntity(OrderRequestDTO dto, Client client, List<Dish> dishes) {
        Order order = new Order();
        order.setClient(client);
        order.setDishes(dishes);
        return order;
    }
}
