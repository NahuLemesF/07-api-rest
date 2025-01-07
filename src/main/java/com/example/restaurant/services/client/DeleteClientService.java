package com.example.restaurant.services.client;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.observers.ClientSubject;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteClientService implements ICommandParametrized<Void, Long> {

    private final IClientRepository IClientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public DeleteClientService(IClientRepository IClientRepository, ClientSubject clientSubject) {
        this.IClientRepository = IClientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Void execute(Long id) {
        Client clientToDelete = IClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));

        IClientRepository.deleteById(id);
        clientSubject.notifyObservers(EventType.DELETE, clientToDelete);
        return null;
    }
}
