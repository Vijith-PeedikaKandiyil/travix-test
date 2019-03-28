package com.travix.medusa.busyflights.domain.busyflights;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class BusyFlightsRequest {

    @NotNull(message = "Orgin cannot be empty")
    @NotEmpty(message = "Orgin cannot be empty")
    @Length(min = 3, max = 3, message = "Please enter a valid orgin")
    private String origin;

    @NotNull(message = "destination cannot be empty")
    @NotEmpty(message = "destination cannot be empty")
    @Length(min = 3, max = 3, message = "Please enter a valid destination")
    private String destination;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "departure date cannot be null")
    private LocalDate departureDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnDate;
    @Max(value = 4, message = "Maximum number of passenger is 4")
    @Min(value = 1, message = "Atleast 1 passanger is required")
    private int numberOfPassengers;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(final LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(final int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
