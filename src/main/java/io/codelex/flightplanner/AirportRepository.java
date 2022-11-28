package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {

//    @Query("SELECT a FROM Airport a WHERE a.airport = ")
}
