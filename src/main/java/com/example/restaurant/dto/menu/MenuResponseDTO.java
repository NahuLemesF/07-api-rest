package com.example.restaurant.dto.menu;

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
