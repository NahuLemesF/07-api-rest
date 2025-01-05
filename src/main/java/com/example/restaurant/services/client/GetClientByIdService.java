package com.example.restaurant.services.client;

import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.ClientRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
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
