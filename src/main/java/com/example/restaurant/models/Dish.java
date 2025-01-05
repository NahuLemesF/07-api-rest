package com.example.restaurant.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Boolean isPopular = false;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToMany(mappedBy = "dishes")
    private List<Order> orders = new ArrayList<>();

    public Dish(Long id, String name, String description, Float price, Boolean isPopular) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isPopular = isPopular;
    }

    public Dish() {
    }
}
