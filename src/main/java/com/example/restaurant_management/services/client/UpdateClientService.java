package com.example.restaurant_management.services.client;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.observers.ClientSubject;
import com.example.restaurant_management.repositories.ClientRepository;
import com.example.restaurant_management.services.interfaces.ICommandModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateClientService implements ICommandModifier<Client, Client> {

    private final ClientRepository clientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public UpdateClientService(ClientRepository clientRepository, ClientSubject clientSubject) {
        this.clientRepository = clientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Client execute(Long id, Client client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));

        existingClient.setName(client.getName());
        existingClient.setLastName(client.getLastName());
        existingClient.setEmail(client.getEmail());
        Client updatedClient = clientRepository.save(existingClient);

        clientSubject.notifyObservers(EventType.UPDATE, updatedClient);
        return updatedClient;
    }
}
