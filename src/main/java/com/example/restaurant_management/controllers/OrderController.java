package com.example.restaurant_management.controllers;

import com.example.restaurant_management.dto.Order.OrderRequestDTO;
import com.example.restaurant_management.dto.Order.OrderResponseDTO;
import com.example.restaurant_management.models.Order;
import com.example.restaurant_management.services.ClientService;
import com.example.restaurant_management.services.DishService;
import com.example.restaurant_management.services.OrderService;
import com.example.restaurant_management.utils.OrderDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final ClientService clientService;
    private final DishService dishService;

    @Autowired
    public OrderController(OrderService orderService, ClientService clientService, DishService dishService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.dishService = dishService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        Order orderEntity = OrderDtoConverter.convertToEntity(
                orderRequestDTO,
                clientService.getClientById(orderRequestDTO.getClientId()),
                orderRequestDTO.getDishIds().stream().map(dishService::getDishById).toList()
        );
        Order newOrder = orderService.createOrder(orderEntity.getClient().getId(), orderRequestDTO.getDishIds());
        OrderResponseDTO responseDTO = OrderDtoConverter.convertToDto(newOrder);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        OrderResponseDTO responseDTO = OrderDtoConverter.convertToDto(order);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponseDTO> responseDTOs = orders.stream()
                .map(OrderDtoConverter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        Order orderEntity = OrderDtoConverter.convertToEntity(
                orderRequestDTO,
                clientService.getClientById(orderRequestDTO.getClientId()),
                orderRequestDTO.getDishIds().stream().map(dishService::getDishById).toList()
        );
        Order updatedOrder = orderService.updateOrder(id, orderEntity);
        OrderResponseDTO responseDTO = OrderDtoConverter.convertToDto(updatedOrder);
        return ResponseEntity.ok(responseDTO);
    }
}
