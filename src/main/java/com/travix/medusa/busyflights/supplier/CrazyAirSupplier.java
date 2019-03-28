package com.travix.medusa.busyflights.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.mapper.BusyFlightResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class CrazyAirSupplier implements FlightSupplier {

    public static final String SUPPLIER = "Tough Jet";

    @Value("${flight.supplier.crazyair.url}")
    private String supplierUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public CrazyAirSupplier(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CompletableFuture<List<BusyFlightsResponse>> findFlight(BusyFlightsRequest busyFlightsRequest) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(supplierUrl)
                .queryParam("origin", busyFlightsRequest.getOrigin())
                .queryParam("destination", busyFlightsRequest.getDestination())
                .queryParam("departureDate", busyFlightsRequest.getReturnDate())
                .queryParam("returnDate", busyFlightsRequest.getDepartureDate())
                .queryParam("passengerCount", busyFlightsRequest.getNumberOfPassengers());

        ResponseEntity<List<CrazyAirResponse>> crazyAirRespone = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CrazyAirResponse>>() {}, new Object());

        List<BusyFlightsResponse> busyFlightsResponses = crazyAirRespone.getBody().parallelStream().
                map(BusyFlightResponseMapper::from).collect(Collectors.toList());
        CompletableFuture<List<BusyFlightsResponse>> completableFuture = new CompletableFuture<>();
        completableFuture.complete(busyFlightsResponses);

        return completableFuture;
    }
}
