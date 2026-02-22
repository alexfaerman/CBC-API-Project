package com.example.cbc_api.mapper;

import com.example.cbc_api.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
class ContentSourceBMapper implements ContentSourceMapper {

    @Override
    public String getSourceName() {
        return "B";
    }

    @Override
    public Item mapToItem(Map<String, Object> payload) {
        Item item = new Item();
        item.setExternalId((String) payload.get("itemNumber"));
        item.setTitle((String) payload.get("itemTitle"));
        item.setAuthor((String) payload.get("authorName"));
        item.setPublishedYear(((Number) payload.get("yearPublished")).intValue());
        item.setType((String) payload.get("typeB"));
        log.debug("Mapping done for ContentSourceB: {}", item);
        return item;
    }

    @Override
    public Item updateItem(Item existingItem, Map<String, Object> payload) {
        existingItem.setExternalId((String) payload.get("itemNumber"));
        existingItem.setTitle((String) payload.get("itemTitle"));
        existingItem.setAuthor((String) payload.get("authorName"));
        existingItem.setPublishedYear((int) payload.get("yearPublished"));
        existingItem.setType((String) payload.get("typeB"));
        log.info("Item updated for ContentSourceB: {}", existingItem);
        return existingItem;
    }
}
