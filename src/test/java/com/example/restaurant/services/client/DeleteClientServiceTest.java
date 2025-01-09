package com.example.restaurant.services.client;

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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;

class DeleteClientServiceTest {

    private IClientRepository clientRepository;
    private ClientSubject clientSubject;
    private DeleteClientService deleteClientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(IClientRepository.class);
        clientSubject = mock(ClientSubject.class);
        deleteClientService = new DeleteClientService(clientRepository, clientSubject);
    }

    @Test
    @DisplayName("Test DeleteClientService execute method - Success")
    void testExecuteSuccess() {
        Client client = new Client();
        client.setId(1L);
        client.setName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).deleteById(1L);

        deleteClientService.execute(1L);

        verify(clientRepository).findById(1L);
        verify(clientRepository).deleteById(1L);

        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientSubject).notifyObservers(eq(EventType.DELETE), clientCaptor.capture());
        assertEquals(client, clientCaptor.getValue());
    }

    @Test
    @DisplayName("Test DeleteClientService execute method - Client Not Found")
    void testExecuteClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deleteClientService.execute(1L));
        assertEquals("Cliente con el id 1 no encontrado", exception.getMessage());

        verify(clientRepository).findById(1L);
        verify(clientRepository, never()).deleteById(anyLong());
        verify(clientSubject, never()).notifyObservers(any(), any());
    }
}