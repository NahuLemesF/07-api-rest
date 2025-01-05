package com.example.restaurant_management.utils;

import com.example.restaurant_management.dto.Dish.DishRequestDTO;
import com.example.restaurant_management.dto.Dish.DishResponseDTO;
import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.models.Menu;

public class DishDtoConverter {

    public static DishResponseDTO convertToDto(Dish dish) {
        DishResponseDTO dto = new DishResponseDTO();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setPrice(dish.getPrice());
        dto.setIsPopular(dish.getIsPopular());
        return dto;
    }

    public static Dish convertToEntity(DishRequestDTO dto, Menu menu) {
        Dish dish = new Dish();
        dish.setName(dto.getName());
        dish.setDescription(dto.getDescription());
        dish.setPrice(dto.getPrice());
        dish.setMenu(menu);
        return dish;
    }
}
