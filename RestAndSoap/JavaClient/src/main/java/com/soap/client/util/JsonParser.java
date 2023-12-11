package com.soap.client.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soap.client.model.CustomRouteSegment;

import java.util.List;

public class JsonParser {
    public static List<CustomRouteSegment> parseRouteSegments(String jsonContent) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonContent, new TypeReference<List<CustomRouteSegment>>() {});
    }
}
