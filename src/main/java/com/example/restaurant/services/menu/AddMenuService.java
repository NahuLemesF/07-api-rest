package com.example.restaurant.services.menu;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Menu;
import com.example.restaurant.observers.MenuSubject;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddMenuService implements ICommandParametrized<Void, Menu> {

    private final IMenuRepository IMenuRepository;
    private final MenuSubject menuSubject;

    @Autowired
    public AddMenuService(IMenuRepository IMenuRepository, MenuSubject menuSubject) {
        this.IMenuRepository = IMenuRepository;
        this.menuSubject = menuSubject;
    }

    @Override
    public Void execute(Menu menu) {
        IMenuRepository.save(menu);
        menuSubject.notifyObservers(EventType.CREATE, menu);
        return null;
    }
}
