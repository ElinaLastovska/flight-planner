package io.codelex.flightplanner.dto;

import io.codelex.flightplanner.domain.Airport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SearchFlightsRequest {
    private String from;
    private String to;
    private String departureDate;

    public SearchFlightsRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Airport getFromAirport() {
        Airport airportFrom = new Airport();
        airportFrom.setAirport(this.getFrom());
        return airportFrom;
    }

    public Airport getToAirport() {
        Airport airportTo = new Airport();
        airportTo.setAirport(this.getTo());
        return airportTo;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getDepartureDateAsDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(departureDate, formatter);

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchFlightsRequest that)) return false;
        return from.equals(that.from) && to.equals(that.to) && departureDate.equals(that.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, departureDate);
    }
}
