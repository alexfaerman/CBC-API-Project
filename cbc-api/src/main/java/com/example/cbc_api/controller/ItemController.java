package com.example.cbc_api.controller;

import com.example.cbc_api.model.Item;
import com.example.cbc_api.service.ItemService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasAuthority('READ_ITEM')")
    @RateLimiter(name = "itemsRateLimiter")
    @GetMapping("/{id}")
    public Item getItem(@PathVariable @NotNull UUID id) {
        log.info("Getting item with id: {}", id);
        return itemService.getItemById(id);
    }

    @RateLimiter(name = "itemsRateLimiter")
    @PostMapping
    public Item createItem(@RequestBody Map<String, Object> payload, @RequestParam("ContentSource") String source) {
        log.info("Creating item with source: {} and payload: {}", source, payload);
        return itemService.createItem(payload, source);
    }

    @RateLimiter(name = "itemsRateLimiter")
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable UUID id, @RequestBody Map<String, Object> payload, @RequestParam("ContentSource") String source) {
        log.info("Updating item with id: {} using source: {} and payload: {}", id, source, payload);
        return itemService.updateItem(id, payload, source);
    }

    @RateLimiter(name = "itemsRateLimiter")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable UUID id) {
        log.info("Deleting item with id: {}", id);
        itemService.deleteItem(id);
    }

    @RateLimiter(name = "itemsRateLimiter")
    @PostMapping("/bulk")
    @Transactional
    public List<Item> createItemsBulk(@RequestBody Map<String, Map<String, Object>> bulkPayload) {
        log.info("Processing bulk item creation: {}", bulkPayload);
        List<Item> createdItems = new ArrayList<>();

        bulkPayload.forEach((source, payload) -> {
            try {
                log.info("Processing item for source: {}", source);
                Item item = itemService.createItem(payload, source.replace("ContentSource", ""));
                createdItems.add(item);
            } catch (IllegalArgumentException e) {
                log.error("Error processing source {}: {}", source, e.getMessage());
            }
        });

        return createdItems;
    }

}
