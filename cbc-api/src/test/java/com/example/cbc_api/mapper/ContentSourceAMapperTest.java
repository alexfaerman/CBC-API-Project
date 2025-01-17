package com.example.cbc_api.mapper;

import com.example.cbc_api.model.Item;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContentSourceAMapperTest {

    private final ContentSourceAMapper mapper = new ContentSourceAMapper();

    @Test
    void testMapToItem() {
        Map<String, Object> payload = Map.of(
                "itemId", "123",
                "title", "Test Title",
                "author", "Test Author",
                "publishedYear", 2023,
                "typeA", "Book"
        );

        Item item = mapper.mapToItem(payload);

        assertNotNull(item);
        assertEquals("123", item.getExternalId());
        assertEquals("Test Title", item.getTitle());
        assertEquals("Test Author", item.getAuthor());
        assertEquals(2023, item.getPublishedYear());
        assertEquals("Book", item.getType());
    }

    @Test
    void testUpdateItem() {
        Item existingItem = new Item();
        existingItem.setExternalId("123");
        existingItem.setTitle("Old Title");
        existingItem.setAuthor("Old Author");
        existingItem.setPublishedYear(2020);
        existingItem.setType("Old Type");

        Map<String, Object> payload = Map.of(
                "itemId", "456",
                "title", "Updated Title",
                "author", "Updated Author",
                "publishedYear", 2024,
                "typeA", "Magazine"
        );

        Item updatedItem = mapper.updateItem(existingItem, payload);

        assertEquals("456", updatedItem.getExternalId());
        assertEquals("Updated Title", updatedItem.getTitle());
        assertEquals("Updated Author", updatedItem.getAuthor());
        assertEquals(2024, updatedItem.getPublishedYear());
        assertEquals("Magazine", updatedItem.getType());
    }
}
