package com.example.restaurant_management.dto.Client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDTO {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Boolean isFrequent;

}
