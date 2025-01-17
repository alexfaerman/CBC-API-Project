package com.example.cbc_api.controller;

import com.example.cbc_api.model.Item;
import com.example.cbc_api.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void testGetItem() throws Exception {
        UUID itemId = UUID.randomUUID();
        Item mockItem = new Item();
        mockItem.setId(itemId);
        mockItem.setExternalId("12345");
        mockItem.setTitle("Sample Title");
        mockItem.setAuthor("Sample Author");
        mockItem.setPublishedYear(2023);
        mockItem.setType("Sample Type");

        when(itemService.getItemById(itemId)).thenReturn(mockItem);

        // Perform the GET request
        mockMvc.perform(get("/items/" + itemId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(itemId.toString()))
                .andExpect(jsonPath("$.externalId").value("12345"))
                .andExpect(jsonPath("$.title").value("Sample Title"))
                .andExpect(jsonPath("$.author").value("Sample Author"))
                .andExpect(jsonPath("$.publishedYear").value(2023))
                .andExpect(jsonPath("$.type").value("Sample Type"));
    }

    @Test
    void testBulkCreateItems() throws Exception {
        Item item1 = new Item();
        item1.setId(UUID.randomUUID());
        item1.setExternalId("A12345");
        item1.setTitle("Article A");
        item1.setAuthor("John Doe");
        item1.setPublishedYear(2023);
        item1.setType("article");

        Item item2 = new Item();
        item2.setId(UUID.randomUUID());
        item2.setExternalId("B67890");
        item2.setTitle("Story B");
        item2.setAuthor("Jane Smith");
        item2.setPublishedYear(2022);
        item2.setType("story");

        Item item3 = new Item();
        item3.setId(UUID.randomUUID());
        item3.setExternalId("C54321");
        item3.setTitle("Video C");
        item3.setAuthor("Sam Johnson");
        item3.setPublishedYear(2024);
        item3.setType("video");

        when(itemService.createItem(anyMap(), eq("A"))).thenReturn(item1);
        when(itemService.createItem(anyMap(), eq("B"))).thenReturn(item2);
        when(itemService.createItem(anyMap(), eq("C"))).thenReturn(item3);

        // Perform the POST request
        mockMvc.perform(post("/items/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "ContentSourceA": {
                                "itemId": "A12345",
                                "title": "Article A",
                                "author": "John Doe",
                                "publishedYear": 2023,
                                "typeA": "article"
                              },
                              "ContentSourceB": {
                                "itemNumber": "B67890",
                                "itemTitle": "Story B",
                                "authorName": "Jane Smith",
                                "yearPublished": 2022,
                                "typeB": "story"
                              },
                              "ContentSourceC": {
                                "item_id": "C54321",
                                "name_of_work": "Video C",
                                "published_by": "Sam Johnson",
                                "year_of_publication": 2024,
                                "typeC": "video"
                              }
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].externalId").value("A12345"))
                .andExpect(jsonPath("$[0].title").value("Article A"))
                .andExpect(jsonPath("$[0].author").value("John Doe"))
                .andExpect(jsonPath("$[0].type").value("article"))
                .andExpect(jsonPath("$[1].externalId").value("B67890"))
                .andExpect(jsonPath("$[1].title").value("Story B"))
                .andExpect(jsonPath("$[1].author").value("Jane Smith"))
                .andExpect(jsonPath("$[1].type").value("story"))
                .andExpect(jsonPath("$[2].externalId").value("C54321"))
                .andExpect(jsonPath("$[2].title").value("Video C"))
                .andExpect(jsonPath("$[2].author").value("Sam Johnson"))
                .andExpect(jsonPath("$[2].type").value("video"));

        Mockito.verify(itemService).createItem(anyMap(), eq("A"));
        Mockito.verify(itemService).createItem(anyMap(), eq("B"));
        Mockito.verify(itemService).createItem(anyMap(), eq("C"));
    }
}
