package com.example.restaurant.services.menu;

import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetMenuByIdService implements ICommandParametrized<Menu, Long> {

    private final IMenuRepository IMenuRepository;

    @Autowired
    public GetMenuByIdService(IMenuRepository IMenuRepository) {
        this.IMenuRepository = IMenuRepository;
    }

    @Override
    public Menu execute(Long id) {
        return IMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú con el id " + id + " no encontrado"));
    }
}
