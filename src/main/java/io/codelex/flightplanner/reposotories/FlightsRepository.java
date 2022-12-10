package io.codelex.flightplanner.reposotories;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class FlightsRepository {
    private final AtomicInteger id = new AtomicInteger(0);

    private final List<Flight> flightList = new ArrayList<>();

    public List<Flight> getFlightList() {
        return flightList;
    }

    public int getId() {
        return id.getAndIncrement();
    }


}
