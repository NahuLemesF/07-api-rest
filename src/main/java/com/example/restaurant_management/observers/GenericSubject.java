package com.example.restaurant_management.observers;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.observers.interfaces.IObserver;

import java.util.ArrayList;
import java.util.List;

public class GenericSubject<T> {

    private final List<IObserver<T>> observers = new ArrayList<>();

    public void attach(IObserver<T> observer) {
        observers.add(observer);
    }

    public void detach(IObserver<T> observer) {
        observers.remove(observer);
    }

    public void notifyObservers(EventType eventType, T entity) {
        for (IObserver<T> observer : observers) {
            observer.update(eventType, entity);
        }
    }
}
