package com.example.restaurant_management.services.client;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.observers.ClientSubject;
import com.example.restaurant_management.repositories.ClientRepository;
import com.example.restaurant_management.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteClientService implements ICommandParametrized<Void, Long> {

    private final ClientRepository clientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public DeleteClientService(ClientRepository clientRepository, ClientSubject clientSubject) {
        this.clientRepository = clientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Void execute(Long id) {
        Client clientToDelete = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));

        clientRepository.deleteById(id);
        clientSubject.notifyObservers(EventType.DELETE, clientToDelete);
        return null;
    }
}
