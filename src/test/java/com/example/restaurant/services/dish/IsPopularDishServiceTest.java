package com.example.restaurant.services.dish;

import com.example.restaurant.constants.DishType;
import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.repositories.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;

class IsPopularDishServiceTest {

    private IDishRepository dishRepository;
    private IOrderRepository orderRepository;
    private DishSubject dishSubject;
    private IsPopularDishService isPopularDishService;

    @BeforeEach
    void setUp() {
        dishRepository = mock(IDishRepository.class);
        orderRepository = mock(IOrderRepository.class);
        dishSubject = mock(DishSubject.class);
        isPopularDishService = new IsPopularDishService(dishRepository, orderRepository, dishSubject);
    }

    @Test
    @DisplayName("Test IsPopularDishService execute method - Mark as Popular")
    void testExecuteMarkAsPopular() {
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

        when(orderRepository.countByDishesId(1L)).thenReturn(150L);
        when(orderRepository.countByDishesId(2L)).thenReturn(50L);

        isPopularDishService.execute(dishes);

        verify(orderRepository).countByDishesId(1L);
        verify(orderRepository).countByDishesId(2L);

        verify(dishRepository).save(dish1);
        verify(dishSubject).notifyObservers(eq(EventType.UPDATE), eq(dish1));

        assertEquals(DishType.POPULAR, dish1.getDishType());
        assertEquals(DishType.COMMON, dish2.getDishType());
    }

    @Test
    @DisplayName("Test IsPopularDishService execute method - No Popular Dishes")
    void testExecuteNoPopularDishes() {
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

        when(orderRepository.countByDishesId(1L)).thenReturn(50L);
        when(orderRepository.countByDishesId(2L)).thenReturn(30L);

        isPopularDishService.execute(dishes);

        verify(orderRepository).countByDishesId(1L);
        verify(orderRepository).countByDishesId(2L);

        verify(dishRepository, never()).save(any(Dish.class));
        verify(dishSubject, never()).notifyObservers(any(), any());

        assertEquals(DishType.COMMON, dish1.getDishType());
        assertEquals(DishType.COMMON, dish2.getDishType());
    }
}
