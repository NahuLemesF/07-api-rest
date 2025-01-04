package com.example.restaurant_management.dto.Menu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponseDTO {

    private Long id;
    private String name;
    private String description;
    private List<Long> dishIds;

}
