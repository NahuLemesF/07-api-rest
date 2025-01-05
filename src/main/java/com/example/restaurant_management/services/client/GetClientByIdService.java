package com.example.restaurant_management.services.client;

import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.repositories.ClientRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetClientByIdService implements ICommandParametrized<Client, Long> {

    private final ClientRepository clientRepository;

    @Autowired
    public GetClientByIdService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client execute(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));
    }
}
