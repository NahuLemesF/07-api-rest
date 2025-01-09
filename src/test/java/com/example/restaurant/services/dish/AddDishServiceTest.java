package com.example.restaurant.services.dish;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;


class AddDishServiceTest {

    private IDishRepository dishRepository;
    private DishSubject dishSubject;
    private AddDishService addDishService;

    @BeforeEach
    void setUp() {
        dishRepository = mock(IDishRepository.class);
        dishSubject = mock(DishSubject.class);
        addDishService = new AddDishService(dishRepository, dishSubject);
    }

    @Test
    @DisplayName("Test AddDishService execute method - Success")
    void testExecuteSuccess() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Pasta");
        dish.setDescription("Delicious pasta with tomato sauce");
        dish.setPrice(12.99F);

        when(dishRepository.save(dish)).thenReturn(dish);

        Dish result = addDishService.execute(dish);

        assertEquals(dish, result);
        verify(dishRepository).save(dish);

        ArgumentCaptor<Dish> dishCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishSubject).notifyObservers(eq(EventType.CREATE), dishCaptor.capture());
        assertEquals(dish, dishCaptor.getValue());
    }
}