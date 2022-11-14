package io.codelex.flightplanner.costumer;

import io.codelex.flightplanner.FlightsRepository;
import io.codelex.flightplanner.flights.Airport;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.PageResult;
import io.codelex.flightplanner.flights.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CostumerService {
    private final FlightsRepository flightsRepository;

    public CostumerService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public List<Airport> searchAirports(String search){
        return this.flightsRepository.getFlightList().stream().map(Flight::getFrom)
                .filter(airport -> airport.doContains(search)).toList();
    }

    public List<Flight> listForSearchFlight(SearchFlightsRequest searchFlightsRequest){
        return this.flightsRepository.getFlightList().stream().filter(c -> c.doExistSearchFlightRequest(searchFlightsRequest)).toList();
    }
    public PageResult searchFlights(SearchFlightsRequest searchFlightsRequest){
        PageResult pageResult = new PageResult();
        pageResult.setItems(listForSearchFlight(searchFlightsRequest));
        pageResult.setPage(0);
        pageResult.setTotalItems(pageResult.getItems().size());

        if (searchFlightsRequest.getTo() == null || searchFlightsRequest.getFrom() == null &&
                searchFlightsRequest.getDepartureDate() == null || searchFlightsRequest.getFrom().equals(searchFlightsRequest.getTo())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Airports from and to can not be the same");
        }
        return pageResult;
    }


    public Flight findFlightById(int id){
        return this.flightsRepository.getFlightList().stream().filter(c -> c.getId() == id)
                .findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
