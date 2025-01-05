package com.example.restaurant.controllers;

import com.example.restaurant.dto.menu.MenuRequestDTO;
import com.example.restaurant.dto.menu.MenuResponseDTO;
import com.example.restaurant.models.Menu;
import com.example.restaurant.models.Dish;
import com.example.restaurant.services.dish.GetDishByIdService;
import com.example.restaurant.services.menu.AddMenuService;
import com.example.restaurant.services.menu.DeleteMenuService;
import com.example.restaurant.services.menu.GetAllMenusService;
import com.example.restaurant.services.menu.GetMenuByIdService;
import com.example.restaurant.services.menu.UpdateMenuService;
import com.example.restaurant.utils.MenuDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menus")
public class MenuController {

    private final AddMenuService addMenuService;
    private final GetMenuByIdService getMenuByIdService;
    private final GetAllMenusService getAllMenusService;
    private final UpdateMenuService updateMenuService;
    private final DeleteMenuService deleteMenuService;
    private final GetDishByIdService getDishByIdService;

    @Autowired
    public MenuController(AddMenuService addMenuService, GetMenuByIdService getMenuByIdService, GetAllMenusService getAllMenusService, UpdateMenuService updateMenuService, DeleteMenuService deleteMenuService, GetDishByIdService getDishByIdService) {
        this.addMenuService = addMenuService;
        this.getMenuByIdService = getMenuByIdService;
        this.getAllMenusService = getAllMenusService;
        this.updateMenuService = updateMenuService;
        this.deleteMenuService = deleteMenuService;
        this.getDishByIdService = getDishByIdService;
    }

    @PostMapping
    public ResponseEntity<MenuResponseDTO> addMenu(@RequestBody @Valid MenuRequestDTO menuRequestDTO) {
        List<Dish> dishes = menuRequestDTO.getDishIds().stream()
                .map(getDishByIdService::execute)
                .collect(Collectors.toList());
        Menu menu = MenuDtoConverter.convertToEntity(menuRequestDTO, dishes);
        addMenuService.execute(menu);
        MenuResponseDTO responseDTO = MenuDtoConverter.convertToDto(menu);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDTO> getMenuById(@PathVariable Long menuId) {
        Menu menu = getMenuByIdService.execute(menuId);
        MenuResponseDTO responseDTO = MenuDtoConverter.convertToDto(menu);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> getAllMenus() {
        List<Menu> menus = getAllMenusService.execute();
        List<MenuResponseDTO> responseDTOs = menus.stream()
                .map(MenuDtoConverter::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponseDTO> updateMenu(@PathVariable Long menuId, @RequestBody @Valid MenuRequestDTO menuRequestDTO) {
        List<Dish> dishes = menuRequestDTO.getDishIds().stream()
                .map(getDishByIdService::execute)
                .collect(Collectors.toList());
        Menu menuEntity = MenuDtoConverter.convertToEntity(menuRequestDTO, dishes);
        Menu updatedMenu = updateMenuService.execute(menuId, menuEntity);
        MenuResponseDTO responseDTO = MenuDtoConverter.convertToDto(updatedMenu);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        deleteMenuService.execute(menuId);
        return ResponseEntity.noContent().build();
    }
}
