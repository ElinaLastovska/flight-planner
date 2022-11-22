package io.codelex.flightplanner;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {
    private final FlightsService flightsService;

    public TestingController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @PostMapping("/testing-api/clear")
    public void clear() {
        this.flightsService.clear();
    }

}
