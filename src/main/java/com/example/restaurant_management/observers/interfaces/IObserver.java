package com.example.restaurant_management.observers.interfaces;

import com.example.restaurant_management.constants.EventType;

public interface IObserver<T> {
    void update(EventType eventType, T entity);
}
