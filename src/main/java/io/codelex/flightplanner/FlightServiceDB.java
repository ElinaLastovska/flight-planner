package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightRequest;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "flightsapp", name = "appmode", havingValue = "database")
public class FlightServiceDB implements FlightService {
    @Override
    public void clear() {

    }

    @Override
    public Flight createFlight(FlightRequest flightRequest) {
        return null;
    }

    @Override
    public Flight addFlight(FlightRequest flightRequest) {
        return null;
    }

    @Override
    public boolean exists(Flight flight) {
        return false;
    }

    @Override
    public boolean isAirportsSame(Flight flight) {
        return false;
    }

    @Override
    public boolean isDatesCorrect(Flight flight) {
        return false;
    }

    @Override
    public Flight fetchFlight(int id) {
        return null;
    }

    @Override
    public void deleteFlight(int id) {

    }

    @Override
    public List<Airport> searchAirports(String search) {
        return null;
    }

    @Override
    public PageResult searchFlights(SearchFlightsRequest searchFlightsRequest) {
        return null;
    }

    @Override
    public Flight findFlightById(int id) {
        return null;
    }
}
