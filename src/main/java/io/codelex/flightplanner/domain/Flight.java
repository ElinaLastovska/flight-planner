package io.codelex.flightplanner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.dto.SearchFlightsRequest;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "airport_from")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @Valid
    private Airport from;
    @JoinColumn(name = "airport_to")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @Valid
    private Airport to;
    @NotBlank
    private String carrier;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;


    public Flight() {

    }

    public boolean flightMatches(SearchFlightsRequest searchFlightsRequest) {
        return this.from.getAirport().equals(searchFlightsRequest.getFrom()) && this.to.getAirport().equals(searchFlightsRequest.getTo())
                && this.departureTime.toString().equals(searchFlightsRequest.getDepartureDate());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight flight)) return false;
        return id.equals(flight.id) && from.equals(flight.from) && to.equals(flight.to) && carrier.equals(flight.carrier) && departureTime.equals(flight.departureTime) && arrivalTime.equals(flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, carrier, departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "Flight{" + "id=" + id + ", from=" + from + ", to=" + to + ", carrier='" + carrier + '\'' + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + '}';
    }
}
