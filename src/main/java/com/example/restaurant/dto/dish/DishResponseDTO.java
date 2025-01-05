package com.example.restaurant.dto.dish;

import com.example.restaurant.constants.DishType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Float price;
    private DishType dishType;
    private Long menuId;

}
