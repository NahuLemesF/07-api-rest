package com.example.restaurant.services.client;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.observers.ClientSubject;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddClientService implements ICommandParametrized<Client, Client> {

    private final IClientRepository IClientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public AddClientService(IClientRepository IClientRepository, ClientSubject clientSubject) {
        this.IClientRepository = IClientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Client execute(Client client) {
        Client savedClient = IClientRepository.save(client);
        clientSubject.notifyObservers(EventType.CREATE, savedClient);
        return savedClient;
    }
}
