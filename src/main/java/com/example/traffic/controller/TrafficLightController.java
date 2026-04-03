package com.example.traffic.controller;
import com.example.traffic.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TrafficLightController {
    private final ReentrantLock lock = new ReentrantLock();
    private boolean paused = false;
    private TrafficLightState current;
    private final List<TrafficLightState> history = new ArrayList<>();

    public void changeState(TrafficLightState next) {
        lock.lock();
        try {
            if (paused) return;
            validate(next);
            current = next;
            history.add(next);
        } finally { lock.unlock(); }
    }

    private void validate(TrafficLightState state) {
        boolean ns = isGreen(state, Direction.NORTH, Direction.SOUTH);
        boolean ew = isGreen(state, Direction.EAST, Direction.WEST);
        if (ns && ew) throw new ConflictingGreenException("Conflicting green directions");
    }

    private boolean isGreen(TrafficLightState s, Direction d1, Direction d2) {
        return s.getLights().getOrDefault(d1, LightColor.RED) == LightColor.GREEN ||
               s.getLights().getOrDefault(d2, LightColor.RED) == LightColor.GREEN;
    }

    public void pause() { paused = true; }
    public void resume() { paused = false; }
    public TrafficLightState current() { return current; }
    public List<TrafficLightState> history() { return Collections.unmodifiableList(history); }
}
