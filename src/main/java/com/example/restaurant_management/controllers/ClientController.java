package com.example.restaurant_management.controllers;

import com.example.restaurant_management.dto.Client.ClientRequestDTO;
import com.example.restaurant_management.dto.Client.ClientResponseDTO;
import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.services.ClientService;
import com.example.restaurant_management.utils.ClientDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> addClient(@RequestBody ClientRequestDTO clientRequest) {
        Client clientEntity = ClientDtoConverter.convertToEntity(clientRequest);
        clientService.addClient(clientEntity);
        ClientResponseDTO responseDTO = ClientDtoConverter.convertToDto(clientEntity);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        ClientResponseDTO responseDTO = ClientDtoConverter.convertToDto(client);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientResponseDTO> responseDTOs = clients.stream()
                .map(ClientDtoConverter::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO client) {
        Client clientEntity = ClientDtoConverter.convertToEntity(client);
        Client updatedClient = clientService.updateClient(id, clientEntity);
        ClientResponseDTO responseDTO = ClientDtoConverter.convertToDto(updatedClient);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
