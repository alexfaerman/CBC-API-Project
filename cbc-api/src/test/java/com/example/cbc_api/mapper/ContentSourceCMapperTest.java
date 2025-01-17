package com.example.cbc_api.mapper;

import com.example.cbc_api.model.Item;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContentSourceCMapperTest {

    private final ContentSourceCMapper mapper = new ContentSourceCMapper();

    @Test
    void testMapToItem() {
        Map<String, Object> payload = Map.of(
                "item_id", "789",
                "name_of_work", "Test Title C",
                "published_by", "Author C",
                "year_of_publication", 2021,
                "typeC", "Novel"
        );

        Item item = mapper.mapToItem(payload);

        assertNotNull(item);
        assertEquals("789", item.getExternalId());
        assertEquals("Test Title C", item.getTitle());
        assertEquals("Author C", item.getAuthor());
        assertEquals(2021, item.getPublishedYear());
        assertEquals("Novel", item.getType());
    }

    @Test
    void testUpdateItem() {
        Item existingItem = new Item();
        existingItem.setExternalId("999");
        existingItem.setTitle("Old Title C");
        existingItem.setAuthor("Old Author C");
        existingItem.setPublishedYear(2017);
        existingItem.setType("Old Type C");

        Map<String, Object> payload = Map.of(
                "item_id", "111",
                "name_of_work", "Updated Title C",
                "published_by", "Updated Author C",
                "year_of_publication", 2023,
                "typeC", "Drama"
        );

        Item updatedItem = mapper.updateItem(existingItem, payload);

        assertEquals("111", updatedItem.getExternalId());
        assertEquals("Updated Title C", updatedItem.getTitle());
        assertEquals("Updated Author C", updatedItem.getAuthor());
        assertEquals(2023, updatedItem.getPublishedYear());
        assertEquals("Drama", updatedItem.getType());
    }
}
