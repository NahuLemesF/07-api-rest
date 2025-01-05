package com.example.restaurant_management.services;

import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.models.Menu;
import com.example.restaurant_management.observers.MenuSubject;
import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuSubject menuSubject;

    @Autowired
    public MenuService(MenuRepository menuRepository, MenuSubject menuSubject) {
        this.menuRepository = menuRepository;
        this.menuSubject = menuSubject;
    }

    public void addMenu(Menu menu) {
        menuRepository.save(menu);
        menuSubject.notifyObservers(EventType.CREATE, menu);
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
        Menu updatedMenu = menuRepository.save(existingMenu);
        menuSubject.notifyObservers(EventType.UPDATE, updatedMenu);
        return updatedMenu;
    }

    public void deleteMenu(Long id) {
        Menu menuToDelete = getMenuById(id);
        menuRepository.deleteById(id);
        menuSubject.notifyObservers(EventType.DELETE, menuToDelete);
    }
}
