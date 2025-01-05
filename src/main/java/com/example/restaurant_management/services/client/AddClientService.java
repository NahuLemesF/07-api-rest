package com.example.restaurant_management.services.client;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.observers.ClientSubject;
import com.example.restaurant_management.repositories.ClientRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddClientService implements ICommandParametrized<Client, Client> {

    private final ClientRepository clientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public AddClientService(ClientRepository clientRepository, ClientSubject clientSubject) {
        this.clientRepository = clientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Client execute(Client client) {
        Client savedClient = clientRepository.save(client);
        clientSubject.notifyObservers(EventType.CREATE, savedClient);
        return savedClient;
    }
}
