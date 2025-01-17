package com.example.cbc_api.mapper;

import com.example.cbc_api.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class ContentSourceCMapper implements ContentSourceMapper {

    private static final Logger logger = LoggerFactory.getLogger(ContentSourceCMapper.class);

    @Override
    public String getSourceName() {
        return "C";
    }

    @Override
    public Item mapToItem(Map<String, Object> payload) {
        Item item = new Item();
        item.setExternalId((String) payload.get("item_id"));
        item.setTitle((String) payload.get("name_of_work"));
        item.setAuthor((String) payload.get("published_by"));
        item.setPublishedYear((int) payload.get("year_of_publication"));
        item.setType((String) payload.get("typeC"));
        logger.debug("Mapping done for ContentSourceC: {}", item);
        return item;
    }

    @Override
    public Item updateItem(Item existingItem, Map<String, Object> payload) {
        existingItem.setExternalId((String) payload.get("item_id"));
        existingItem.setTitle((String) payload.get("name_of_work"));
        existingItem.setAuthor((String) payload.get("published_by"));
        existingItem.setPublishedYear((int) payload.get("year_of_publication"));
        existingItem.setType((String) payload.get("typeC"));
        logger.info("Item updated for ContentSourceC: {}", existingItem);
        return existingItem;
    }
}
