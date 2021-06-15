package org.energygrid.east.simulationnuclearservice.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.energygrid.east.simulationnuclearservice.model.enums.ScenarioType;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScenarioDTO {
    private UUID id;
    private String name;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTimeEvent;

    private int hours;
    private ScenarioType scenarioType;
    private int power;

    public ScenarioDTO(UUID id, String name, LocalDateTime startTime, LocalDateTime startTimeEvent, int hours, ScenarioType scenarioType, int power) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.startTimeEvent = startTimeEvent;
        this.hours = hours;
        this.scenarioType = scenarioType;
        this.power = power;
    }

    public ScenarioDTO() {
    }

    public LocalDateTime getStartTimeEvent() {
        return startTimeEvent;
    }

    public int getPower() {
        return power;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public UUID getId() {
        return id;
    }

    public ScenarioType getScenarioType() {
        return scenarioType;
    }

    public int getHours() {
        return hours;
    }

    public String getName() {
        return name;
    }
}