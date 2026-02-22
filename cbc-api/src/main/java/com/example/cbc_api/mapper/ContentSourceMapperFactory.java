package com.example.cbc_api.mapper;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContentSourceMapperFactory {
    // Why Store Mappers in a HashMap?
    // Instead of looping through the list each time, this approach:
    // Reduces lookup time from O(n) (loop through a list) to O(1) (HashMap lookup).
    // Supports dynamic content sources – new sources can be added without modifying ItemService.
    private final Map<String, ContentSourceMapper> mappers = new HashMap<>();

    public ContentSourceMapperFactory(List<ContentSourceMapper> mapperList) {
        for (ContentSourceMapper mapper : mapperList) {
            mappers.put(mapper.getSourceName(), mapper);
        }
    }

    public ContentSourceMapper getMapper(String source) {
        ContentSourceMapper mapper = mappers.get(source);
        if (mapper == null) {
            throw new IllegalArgumentException("Unsupported content source: " + source);
        }
        return mapper;
    }
}

