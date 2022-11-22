package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private final FlightsService flightsService;

    public AdminController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody FlightRequest flightRequest) {
        return this.flightsService.addFlight(flightRequest);
    }

    @GetMapping("/flights/{id}")
    public Flight fetchFlight(@PathVariable int id) {
        return this.flightsService.fetchFlight(id);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable int id) {
        this.flightsService.deleteFlight(id);
    }

}
