package com.example.restaurant_management.services;

import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.observers.ClientSubject;
import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.repositories.ClientRepository;
import com.example.restaurant_management.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public ClientService(ClientRepository clientRepository, OrderRepository orderRepository, ClientSubject clientSubject) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.clientSubject = clientSubject;
    }

    public void addClient(Client client) {
        clientRepository.save(client);
        clientSubject.notifyObservers(EventType.CREATE, client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Long id, Client client) {
        Client existingClient = getClientById(id);
        existingClient.setName(client.getName());
        existingClient.setLastName(client.getLastName());
        existingClient.setEmail(client.getEmail());
        Client updatedClient = clientRepository.save(existingClient);
        clientSubject.notifyObservers(EventType.UPDATE, updatedClient);
        return updatedClient;
    }

    public void deleteClient(Long id) {
        Client clientToDelete = getClientById(id);
        clientRepository.deleteById(id);
        clientSubject.notifyObservers(EventType.DELETE, clientToDelete);
    }

    public void checkAndMarkFrequent(Client client) {
        long ordersCount = orderRepository.countOrdersByClientId(client.getId());

        if (ordersCount >= 10 && Boolean.TRUE.equals(!client.getIsFrequent())) {
            client.setIsFrequent(true);
            clientRepository.save(client);
            clientSubject.notifyObservers(EventType.UPDATE, client);
        }
    }
}
