package com.example.cbc_api.mapper;

import com.example.cbc_api.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ContentSourceAMapper implements ContentSourceMapper {

    @Override
    public String getSourceName() {
        return "A";
    }

    @Override
    public Item mapToItem(Map<String, Object> payload) {
        Item item = new Item();
        item.setExternalId((String) payload.get("itemId"));
        item.setTitle((String) payload.get("title"));
        item.setAuthor((String) payload.get("author"));
        item.setPublishedYear(((Number) payload.get("publishedYear")).intValue());
        item.setType((String) payload.get("typeA"));
        log.debug("Mapping done for ContentSourceA: {}", item);
        return item;
    }

    @Override
    public Item updateItem(Item existingItem, Map<String, Object> payload) {
        existingItem.setExternalId((String) payload.get("itemId"));
        existingItem.setTitle((String) payload.get("title"));
        existingItem.setAuthor((String) payload.get("author"));
        existingItem.setPublishedYear((int) payload.get("publishedYear"));
        existingItem.setType((String) payload.get("typeA"));
        log.info("Item updated for ContentSourceA: {}", existingItem);
        return existingItem;
    }
}
