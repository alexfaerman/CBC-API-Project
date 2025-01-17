package com.example.cbc_api.controller;

import com.example.cbc_api.mapper.ContentSourceMapper;
import com.example.cbc_api.model.Item;
import com.example.cbc_api.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable UUID id) {
        logger.info("Getting item with id: {}", id);
        return itemService.getItemById(id);
    }

    @PostMapping
    public Item createItem(@RequestBody Map<String, Object> payload, @RequestParam("ContentSource") String source) {
        logger.info("Creating item with source: {} and payload: {}", source, payload);
        ContentSourceMapper mapper = itemService.getMapper(source);
        if (mapper == null) {
            throw new IllegalArgumentException("Invalid content source: " + source);
        }
        return itemService.createItem(payload, source);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable UUID id, @RequestBody Map<String, Object> payload, @RequestParam("ContentSource") String source) {
        logger.info("Updating item with id: {} using source: {} and payload: {}", id, source, payload);
        return itemService.updateItem(id, payload, source);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable UUID id) {
        logger.info("Deleting item with id: {}", id);
        itemService.deleteItem(id);
    }

    @PostMapping("/bulk")
    public List<Item> createItemsBulk(@RequestBody Map<String, Map<String, Object>> bulkPayload) {
        logger.info("Processing bulk item creation: {}", bulkPayload);
        List<Item> createdItems = new ArrayList<>();

        bulkPayload.forEach((source, payload) -> {
            try {
                logger.info("Processing item for source: {}", source);
                Item item = itemService.createItem(payload, source.replace("ContentSource", ""));
                createdItems.add(item);
            } catch (IllegalArgumentException e) {
                logger.error("Error processing source {}: {}", source, e.getMessage());
            }
        });

        return createdItems;
    }
}
