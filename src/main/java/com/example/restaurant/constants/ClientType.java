package com.example.restaurant.constants;

public enum ClientType {
    COMMON("Common"),
    FREQUENT("Frequent");

    private final String name;

    ClientType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
