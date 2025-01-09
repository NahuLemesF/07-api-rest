package com.example.restaurant.services.dish;

import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class GetAllDishesServiceTest {

    private IDishRepository dishRepository;
    private GetAllDishesService getAllDishesService;

    @BeforeEach
    void setUp() {
        dishRepository = mock(IDishRepository.class);
        getAllDishesService = new GetAllDishesService(dishRepository);
    }

    @Test
    @DisplayName("Test GetAllDishesService execute method - Success")
    void testExecuteSuccess() {
        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setName("Pasta");
        dish1.setDescription("Delicious pasta with tomato sauce");
        dish1.setPrice(12.99F);

        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setName("Pizza");
        dish2.setDescription("Cheesy pizza with pepperoni");
        dish2.setPrice(15.99F);

        List<Dish> dishes = Arrays.asList(dish1, dish2);

        when(dishRepository.findAll()).thenReturn(dishes);

        List<Dish> result = getAllDishesService.execute();

        assertEquals(dishes, result);
        verify(dishRepository).findAll();
    }
}