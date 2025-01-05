package com.example.restaurant_management.services.interfaces;

public interface ICommand<T> {
    T execute();
}