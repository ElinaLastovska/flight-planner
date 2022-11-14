package io.codelex.flightplanner.admin;
import io.codelex.flightplanner.FlightsRepository;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.FlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service
public class AdminService {
    private final FlightsRepository flightsRepository;

    public AdminService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private LocalDateTime dateTimeConverter(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }
    public Flight createFlight(FlightRequest flightRequest){
        Flight flight = new Flight();
        flight.setId(flightsRepository.getId());
        flight.setFrom(flightRequest.getFrom());
        flight.setTo(flightRequest.getTo());
        flight.setCarrier(flightRequest.getCarrier());
        flight.setDepartureTime(dateTimeConverter(flightRequest.getDepartureTime()));
        flight.setArrivalTime(dateTimeConverter(flightRequest.getArrivalTime()));
        return flight;
    }
    public synchronized Flight addFlight(FlightRequest flightRequest) {
        Flight flight = createFlight(flightRequest);
        if (ifExists(flight)){throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists");
        }else if (isAirportsSame(flight)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There can not be flight with same airport from and airport to");
        }else if (!isDatesCorrect(flight)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are problem with time");
        }
        else { flightsRepository.getFlightList().add(flight);
            return flight;}
    }

    public boolean ifExists (Flight flight){
        return this.flightsRepository.getFlightList().stream().anyMatch(flightsInList ->
                flightsInList.getFrom().equals(flight.getFrom()) &&
                        flightsInList.getTo().equals(flight.getTo())&&
                        flightsInList.getCarrier().equals(flight.getCarrier())&&
                        flightsInList.getArrivalTime().equals(flight.getArrivalTime())&&
                        flightsInList.getDepartureTime().equals(flight.getDepartureTime()));
    }

    public boolean isAirportsSame(Flight flight){

        return flight.getFrom().getAirport().replaceAll("\\s+","").equalsIgnoreCase(flight.getTo().getAirport().replaceAll("\\s+",""))&&
                flight.getFrom().getCity().replaceAll("\\s+","").equalsIgnoreCase(flight.getTo().getCity().replaceAll("\\s+",""))&&
                flight.getFrom().getCountry().replaceAll("\\s+","").equalsIgnoreCase(flight.getTo().getCountry().replaceAll("\\s+",""));

    }

    public boolean isDatesCorrect(Flight flight){
        return flight.getArrivalTime().isAfter(flight.getDepartureTime())&&
                flight.getArrivalTime().isAfter(flight.getDepartureTime().plusMinutes(2))&&
                flight.getArrivalTime().isBefore(flight.getArrivalTime().plusHours(19L));
    }
    public Flight fetchFlight(int id){
        return this.flightsRepository.getFlightList().stream().filter(c -> c.getId() == id).findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public synchronized  void deleteFlight(int id) {
        this.flightsRepository.getFlightList().removeIf(c -> c.getId() == id);
    }
}
