package com.example.restaurant_management.dto.Dish;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Float price;
    private Boolean isPopular;
    private Long menuId;

}
