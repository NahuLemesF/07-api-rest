package com.example.restaurant.services.client;

import com.example.restaurant.models.Client;
import com.example.restaurant.constants.EventType;
import com.example.restaurant.observers.ClientSubject;
import com.example.restaurant.repositories.ClientRepository;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IsFrequentClientService implements ICommandParametrized<Void, Client> {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public IsFrequentClientService(OrderRepository orderRepository, ClientRepository clientRepository, ClientSubject clientSubject) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Void execute(Client client) {
        long ordersCount = orderRepository.countOrdersByClientId(client.getId());
        if (ordersCount >= 10 && !Boolean.TRUE.equals(client.getIsFrequent())) {
            client.setIsFrequent(true);
            clientRepository.save(client);
            clientSubject.notifyObservers(EventType.UPDATE, client);
        }
        return null;
    }
}
