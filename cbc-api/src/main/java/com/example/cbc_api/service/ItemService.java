package com.example.cbc_api.service;

import com.example.cbc_api.mapper.ContentSourceMapper;
import com.example.cbc_api.mapper.ContentSourceMapperFactory;
import com.example.cbc_api.model.Item;
import com.example.cbc_api.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ContentSourceMapperFactory mapperFactory;

    //What Happens When This Constructor Is Called?
    //Spring Boot detects ItemService as a @Service and creates an instance.
    //Spring scans for:
    //A bean of type ItemRepository and injects it.
    //All beans (@Component) implementing ContentSourceMapper (ContentSourceAMapper, ContentSourceBMapper, etc.).
    //Spring calls the constructor, passing the dependencies.
    //The constructor populates the mappers map with all available content source mappers.

    public ItemService(ItemRepository itemRepository, ContentSourceMapperFactory mapperFactory) {
        this.itemRepository = itemRepository;
        this.mapperFactory = mapperFactory;
    }

    public Item getItemById(UUID id) {
        log.debug("Getting item from database with id: {}", id);
        return itemRepository.findById(id).orElseThrow(() -> {
            log.warn("Item with id {} not found", id);
            return new NoSuchElementException("Item with id " + id + " not found");
        });
    }

    public Item createItem(Map<String, Object> payload, String source) {
        log.debug("Creating item for source: {}", source);
        ContentSourceMapper mapper = mapperFactory.getMapper(source);
        Item item = mapper.mapToItem(payload);
        log.info("Item mapped successfully: {}", item);
        return itemRepository.save(item);
    }

    public Item updateItem(UUID id, Map<String, Object> payload, String source) {
        log.debug("Updating item with id: {} for source: {}", id, source);
        ContentSourceMapper mapper = mapperFactory.getMapper(source);
        Item existingItem = getItemById(id);
        Item updatedItem = mapper.updateItem(existingItem, payload);
        log.info("Item updated successfully: {}", updatedItem);
        return itemRepository.save(updatedItem);
    }

    public void deleteItem(UUID id) {
        log.debug("Deleting item from database with id: {}", id);
        getItemById(id); // throws NoSuchElementException → 404 if not found
        itemRepository.deleteById(id);
        log.info("Item with id {} deleted successfully", id);
    }

}