package com.travix.medusa.busyflights.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
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
public class ToughJetSupplier implements FlightSupplier {

    public static final String SUPPLIER = "Tough Jet";

    @Value("${flight.supplier.toughjet.url}")
    private String supplierUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public ToughJetSupplier(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CompletableFuture<List<BusyFlightsResponse>> findFlight(BusyFlightsRequest busyFlightsRequest) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(supplierUrl)
                .queryParam("from", busyFlightsRequest.getOrigin())
                .queryParam("to", busyFlightsRequest.getDestination())
                .queryParam("inboundDate", busyFlightsRequest.getReturnDate())
                .queryParam("outBoundDate", busyFlightsRequest.getDepartureDate())
                .queryParam("numberOfAdults", busyFlightsRequest.getNumberOfPassengers());
        ResponseEntity<List<ToughJetResponse>> toughJetResponses = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ToughJetResponse>>() {}, new Object());

        List<BusyFlightsResponse> busyFlightsResponses = toughJetResponses.getBody().parallelStream().
                map(BusyFlightResponseMapper::from).collect(Collectors.toList());
        CompletableFuture<List<BusyFlightsResponse>> completableFuture = new CompletableFuture<>();
        completableFuture.complete(busyFlightsResponses);

        return completableFuture;
    }
}
