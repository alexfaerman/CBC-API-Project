package com.example.cbc_api.service;

import com.example.cbc_api.mapper.ContentSourceMapper;
import com.example.cbc_api.model.Item;
import com.example.cbc_api.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final Map<String, ContentSourceMapper> mappers;
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    public ItemService(ItemRepository itemRepository, List<ContentSourceMapper> mapperList) {
        this.itemRepository = itemRepository;
        this.mappers = new HashMap<>();
        for (ContentSourceMapper mapper : mapperList) {
            mappers.put(mapper.getSourceName(), mapper);
        }
    }

    public Item getItemById(UUID id) {
        logger.debug("Getting item from database with id: {}", id);
        return itemRepository.findById(id).orElseThrow(() -> {
            logger.warn("Item with id {} not found", id);
            return new NoSuchElementException("Item not found");
        });
    }

    public Item createItem(Map<String, Object> payload, String source) {
        logger.debug("Creating item for source: {}", source);
        ContentSourceMapper mapper = validateAndGetMapper(source);
        Item item = mapper.mapToItem(payload);
        logger.info("Item mapped successfully: {}", item);
        return itemRepository.save(item);
    }

    public Item updateItem(UUID id, Map<String, Object> payload, String source) {
        logger.debug("Updating item with id: {} for source: {}", id, source);
        ContentSourceMapper mapper = validateAndGetMapper(source);
        Item existingItem = getItemById(id);
        Item updatedItem = mapper.updateItem(existingItem, payload);
        logger.info("Item updated successfully: {}", updatedItem);
        return itemRepository.save(updatedItem);
    }

    public void deleteItem(UUID id) {
        logger.debug("Deleting item from database with id: {}", id);
        itemRepository.deleteById(id);
        logger.info("Item with id {} deleted successfully", id);
    }

    public ContentSourceMapper getMapper(String source) {
        return validateAndGetMapper(source);
    }

    private ContentSourceMapper validateAndGetMapper(String source) {
        ContentSourceMapper mapper = mappers.get(source);
        if (mapper == null) {
            logger.error("Unsupported content source: {}", source);
            throw new IllegalArgumentException("Unsupported content source: " + source);
        }
        return mapper;
    }
}