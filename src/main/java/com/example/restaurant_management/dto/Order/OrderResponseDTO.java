package com.example.restaurant_management.dto.Order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {

    private Long id;
    private Long clientId;
    private List<Long> dishIds;
    private Float totalPrice;
    private LocalDateTime orderDate;

}
