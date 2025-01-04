package com.example.restaurant_management.dto.Order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clientId;

    private List<Long> dishIds;
}
