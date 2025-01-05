package com.example.restaurant_management.services.interfaces;

public interface ICommandParametrized<T, R> {
    T execute(R parameter);
}
