package com.example.traffic.api;

import com.example.traffic.controller.TrafficLightController;
import com.example.traffic.domain.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/traffic")
public class TrafficLightApi {

    private final TrafficLightController controller;

    public TrafficLightApi(TrafficLightController controller) {
        this.controller = controller;
    }

    @PostMapping("/state")
    public void changeState(@RequestBody Map<Direction, LightColor> lights) {
        controller.changeState(new TrafficLightState(lights));
    }

    @PostMapping("/pause")
    public void pause() { controller.pause(); }

    @PostMapping("/resume")
    public void resume() { controller.resume(); }

    @GetMapping("/state")
    public TrafficLightState current() { return controller.current(); }

    @GetMapping("/history")
    public List<TrafficLightState> history() { return controller.history(); }
}
