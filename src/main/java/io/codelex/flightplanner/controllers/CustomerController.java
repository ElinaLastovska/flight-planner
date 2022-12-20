package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
import io.codelex.flightplanner.services.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final FlightService flightService;

    public CustomerController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam String search) {
        return flightService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult searchFlights(@RequestBody SearchFlightsRequest searchFlightsRequest) {
        return flightService.searchFlights(searchFlightsRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(@PathVariable int id) {
        return this.flightService.findFlightById(id);
    }
}
