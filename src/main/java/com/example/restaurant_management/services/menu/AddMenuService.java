package com.example.restaurant_management.services.menu;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Menu;
import com.example.restaurant_management.observers.MenuSubject;
import com.example.restaurant_management.repositories.MenuRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddMenuService implements ICommandParametrized<Void, Menu> {

    private final MenuRepository menuRepository;
    private final MenuSubject menuSubject;

    @Autowired
    public AddMenuService(MenuRepository menuRepository, MenuSubject menuSubject) {
        this.menuRepository = menuRepository;
        this.menuSubject = menuSubject;
    }

    @Override
    public Void execute(Menu menu) {
        menuRepository.save(menu);
        menuSubject.notifyObservers(EventType.CREATE, menu);
        return null;
    }
}
