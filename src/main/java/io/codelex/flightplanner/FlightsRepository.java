package io.codelex.flightplanner;
import io.codelex.flightplanner.flights.Flight;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class FlightsRepository {
    private final List<Flight> flightList = new ArrayList<>();

    public List<Flight> getFlightList() {
        return flightList;
    }

    private final AtomicInteger id = new AtomicInteger(0);

    public int getId() {
        return id.getAndIncrement();
    }



}
