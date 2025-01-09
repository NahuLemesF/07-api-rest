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

class UpdateMenuServiceTest {

    private IMenuRepository menuRepository;
    private MenuSubject menuSubject;
    private UpdateMenuService updateMenuService;

    @BeforeEach
    void setUp() {
        menuRepository = mock(IMenuRepository.class);
        menuSubject = mock(MenuSubject.class);
        updateMenuService = new UpdateMenuService(menuRepository, menuSubject);
    }

    @Test
    @DisplayName("Test UpdateMenuService execute method - Success")
    void testExecuteSuccess() {
        Menu existingMenu = new Menu();
        existingMenu.setId(1L);
        existingMenu.setName("Lunch Menu");
        existingMenu.setDescription("A variety of lunch options");

        Menu updatedMenu = new Menu();
        updatedMenu.setName("Updated Lunch Menu");
        updatedMenu.setDescription("Updated description");

        when(menuRepository.findById(1L)).thenReturn(Optional.of(existingMenu));
        when(menuRepository.save(existingMenu)).thenReturn(existingMenu);

        Menu result = updateMenuService.execute(1L, updatedMenu);

        assertEquals(updatedMenu.getName(), result.getName());
        assertEquals(updatedMenu.getDescription(), result.getDescription());

        verify(menuRepository).findById(1L);
        verify(menuRepository).save(existingMenu);

        ArgumentCaptor<Menu> menuCaptor = ArgumentCaptor.forClass(Menu.class);
        verify(menuSubject).notifyObservers(eq(EventType.UPDATE), menuCaptor.capture());
        assertEquals(existingMenu, menuCaptor.getValue());
    }

    @Test
    @DisplayName("Test UpdateMenuService execute method - Menu Not Found")
    void testExecuteMenuNotFound() {
        Menu updatedMenu = new Menu();
        updatedMenu.setName("Updated Lunch Menu");
        updatedMenu.setDescription("Updated description");

        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> updateMenuService.execute(1L, updatedMenu));
        assertEquals("Menú con el id 1 no encontrado", exception.getMessage());

        verify(menuRepository).findById(1L);
        verify(menuRepository, never()).save(any(Menu.class));
        verify(menuSubject, never()).notifyObservers(any(), any());
    }
}