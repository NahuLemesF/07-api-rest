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
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;

class DeleteDishServiceTest {

    private IDishRepository dishRepository;
    private DishSubject dishSubject;
    private DeleteDishService deleteDishService;

    @BeforeEach
    void setUp() {
        dishRepository = mock(IDishRepository.class);
        dishSubject = mock(DishSubject.class);
        deleteDishService = new DeleteDishService(dishRepository, dishSubject);
    }

    @Test
    @DisplayName("Test DeleteDishService execute method - Success")
    void testExecuteSuccess() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Pasta");
        dish.setDescription("Delicious pasta with tomato sauce");
        dish.setPrice(12.99F);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        deleteDishService.execute(1L);

        verify(dishRepository).findById(1L);
        verify(dishRepository).deleteById(1L);

        ArgumentCaptor<Dish> dishCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishSubject).notifyObservers(eq(EventType.DELETE), dishCaptor.capture());
        assertEquals(dish, dishCaptor.getValue());
    }

    @Test
    @DisplayName("Test DeleteDishService execute method - Dish Not Found")
    void testExecuteDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deleteDishService.execute(1L));
        assertEquals("Plato con el id 1 no encontrado", exception.getMessage());

        verify(dishRepository).findById(1L);
        verify(dishRepository, never()).deleteById(1L);
        verify(dishSubject, never()).notifyObservers(any(), any());
    }
}