package com.example.restaurant_management.utils;

import com.example.restaurant_management.dto.Menu.MenuRequestDTO;
import com.example.restaurant_management.dto.Menu.MenuResponseDTO;
import com.example.restaurant_management.models.Menu;
import com.example.restaurant_management.models.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class MenuDtoConverter {

    public static MenuResponseDTO convertToDto(Menu menu) {
        MenuResponseDTO dto = new MenuResponseDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setDishIds(menu.getDishes().stream().map(Dish::getId).collect(Collectors.toList()));
        return dto;
    }

    public static Menu convertToEntity(MenuRequestDTO dto, List<Dish> dishes) {
        Menu menu = new Menu();
        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setDishes(dishes);
        return menu;
    }
}
