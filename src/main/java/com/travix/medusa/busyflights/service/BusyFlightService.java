package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.supplier.FlightSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class BusyFlightService {

    private final List<FlightSupplier> flightSuppliers;

    @Autowired
    public BusyFlightService(List<FlightSupplier> flightSuppliers) {
        this.flightSuppliers = flightSuppliers;
    }

    public List<BusyFlightsResponse> findFlights(BusyFlightsRequest busyFlightsRequest) {

        List<CompletableFuture<List<BusyFlightsResponse>>> futures = flightSuppliers.stream()
                .map(flightSuppliersService -> flightSuppliersService.findFlight(busyFlightsRequest))
                .collect(Collectors.toList());
        List<BusyFlightsResponse> result = new ArrayList<>();
        futures.forEach(completableFuture -> result.addAll(completableFuture.join()));
        result.sort(Comparator.comparingDouble(response -> Double.valueOf(response.getFare())));
        return result;
    }



}
