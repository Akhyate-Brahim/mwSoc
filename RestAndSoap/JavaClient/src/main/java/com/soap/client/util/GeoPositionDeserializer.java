package com.soap.client.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.jxmapviewer.viewer.GeoPosition;

import java.io.IOException;

public class GeoPositionDeserializer extends JsonDeserializer<GeoPosition> {

    @Override
    public GeoPosition deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        double latitude = node.get("lat").asDouble();
        double longitude = node.get("lng").asDouble();

        return new GeoPosition(latitude, longitude);
    }
}
