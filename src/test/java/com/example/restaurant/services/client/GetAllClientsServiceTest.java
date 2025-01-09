package com.example.restaurant.services.client;

import com.example.restaurant.constants.ClientType;
import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllClientsServiceTest {

    private IClientRepository clientRepository;
    private GetAllClientsService getAllClientsService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(IClientRepository.class);
        getAllClientsService = new GetAllClientsService(clientRepository);
    }

    @Test
    @DisplayName("Test GetAllClientsService execute method")
    void testExecute() {
        Client client1 = new Client(1L, "John", "Doe", "john.doe@example.com", ClientType.COMMON);
        Client client2 = new Client(2L, "Jane", "Doe", "jane.doe@example.com", ClientType.COMMON);
        List<Client> clients = List.of(client1, client2);

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = getAllClientsService.execute();

        assertEquals(clients, result);
        verify(clientRepository).findAll();
    }
}