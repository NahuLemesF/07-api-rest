package com.example.restaurant_management.services;

import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) {
        clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Long id, Client client) {
        return clientRepository.findById(id).map(c -> {
            c.setName(client.getName());
            c.setLastName(client.getLastName());
            c.setEmail(client.getEmail());
            return clientRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public void markAsFrequent(Long id) {
        Client client = getClientById(id);
        client.setIsFrequent(true);
        clientRepository.save(client);
    }


}
