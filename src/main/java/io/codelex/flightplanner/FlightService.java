package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightRequest;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;

import java.util.List;

public interface FlightService {

    void clear();

    Flight createFlight(FlightRequest flightRequest);

    Flight addFlight(FlightRequest flightRequest);

    boolean exists(Flight flight);

    boolean isAirportsSame(Flight flight);

    boolean isDatesCorrect(Flight flight);

    Flight fetchFlight(int id);

    void deleteFlight(int id);

    List<Airport> searchAirports(String search);

    PageResult searchFlights(SearchFlightsRequest searchFlightsRequest);

    Flight findFlightById(int id);
}
