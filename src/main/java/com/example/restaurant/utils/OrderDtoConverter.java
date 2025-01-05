package com.example.restaurant.utils;

import com.example.restaurant.dto.order.OrderRequestDTO;
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
        dto.setTotalPrice(RoundToTwoDecimals.roundToTwoDecimals(order.getTotalPrice()));
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }

    public static Order convertToEntity(OrderRequestDTO dto, Client client, List<Dish> dishes) {
        Order order = new Order();
        order.setClient(client);
        order.setDishes(new ArrayList<>(dishes));
        return order;
    }


}
