package com.example.restaurant.services.client;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.observers.ClientSubject;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateClientService implements ICommandModifier<Client, Client> {

    private final IClientRepository IClientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public UpdateClientService(IClientRepository IClientRepository, ClientSubject clientSubject) {
        this.IClientRepository = IClientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Client execute(Long id, Client client) {
        Client existingClient = IClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));

        existingClient.setName(client.getName());
        existingClient.setLastName(client.getLastName());
        existingClient.setEmail(client.getEmail());
        Client updatedClient = IClientRepository.save(existingClient);

        clientSubject.notifyObservers(EventType.UPDATE, updatedClient);
        return updatedClient;
    }
}
