package com.example.restaurant.services.dish;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;


class UpdateDishServiceTest {

    private IDishRepository dishRepository;
    private DishSubject dishSubject;
    private UpdateDishService updateDishService;

    @BeforeEach
    void setUp() {
        dishRepository = mock(IDishRepository.class);
        dishSubject = mock(DishSubject.class);
        updateDishService = new UpdateDishService(dishRepository, dishSubject);
    }

    @Test
    @DisplayName("Test UpdateDishService execute method - Success")
    void testExecuteSuccess() {
        Dish existingDish = new Dish();
        existingDish.setId(1L);
        existingDish.setName("Pasta");
        existingDish.setDescription("Delicious pasta with tomato sauce");
        existingDish.setPrice(12.99F);

        Dish updatedDish = new Dish();
        updatedDish.setName("Spaghetti");
        updatedDish.setDescription("Spaghetti with meatballs");
        updatedDish.setPrice(14.99F);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(existingDish));
        when(dishRepository.save(existingDish)).thenReturn(existingDish);

        Dish result = updateDishService.execute(1L, updatedDish);

        assertEquals(updatedDish.getName(), result.getName());
        assertEquals(updatedDish.getDescription(), result.getDescription());
        assertEquals(updatedDish.getPrice(), result.getPrice());

        verify(dishRepository).findById(1L);
        verify(dishRepository).save(existingDish);

        ArgumentCaptor<Dish> dishCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishSubject).notifyObservers(eq(EventType.UPDATE), dishCaptor.capture());
        assertEquals(existingDish, dishCaptor.getValue());
    }

    @Test
    @DisplayName("Test UpdateDishService execute method - Dish Not Found")
    void testExecuteDishNotFound() {
        Dish updatedDish = new Dish();
        updatedDish.setName("Spaghetti");
        updatedDish.setDescription("Spaghetti with meatballs");
        updatedDish.setPrice(14.99F);

        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> updateDishService.execute(1L, updatedDish));
        assertEquals("Plato con el id 1 no encontrado", exception.getMessage());

        verify(dishRepository).findById(1L);
        verify(dishRepository, never()).save(any(Dish.class));
        verify(dishSubject, never()).notifyObservers(any(), any());
    }
}