package com.example.restaurant.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ClientType {
    COMMON("Comun"),
    FREQUENT("Frecuente");

    private final String name;

    ClientType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static ClientType fromValue(String value) {
        return Arrays.stream(ClientType.values())
                .filter(clientType -> clientType.name.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown value: " + value));
    }
}

