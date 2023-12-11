package com.soap.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.soap.client.util.GeoPositionDeserializer;
import org.jxmapviewer.viewer.GeoPosition;
import java.util.List;

public class CustomRouteSegment {
    @JsonProperty("distance")
    private Double distance;

    @JsonProperty("duration")
    private Double duration;

    @JsonDeserialize(contentUsing = GeoPositionDeserializer.class)
    private List<GeoPosition> routePositions;

    @JsonProperty("steps")
    private List<CustomStep> steps;
    public CustomRouteSegment() {
    }

    public CustomRouteSegment(Double distance, Double duration, List<GeoPosition> routePositions, List<CustomStep> steps) {
        this.distance = distance;
        this.duration = duration;
        this.routePositions = routePositions;
        this.steps = steps;
    }

    // Getters and Setters
    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public List<GeoPosition> getRoutePositions() {
        return routePositions;
    }

    public void setRoutePositions(List<GeoPosition> routePositions) {
        this.routePositions = routePositions;
    }

    public List<CustomStep> getSteps() {
        return steps;
    }

    public void setSteps(List<CustomStep> steps) {
        this.steps = steps;
    }
    public GeoPosition getLast(){
        return routePositions.get(routePositions.size()-1);
    }
}
