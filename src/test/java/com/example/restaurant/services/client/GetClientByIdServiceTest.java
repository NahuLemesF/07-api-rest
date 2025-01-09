package com.example.restaurant.services.client;

import com.example.restaurant.constants.ClientType;
import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class GetClientByIdServiceTest {

    private IClientRepository clientRepository;
    private GetClientByIdService getClientByIdService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(IClientRepository.class);
        getClientByIdService = new GetClientByIdService(clientRepository);
    }

    @Test
    @DisplayName("Test GetClientByIdService execute method - Success")
    void testExecuteSuccess() {
        Client client = new Client(1L, "John", "Doe", "john.doe@example.com", ClientType.COMMON);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = getClientByIdService.execute(1L);

        assertEquals(client, result);
        verify(clientRepository).findById(1L);
    }

    @Test
    @DisplayName("Test GetClientByIdService execute method - Client Not Found")
    void testExecuteClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> getClientByIdService.execute(1L));
        assertEquals("Cliente con el id 1 no encontrado", exception.getMessage());

        verify(clientRepository).findById(1L);
    }
}