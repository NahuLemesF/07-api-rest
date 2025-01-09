package com.example.restaurant.services.menu;

import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllMenusServiceTest {

    private IMenuRepository menuRepository;
    private GetAllMenusService getAllMenusService;

    @BeforeEach
    void setUp() {
        menuRepository = mock(IMenuRepository.class);
        getAllMenusService = new GetAllMenusService(menuRepository);
    }

    @Test
    @DisplayName("Test GetAllMenusService execute method - Success")
    void testExecuteSuccess() {
        Menu menu1 = new Menu();
        menu1.setId(1L);
        menu1.setName("Lunch Menu");
        menu1.setDescription("A variety of lunch options");

        Menu menu2 = new Menu();
        menu2.setId(2L);
        menu2.setName("Dinner Menu");
        menu2.setDescription("A variety of dinner options");

        List<Menu> menus = Arrays.asList(menu1, menu2);

        when(menuRepository.findAll()).thenReturn(menus);

        List<Menu> result = getAllMenusService.execute();

        assertEquals(menus, result);
        verify(menuRepository).findAll();
    }
}