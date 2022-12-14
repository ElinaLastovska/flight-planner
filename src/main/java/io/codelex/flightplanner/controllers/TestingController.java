package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.services.FlightService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {
    private final FlightService flightService;

    public TestingController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/testing-api/clear")
    public void clear() {
        this.flightService.clear();
    }

}
