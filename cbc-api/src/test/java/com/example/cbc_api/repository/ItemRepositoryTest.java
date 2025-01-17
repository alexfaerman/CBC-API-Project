package com.example.cbc_api.repository;

import com.example.cbc_api.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void testSaveAndFindItem() {
        Item item = new Item();
        item.setExternalId("123");
        item.setTitle("Test Item");
        item.setAuthor("Author A");
        item.setPublishedYear(2023);
        item.setType("Book");

        Item savedItem = itemRepository.save(item);

        assertNotNull(savedItem.getId(), "Saved item ID should not be null");
        assertTrue(itemRepository.findById(savedItem.getId()).isPresent(), "Saved item should be found");
    }
}
