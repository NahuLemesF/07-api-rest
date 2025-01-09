package com.example.restaurant.services.menu;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Menu;
import com.example.restaurant.observers.MenuSubject;
import com.example.restaurant.repositories.IMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;


class AddMenuServiceTest {

    private IMenuRepository menuRepository;
    private MenuSubject menuSubject;
    private AddMenuService addMenuService;

    @BeforeEach
    void setUp() {
        menuRepository = mock(IMenuRepository.class);
        menuSubject = mock(MenuSubject.class);
        addMenuService = new AddMenuService(menuRepository, menuSubject);
    }

    @Test
    @DisplayName("Test AddMenuService execute method - Success")
    void testExecuteSuccess() {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Lunch Menu");
        menu.setDescription("A variety of lunch options");

        addMenuService.execute(menu);

        verify(menuRepository).save(menu);

        ArgumentCaptor<Menu> menuCaptor = ArgumentCaptor.forClass(Menu.class);
        verify(menuSubject).notifyObservers(eq(EventType.CREATE), menuCaptor.capture());
        assertEquals(menu, menuCaptor.getValue());
    }
}