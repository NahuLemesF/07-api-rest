package com.example.restaurant.services.menu;

import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMenusService implements ICommand<List<Menu>> {

    private final IMenuRepository IMenuRepository;

    @Autowired
    public GetAllMenusService(IMenuRepository IMenuRepository) {
        this.IMenuRepository = IMenuRepository;
    }

    @Override
    public List<Menu> execute() {
        return IMenuRepository.findAll();
    }
}
