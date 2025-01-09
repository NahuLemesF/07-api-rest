package com.example.restaurant.services.client;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.observers.ClientSubject;
import com.example.restaurant.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


class AddClientServiceTest {

    private IClientRepository clientRepository;
    private ClientSubject clientSubject;
    private AddClientService addClientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(IClientRepository.class);
        clientSubject = mock(ClientSubject.class);
        addClientService = new AddClientService(clientRepository, clientSubject);
    }

    @Test
    @DisplayName("Test AddClientService execute method")
    void testExecute() {
        Client client = new Client();
        client.setId(1L);
        client.setName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = addClientService.execute(client);

        assertEquals(client, result);

        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientCaptor.capture());
        assertEquals(client, clientCaptor.getValue());

        verify(clientSubject).notifyObservers(EventType.CREATE, client);
    }
}