package io.codelex.flightplanner.costumer;

import io.codelex.flightplanner.flights.Airport;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.PageResult;
import io.codelex.flightplanner.flights.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CostumerController {
    private final CostumerService costumerService;

    public CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam String search){
        return costumerService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult searchFlights(@RequestBody SearchFlightsRequest searchFlightsRequest){
        return costumerService.searchFlights(searchFlightsRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(@PathVariable int id){
        return this.costumerService.findFlightById(id);
    }
}
