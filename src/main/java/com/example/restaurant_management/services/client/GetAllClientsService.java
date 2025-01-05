package com.example.restaurant_management.services.client;

import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.repositories.ClientRepository;
import com.example.restaurant_management.services.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllClientsService implements ICommand<List<Client>> {

    private final ClientRepository clientRepository;

    @Autowired
    public GetAllClientsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> execute() {
        return clientRepository.findAll();
    }
}
