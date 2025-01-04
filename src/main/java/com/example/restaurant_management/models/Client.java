package com.example.restaurant_management.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;

    @Email(message = "Email no válido")
    private String email;
    private Boolean isFrequent = false;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Client(Long id, String name, String lastName, String email, Boolean isFrequent) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.isFrequent = isFrequent;
    }

    public Client() {
    }
}
