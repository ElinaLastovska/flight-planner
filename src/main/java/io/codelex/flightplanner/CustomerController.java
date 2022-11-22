package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final FlightsService flightsService;

    public CustomerController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam String search) {
        return flightsService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult searchFlights(@RequestBody SearchFlightsRequest searchFlightsRequest) {
        return flightsService.searchFlights(searchFlightsRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(@PathVariable int id) {
        return this.flightsService.findFlightById(id);
    }
}
