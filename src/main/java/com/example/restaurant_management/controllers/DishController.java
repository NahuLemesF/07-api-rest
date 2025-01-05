package com.example.restaurant_management.controllers;

import com.example.restaurant_management.dto.Dish.DishRequestDTO;
import com.example.restaurant_management.dto.Dish.DishResponseDTO;
import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.models.Menu;
import com.example.restaurant_management.services.DishService;
import com.example.restaurant_management.services.MenuService;
import com.example.restaurant_management.utils.DishDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;
    private final MenuService menuService;

    @Autowired
    public DishController(DishService dishService, MenuService menuService) {
        this.dishService = dishService;
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<DishResponseDTO> addDish(@RequestBody @Valid DishRequestDTO dishRequestDTO) {
        Menu menu = menuService.getMenuById(dishRequestDTO.getMenuId());
        Dish dish = DishDtoConverter.convertToEntity(dishRequestDTO, menu);
        dishService.addDish(dish);
        DishResponseDTO responseDTO = DishDtoConverter.convertToDto(dish);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDTO> getDishById(@PathVariable Long id) {
        Dish dish = dishService.getDishById(id);
        DishResponseDTO responseDTO = DishDtoConverter.convertToDto(dish);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<DishResponseDTO>> getAllDishes() {
        List<Dish> dishes = dishService.getAllDishes();
        List<DishResponseDTO> responseDTOs = dishes.stream()
                .map(DishDtoConverter::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishResponseDTO> updateDish(@PathVariable Long id, @RequestBody @Valid DishRequestDTO dishRequestDTO) {
        Menu menu = menuService.getMenuById(dishRequestDTO.getMenuId());
        Dish dishEntity = DishDtoConverter.convertToEntity(dishRequestDTO, menu);
        Dish updatedDish = dishService.updateDish(id, dishEntity);
        DishResponseDTO responseDTO = DishDtoConverter.convertToDto(updatedDish);
        return ResponseEntity.ok(responseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/popular")
    public ResponseEntity<DishResponseDTO> markAsPopular(@PathVariable Long id) {
        dishService.markAsPopular(id);
        Dish dish = dishService.getDishById(id);
        DishResponseDTO responseDTO = DishDtoConverter.convertToDto(dish);
        return ResponseEntity.ok(responseDTO);
    }
}
