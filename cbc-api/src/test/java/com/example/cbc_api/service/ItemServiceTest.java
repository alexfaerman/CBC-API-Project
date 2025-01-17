package com.example.cbc_api.service;

import com.example.cbc_api.model.Item;
import com.example.cbc_api.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ItemServiceTest {

    private final ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    private final ItemService itemService = new ItemService(itemRepository, List.of());

    @Test
    void testGetItemById_ItemFound() {
        UUID id = UUID.randomUUID();
        Item mockItem = new Item();
        mockItem.setId(id);

        when(itemRepository.findById(id)).thenReturn(Optional.of(mockItem));

        Item item = itemService.getItemById(id);
        assertEquals(id, item.getId());
    }

    @Test
    void testGetItemById_ItemNotFound() {
        UUID id = UUID.randomUUID();
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> itemService.getItemById(id));
    }
}
