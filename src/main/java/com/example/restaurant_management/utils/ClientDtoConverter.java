package com.example.restaurant_management.utils;

import com.example.restaurant_management.dto.Client.ClientRequestDTO;
import com.example.restaurant_management.dto.Client.ClientResponseDTO;
import com.example.restaurant_management.models.Client;

public class ClientDtoConverter {

    public static ClientResponseDTO convertToDto(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setLastName(client.getLastName());
        dto.setEmail(client.getEmail());
        dto.setIsFrequent(client.getIsFrequent());
        return dto;
    }

    public static Client convertToEntity(ClientRequestDTO dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        return client;
    }
}
