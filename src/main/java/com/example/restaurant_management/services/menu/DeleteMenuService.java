package com.example.restaurant_management.services.menu;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Menu;
import com.example.restaurant_management.observers.MenuSubject;
import com.example.restaurant_management.repositories.MenuRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteMenuService implements ICommandParametrized<Void, Long> {

    private final MenuRepository menuRepository;
    private final MenuSubject menuSubject;

    @Autowired
    public DeleteMenuService(MenuRepository menuRepository, MenuSubject menuSubject) {
        this.menuRepository = menuRepository;
        this.menuSubject = menuSubject;
    }

    @Override
    public Void execute(Long id) {
        Menu menuToDelete = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú con el id " + id + " no encontrado"));

        menuRepository.deleteById(id);
        menuSubject.notifyObservers(EventType.DELETE, menuToDelete);
        return null;
    }
}
