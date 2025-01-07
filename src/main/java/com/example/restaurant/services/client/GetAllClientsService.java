package com.example.restaurant.services.client;

import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllClientsService implements ICommand<List<Client>> {

    private final IClientRepository IClientRepository;

    @Autowired
    public GetAllClientsService(IClientRepository IClientRepository) {
        this.IClientRepository = IClientRepository;
    }

    @Override
    public List<Client> execute() {
        return IClientRepository.findAll();
    }
}
