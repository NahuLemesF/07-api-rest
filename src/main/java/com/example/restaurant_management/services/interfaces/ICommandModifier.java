package com.example.restaurant_management.services.interfaces;

public interface ICommandModifier<T, S> {
    T execute(Long id, S value);
}
