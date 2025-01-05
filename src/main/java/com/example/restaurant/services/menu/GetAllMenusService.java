package com.example.restaurant.services.menu;

import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.MenuRepository;
import com.example.restaurant.services.interfaces.ICommand;
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
