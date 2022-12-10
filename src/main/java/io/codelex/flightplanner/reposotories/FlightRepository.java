package io.codelex.flightplanner.reposotories;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    
    List<Flight> findByArrivalTimeAndDepartureTimeAndCarrierAndFromAndTo(LocalDateTime arrivalTime, LocalDateTime departureTime, @NotBlank String carrier, @Valid Airport from, @Valid Airport to);

}
