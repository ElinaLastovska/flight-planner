package io.codelex.flightplanner.reposotories;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
    List<Airport> findAirportsByAirportContainingIgnoreCaseOrCityContainingIgnoreCaseOrCountryContainsIgnoreCase(String search1, String search2, String search3);
}
