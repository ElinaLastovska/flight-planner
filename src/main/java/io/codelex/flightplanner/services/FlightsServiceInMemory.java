package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightRequest;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
import io.codelex.flightplanner.reposotories.FlightsRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "flightsapp", name = "appmode", havingValue = "inmemory")
public class FlightsServiceInMemory implements FlightService {

    private final FlightsRepository flightsRepository;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FlightsServiceInMemory(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    @Override
    public void clear() {
        flightsRepository.getFlightList().clear();
    }

    private LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    @Override
    public Flight createFlight(FlightRequest flightRequest) {
        Flight flight = new Flight();
        flight.setId(flightsRepository.getId());
        flight.setFrom(flightRequest.getFrom());
        flight.setTo(flightRequest.getTo());
        flight.setCarrier(flightRequest.getCarrier());
        flight.setDepartureTime(parseDateTime(flightRequest.getDepartureTime()));
        flight.setArrivalTime(parseDateTime(flightRequest.getArrivalTime()));
        return flight;
    }

    @Override
    public synchronized Flight addFlight(FlightRequest flightRequest) {
        Flight flight = createFlight(flightRequest);
        if (exists(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists");
        } else if (isAirportsSame(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There can not be flight with same airport from and airport to");
        } else if (!isDatesCorrect(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are problem with time");
        } else {
            flightsRepository.getFlightList().add(flight);
            return flight;
        }
    }

    private boolean exists(Flight flight) {
        return this.flightsRepository.getFlightList().stream().anyMatch(flightsInList -> flightsInList.getFrom().equals(flight.getFrom())
                && flightsInList.getTo().equals(flight.getTo())
                && flightsInList.getCarrier().equals(flight.getCarrier())
                && flightsInList.getArrivalTime().equals(flight.getArrivalTime())
                && flightsInList.getDepartureTime().equals(flight.getDepartureTime()));
    }

    @Override
    public boolean isAirportsSame(Flight flight) {

        return flight.getFrom().getAirport()
                .equalsIgnoreCase(flight.getTo().getAirport().replaceAll("\\s+", ""))
                && flight.getFrom().getCity().replaceAll("\\s+", "")
                .equalsIgnoreCase(flight.getTo().getCity().replaceAll("\\s+", ""))
                && flight.getFrom().getCountry().replaceAll("\\s+", "")
                .equalsIgnoreCase(flight.getTo().getCountry().replaceAll("\\s+", ""));

    }

    @Override
    public boolean isDatesCorrect(Flight flight) {
        return flight.getArrivalTime().isAfter(flight.getDepartureTime())
                && flight.getArrivalTime().isAfter(flight.getDepartureTime().plusMinutes(2))
                && flight.getArrivalTime().isBefore(flight.getArrivalTime().plusHours(19L));
    }

    @Override
    public Flight fetchFlight(int id) {
        return this.flightsRepository.getFlightList().stream().filter(c -> c.getId() == id).findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public synchronized void deleteFlight(int id) {
        this.flightsRepository.getFlightList().removeIf(c -> c.getId() == id);
    }

    @Override
    public List<Airport> searchAirports(String search) {
        return this.flightsRepository.getFlightList().stream().map(Flight::getFrom).filter(airport -> airport.doContains(search)).toList();
    }

    private List<Flight> retrieveFlights(SearchFlightsRequest searchFlightsRequest) {
        return this.flightsRepository.getFlightList().stream().filter(c -> c.flightMatches(searchFlightsRequest)).toList();
    }

    @Override
    public PageResult searchFlights(SearchFlightsRequest searchFlightsRequest) {
        PageResult pageResult = new PageResult();
        pageResult.setItems(retrieveFlights(searchFlightsRequest));
        pageResult.setPage(0);
        pageResult.setTotalItems(pageResult.getItems().size());

        if (searchFlightsRequest.getTo() == null || searchFlightsRequest.getFrom() == null
                && searchFlightsRequest.getDepartureDate() == null || searchFlightsRequest.getFrom().equals(searchFlightsRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Airports from and to can not be the same");
        }
        return pageResult;
    }

    @Override
    public Flight findFlightById(int id) {
        return this.flightsRepository.getFlightList().stream().filter(c -> c.getId() == id).findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


}
