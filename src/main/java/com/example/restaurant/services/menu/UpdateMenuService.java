package com.example.restaurant.services.menu;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Menu;
import com.example.restaurant.observers.MenuSubject;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateMenuService implements ICommandModifier<Menu, Menu> {

    private final IMenuRepository IMenuRepository;
    private final MenuSubject menuSubject;

    @Autowired
    public UpdateMenuService(IMenuRepository IMenuRepository, MenuSubject menuSubject) {
        this.IMenuRepository = IMenuRepository;
        this.menuSubject = menuSubject;
    }

    @Override
    public Menu execute(Long id, Menu updatedMenu) {
        Menu existingMenu = IMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú con el id " + id + " no encontrado"));

        existingMenu.setName(updatedMenu.getName());
        existingMenu.setDescription(updatedMenu.getDescription());

        Menu savedMenu = IMenuRepository.save(existingMenu);
        menuSubject.notifyObservers(EventType.UPDATE, savedMenu);

        return savedMenu;
    }
}
