package com.example.restaurant.services.client;

import com.example.restaurant.constants.ClientType;
import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.observers.ClientSubject;
import com.example.restaurant.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

class UpdateClientServiceTest {

    private IClientRepository clientRepository;
    private ClientSubject clientSubject;
    private UpdateClientService updateClientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(IClientRepository.class);
        clientSubject = mock(ClientSubject.class);
        updateClientService = new UpdateClientService(clientRepository, clientSubject);
    }

    @Test
    @DisplayName("Test UpdateClientService execute method - Success")
    void testExecuteSuccess() {
        Client existingClient = new Client(1L, "John", "Doe", "john.doe@example.com", ClientType.COMMON);
        Client updatedClient = new Client(1L, "Jane", "Doe", "jane.doe@example.com", ClientType.COMMON);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        Client result = updateClientService.execute(1L, updatedClient);

        assertEquals(updatedClient, result);
        verify(clientRepository).findById(1L);
        verify(clientRepository).save(existingClient);

        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientSubject).notifyObservers(eq(EventType.UPDATE), clientCaptor.capture());
        assertEquals(updatedClient, clientCaptor.getValue());
    }

    @Test
    @DisplayName("Test UpdateClientService execute method - Client Not Found")
    void testExecuteClientNotFound() {
        Client updatedClient = new Client(1L, "Jane", "Doe", "jane.doe@example.com", ClientType.COMMON);

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> updateClientService.execute(1L, updatedClient));
        assertEquals("Cliente con el id 1 no encontrado", exception.getMessage());

        verify(clientRepository).findById(1L);
        verify(clientRepository, never()).save(any(Client.class));
        verify(clientSubject, never()).notifyObservers(any(), any());
    }
}