package com.example.restaurant.services.menu;

import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetMenuByIdServiceTest {

    private IMenuRepository menuRepository;
    private GetMenuByIdService getMenuByIdService;

    @BeforeEach
    void setUp() {
        menuRepository = mock(IMenuRepository.class);
        getMenuByIdService = new GetMenuByIdService(menuRepository);
    }

    @Test
    @DisplayName("Test GetMenuByIdService execute method - Success")
    void testExecuteSuccess() {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Lunch Menu");
        menu.setDescription("A variety of lunch options");

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        Menu result = getMenuByIdService.execute(1L);

        assertEquals(menu, result);
        verify(menuRepository).findById(1L);
    }

    @Test
    @DisplayName("Test GetMenuByIdService execute method - Menu Not Found")
    void testExecuteMenuNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> getMenuByIdService.execute(1L));
        assertEquals("Menú con el id 1 no encontrado", exception.getMessage());

        verify(menuRepository).findById(1L);
    }
}