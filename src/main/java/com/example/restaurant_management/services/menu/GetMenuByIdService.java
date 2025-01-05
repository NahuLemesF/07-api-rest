package com.example.restaurant_management.services.menu;

import com.example.restaurant_management.models.Menu;
import com.example.restaurant_management.repositories.MenuRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetMenuByIdService implements ICommandParametrized<Menu, Long> {

    private final MenuRepository menuRepository;

    @Autowired
    public GetMenuByIdService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu execute(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú con el id " + id + " no encontrado"));
    }
}
