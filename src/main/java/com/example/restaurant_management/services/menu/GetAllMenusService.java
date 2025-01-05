package com.example.restaurant_management.services.menu;

import com.example.restaurant_management.models.Menu;
import com.example.restaurant_management.repositories.MenuRepository;
import com.example.restaurant_management.services.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMenusService implements ICommand<List<Menu>> {

    private final MenuRepository menuRepository;

    @Autowired
    public GetAllMenusService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> execute() {
        return menuRepository.findAll();
    }
}
