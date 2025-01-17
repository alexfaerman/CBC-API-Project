package com.example.cbc_api.mapper;

import com.example.cbc_api.model.Item;

import java.util.Map;

public interface ContentSourceMapper {
    String getSourceName();
    Item mapToItem(Map<String, Object> payload);
    Item updateItem(Item existingItem, Map<String, Object> payload);
}
