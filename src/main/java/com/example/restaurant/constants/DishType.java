package com.example.restaurant.constants;

public enum DishType {
    COMMON("Common"),
    POPULAR("Popular");

    private final String name;

    DishType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
