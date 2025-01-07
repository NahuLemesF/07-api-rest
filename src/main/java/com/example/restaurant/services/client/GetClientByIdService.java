package com.example.restaurant.services.client;

import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetClientByIdService implements ICommandParametrized<Client, Long> {

    private final IClientRepository IClientRepository;

    @Autowired
    public GetClientByIdService(IClientRepository IClientRepository) {
        this.IClientRepository = IClientRepository;
    }

    @Override
    public Client execute(Long id) {
        return IClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));
    }
}
