package com.soap.client.model;

import org.jxmapviewer.viewer.GeoPosition;
import java.util.List;

public class CustomRouteSegment {
    private Double distance;
    private Double duration;
    private List<GeoPosition> routePositions;
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
