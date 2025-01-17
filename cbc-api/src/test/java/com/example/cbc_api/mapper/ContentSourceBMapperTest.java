package com.example.cbc_api.mapper;

import com.example.cbc_api.model.Item;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContentSourceBMapperTest {

    private final ContentSourceBMapper mapper = new ContentSourceBMapper();

    @Test
    void testMapToItem() {
        Map<String, Object> payload = Map.of(
                "itemNumber", "456",
                "itemTitle", "Test Title B",
                "authorName", "Author B",
                "yearPublished", 2022,
                "typeB", "Journal"
        );

        Item item = mapper.mapToItem(payload);

        assertNotNull(item);
        assertEquals("456", item.getExternalId());
        assertEquals("Test Title B", item.getTitle());
        assertEquals("Author B", item.getAuthor());
        assertEquals(2022, item.getPublishedYear());
        assertEquals("Journal", item.getType());
    }

    @Test
    void testUpdateItem() {
        Item existingItem = new Item();
        existingItem.setExternalId("789");
        existingItem.setTitle("Old Title B");
        existingItem.setAuthor("Old Author B");
        existingItem.setPublishedYear(2018);
        existingItem.setType("Old Type B");

        Map<String, Object> payload = Map.of(
                "itemNumber", "123",
                "itemTitle", "Updated Title B",
                "authorName", "Updated Author B",
                "yearPublished", 2025,
                "typeB", "Research"
        );

        Item updatedItem = mapper.updateItem(existingItem, payload);

        assertEquals("123", updatedItem.getExternalId());
        assertEquals("Updated Title B", updatedItem.getTitle());
        assertEquals("Updated Author B", updatedItem.getAuthor());
        assertEquals(2025, updatedItem.getPublishedYear());
        assertEquals("Research", updatedItem.getType());
    }
}
