package io.codelex.flightplanner.domain;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Airport {
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public boolean doContains(String search) {
        String phrase = search.toLowerCase().replaceAll("\\s", "");
        return this.country.trim().toLowerCase().contains(phrase) || this.city.trim().toLowerCase().contains(phrase) || this.airport.trim().toLowerCase().contains(phrase);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport airport1)) return false;
        return country.equals(airport1.country) && city.equals(airport1.city) && airport.equals(airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public String toString() {
        return "Airport{" + "country='" + country + '\'' + ", city='" + city + '\'' + ", airport='" + airport + '\'' + '}';
    }
}
