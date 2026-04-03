package com.example.traffic.domain;
import java.time.Instant;
import java.util.*;

public class TrafficLightState {
    private final Map<Direction, LightColor> lights;
    private final Instant timestamp;

    public TrafficLightState(Map<Direction, LightColor> lights) {
        this.lights = Collections.unmodifiableMap(new EnumMap<>(lights));
        this.timestamp = Instant.now();
    }

    public Map<Direction, LightColor> getLights() { return lights; }
    public Instant getTimestamp() { return timestamp; }
}
