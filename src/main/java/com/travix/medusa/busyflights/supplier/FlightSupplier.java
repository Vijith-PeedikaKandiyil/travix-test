package com.travix.medusa.busyflights.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FlightSupplier {

     @Async
     CompletableFuture<List<BusyFlightsResponse>> findFlight(BusyFlightsRequest busyFlightsRequest);
}

