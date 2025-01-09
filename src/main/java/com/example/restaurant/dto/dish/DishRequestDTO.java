package com.example.restaurant.dto.dish;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    private Float price;

    private Long menuId;

    public DishRequestDTO(String name, String description, Float price, Long menuId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.menuId = menuId;
    }

}
