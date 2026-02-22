package com.example.cbc_api.service;

import com.example.cbc_api.mapper.ContentSourceMapper;
import com.example.cbc_api.mapper.ContentSourceMapperFactory;
import com.example.cbc_api.model.Item;
import com.example.cbc_api.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    private ItemRepository itemRepository;
    private ContentSourceMapperFactory mapperFactory;
    private ContentSourceMapper mockMapper;
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemRepository = Mockito.mock(ItemRepository.class);
        mapperFactory = Mockito.mock(ContentSourceMapperFactory.class);
        mockMapper = Mockito.mock(ContentSourceMapper.class);
        itemService = new ItemService(itemRepository, mapperFactory);
    }

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

    @Test
    void testCreateItem() {
        UUID mockId = UUID.randomUUID();
        Map<String, Object> payload = Map.of(
                "itemId", "12345",
                "title", "Test Title",
                "author", "John Doe",
                "publishedYear", 2023,
                "typeA", "article"
        );
        Item mockItem = new Item();
        mockItem.setId(mockId);
        mockItem.setExternalId("12345");
        mockItem.setTitle("Test Title");
        mockItem.setAuthor("John Doe");
        mockItem.setPublishedYear(2023);
        mockItem.setType("article");

        when(mapperFactory.getMapper("A")).thenReturn(mockMapper);
        when(mockMapper.mapToItem(payload)).thenReturn(mockItem);
        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        Item createdItem = itemService.createItem(payload, "A");

        assertNotNull(createdItem);
        assertEquals("12345", createdItem.getExternalId());
        assertEquals("Test Title", createdItem.getTitle());
        assertEquals("John Doe", createdItem.getAuthor());
        assertEquals(2023, createdItem.getPublishedYear());
        assertEquals("article", createdItem.getType());

        verify(mapperFactory, times(1)).getMapper("A");
        verify(mockMapper, times(1)).mapToItem(payload);
        verify(itemRepository, times(1)).save(mockItem);
    }
}
