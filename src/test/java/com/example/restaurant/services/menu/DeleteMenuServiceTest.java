package com.example.restaurant.services.menu;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Menu;
import com.example.restaurant.observers.MenuSubject;
import com.example.restaurant.repositories.IMenuRepository;
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
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;

class DeleteMenuServiceTest {

    private IMenuRepository menuRepository;
    private MenuSubject menuSubject;
    private DeleteMenuService deleteMenuService;

    @BeforeEach
    void setUp() {
        menuRepository = mock(IMenuRepository.class);
        menuSubject = mock(MenuSubject.class);
        deleteMenuService = new DeleteMenuService(menuRepository, menuSubject);
    }

    @Test
    @DisplayName("Test DeleteMenuService execute method - Success")
    void testExecuteSuccess() {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Lunch Menu");
        menu.setDescription("A variety of lunch options");

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        deleteMenuService.execute(1L);

        verify(menuRepository).findById(1L);
        verify(menuRepository).deleteById(1L);

        ArgumentCaptor<Menu> menuCaptor = ArgumentCaptor.forClass(Menu.class);
        verify(menuSubject).notifyObservers(eq(EventType.DELETE), menuCaptor.capture());
        assertEquals(menu, menuCaptor.getValue());
    }

    @Test
    @DisplayName("Test DeleteMenuService execute method - Menu Not Found")
    void testExecuteMenuNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deleteMenuService.execute(1L));
        assertEquals("Menú con el id 1 no encontrado", exception.getMessage());

        verify(menuRepository).findById(1L);
        verify(menuRepository, never()).deleteById(1L);
        verify(menuSubject, never()).notifyObservers(any(), any());
    }
}