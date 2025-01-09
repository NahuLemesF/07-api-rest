package com.example.restaurant.services.dish;

import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class GetDishByIdServiceTest {

    private IDishRepository dishRepository;
    private GetDishByIdService getDishByIdService;

    @BeforeEach
    void setUp() {
        dishRepository = mock(IDishRepository.class);
        getDishByIdService = new GetDishByIdService(dishRepository);
    }

    @Test
    @DisplayName("Test GetDishByIdService execute method - Success")
    void testExecuteSuccess() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Pasta");
        dish.setDescription("Delicious pasta with tomato sauce");
        dish.setPrice(12.99F);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        Dish result = getDishByIdService.execute(1L);

        assertEquals(dish, result);
        verify(dishRepository).findById(1L);
    }

    @Test
    @DisplayName("Test GetDishByIdService execute method - Dish Not Found")
    void testExecuteDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> getDishByIdService.execute(1L));
        assertEquals("Plato con el id 1 no encontrado", exception.getMessage());

        verify(dishRepository).findById(1L);
    }
}