package com.example.restaurant.services.interfaces;

public interface ICommandModifier<T, S> {
    T execute(Long id, S value);
}
