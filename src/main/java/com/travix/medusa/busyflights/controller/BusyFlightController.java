package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.BusyFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight")
public class BusyFlightController {

    private final BusyFlightService busyFlightService;

    @Autowired
    public BusyFlightController(BusyFlightService busyFlightService) {
        this.busyFlightService = busyFlightService;
    }

    @GetMapping
    public List<BusyFlightsResponse> getFlights(@Valid BusyFlightsRequest busyFlightsRequest) {
        return busyFlightService.findFlights(busyFlightsRequest);
    }

}
