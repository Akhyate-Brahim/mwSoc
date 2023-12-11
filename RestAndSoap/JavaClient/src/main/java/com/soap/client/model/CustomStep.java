package com.soap.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CustomStep {
    @JsonProperty("Distance")
    private Double distance;

    @JsonProperty("Duration")
    private Double duration;

    @JsonProperty("Instruction")
    private String instruction;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Type")
    private Integer type;

    @JsonProperty("way_points")
    private List<Integer> wayPoints;

    public Double getDistance() {
        return distance;
    }
    public Double getDuration() {
        return duration;
    }
    public String getInstruction() {
        return instruction;
    }

    public String getName() {
        return name;
    }
    public Integer getType() {
        return type;
    }

    public List<Integer> getWayPoints() {
        return wayPoints;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setWayPoints(List<Integer> wayPoints) {
        this.wayPoints = wayPoints;
    }
}
