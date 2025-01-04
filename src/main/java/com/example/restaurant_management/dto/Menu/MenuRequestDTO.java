package com.example.restaurant_management.dto.Menu;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuRequestDTO {

    @NotBlank(message = "El nombre del menú es obligatorio")
    private String name;

    private String description;

    private List<Long> dishIds;
}
