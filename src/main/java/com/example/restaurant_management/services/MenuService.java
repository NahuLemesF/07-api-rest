package com.example.restaurant_management.services;

import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.models.Menu;
import com.example.restaurant_management.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void addMenu(Menu menu) {
        menuRepository.save(menu);
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu con el id " + id + " no encontrado"));
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu updateMenu(Long id, Menu menu) {
        Menu existingMenu = getMenuById(id);
        existingMenu.setName(menu.getName());
        existingMenu.setDescription(menu.getDescription());
        updateMenuDishes(id, menu.getDishes());
        return menuRepository.save(existingMenu);
    }

    public Menu updateMenuDishes(Long id, List<Dish> dishes) {
        Menu existingMenu = getMenuById(id);
        dishes.forEach(dish -> dish.setMenu(existingMenu));
        existingMenu.setDishes(dishes);
        return menuRepository.save(existingMenu);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
