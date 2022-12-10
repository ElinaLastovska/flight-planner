package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.FlightRequest;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
import io.codelex.flightplanner.reposotories.AirportRepository;
import io.codelex.flightplanner.reposotories.FlightRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "flightsapp", name = "appmode", havingValue = "database")
public class FlightServiceDB implements FlightService {

    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FlightServiceDB(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    private LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    @Override
    public void clear() {
        flightRepository.deleteAll();
        airportRepository.deleteAll();
    }

    @Override
    public Flight fetchFlight(int id) {
        Optional<Flight> maybeFlight = flightRepository.findById(id);
        return maybeFlight.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    private void saveIfDoesNotExist(Airport doExistAirport) {

        Optional<Airport> maybeAirport = airportRepository.findById(doExistAirport.getAirport());
        if (maybeAirport.isEmpty()) {
            airportRepository.save(doExistAirport);
        }

    }

    @Override
    public Flight createFlight(FlightRequest flightRequest) {
        Flight flight = new Flight();

        saveIfDoesNotExist(flightRequest.getFrom());
        flight.setFrom(airportRepository.getReferenceById(flightRequest.getFrom().getAirport()));
        saveIfDoesNotExist(flightRequest.getTo());
        flight.setTo(airportRepository.getReferenceById(flightRequest.getTo().getAirport()));

        flight.setCarrier(flightRequest.getCarrier());
        flight.setArrivalTime(parseDateTime(flightRequest.getArrivalTime()));
        flight.setDepartureTime(parseDateTime(flightRequest.getDepartureTime()));
        return flight;
    }

    @Override
    public Flight addFlight(FlightRequest flightRequest) {
        Flight flight = createFlight(flightRequest);
        List<Flight> maybeExist = flightRepository.findByArrivalTimeAndDepartureTimeAndCarrierAndFromAndTo(flight.getArrivalTime(),
                flight.getDepartureTime(), flight.getCarrier(), flight.getFrom(), flight.getTo());
        if (!maybeExist.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists");
        } else if (isAirportsSame(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There can not be flight with same airport from and airport to");
        } else if (!isDatesCorrect(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are problem with time");
        } else
            return flightRepository.save(flight);
    }


    @Override
    public boolean isAirportsSame(Flight flight) {
        return flight.getFrom().getAirport().trim()
                .equalsIgnoreCase(flight.getTo().getAirport().trim())
                && flight.getFrom().getCity().trim()
                .equalsIgnoreCase(flight.getTo().getCity().trim())
                && flight.getFrom().getCountry().trim()
                .equalsIgnoreCase(flight.getTo().getCountry().trim());
    }

    @Override
    public boolean isDatesCorrect(Flight flight) {
        return flight.getArrivalTime().isAfter(flight.getDepartureTime())
                && flight.getArrivalTime().isAfter(flight.getDepartureTime().plusMinutes(2))
                && flight.getArrivalTime().isBefore(flight.getArrivalTime().plusHours(19L));
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
