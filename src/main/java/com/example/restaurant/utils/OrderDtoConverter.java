package com.example.restaurant.utils;

import com.example.restaurant.dto.order.OrderResponseDTO;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDtoConverter {

    public static OrderResponseDTO convertToDto(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setClientId(order.getClient().getId());
        dto.setDishIds(order.getDishes().stream().map(Dish::getId).collect(Collectors.toList()));
        dto.setTotalPrice(getTotalPrice(order));
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }

    private static float getTotalPrice(Order order) {
        return order.getTotalPrice() != null ? RoundToTwoDecimals.roundToTwoDecimals(order.getTotalPrice()) : 0.0f;
    }

    public static Order convertToEntity(Client client, List<Dish> dishes) {
        Order order = new Order();
        order.setClient(client);
        order.setDishes(new ArrayList<>(dishes));
        return order;
    }


}
