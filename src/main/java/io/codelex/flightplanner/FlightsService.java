package io.codelex.flightplanner;
import org.springframework.stereotype.Service;

@Service
public class FlightsService {

    FlightsRepository flightsRepository;

    public FlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public void clear() {
        flightsRepository.getFlightList().clear();
    }
}
