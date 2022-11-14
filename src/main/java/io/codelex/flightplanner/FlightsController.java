package io.codelex.flightplanner;
import org.springframework.web.bind.annotation.*;

@RestController
public class FlightsController {
private final FlightsService flightsService;
    private FlightsController AdminService;

    public FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @PostMapping("/testing-api/clear")
    public void clear() {
       this.flightsService.clear();
    }

}
