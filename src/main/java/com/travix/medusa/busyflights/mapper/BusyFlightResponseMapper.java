package com.travix.medusa.busyflights.mapper;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.supplier.CrazyAirSupplier;
import com.travix.medusa.busyflights.supplier.ToughJetSupplier;

public class BusyFlightResponseMapper {

    public static BusyFlightsResponse from(CrazyAirResponse crazyAirResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setAirLine(crazyAirResponse.getAirline());
        busyFlightsResponse.setArrivalDate(crazyAirResponse.getArrivalDate());
        busyFlightsResponse.setDepartureAirportCode(crazyAirResponse.getDepartureAirportCode());
        busyFlightsResponse.setDepartureDate(crazyAirResponse.getDepartureDate());
        busyFlightsResponse.setDestinationAirportCode(crazyAirResponse.getDestinationAirportCode());
        busyFlightsResponse.setSupplier(CrazyAirSupplier.SUPPLIER);
        busyFlightsResponse.setFare(String.valueOf(crazyAirResponse.getPrice()));
        return busyFlightsResponse;
    }


    public static BusyFlightsResponse from(ToughJetResponse toughJetResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setAirLine(toughJetResponse.getCarrier());
        busyFlightsResponse.setArrivalDate(toughJetResponse.getInboundDateTime());
        busyFlightsResponse.setDepartureAirportCode(toughJetResponse.getDepartureAirportName());
        busyFlightsResponse.setDepartureDate(toughJetResponse.getOutboundDateTime());
        busyFlightsResponse.setDestinationAirportCode(toughJetResponse.getArrivalAirportName());

        Double basePrice = toughJetResponse.getBasePrice();
        Double discount = toughJetResponse.getDiscount();
        basePrice = basePrice - basePrice * (discount/100);
        Double totalprice = basePrice + toughJetResponse.getTax();

        busyFlightsResponse.setFare(totalprice.toString());
        busyFlightsResponse.setSupplier(ToughJetSupplier.SUPPLIER);
        return busyFlightsResponse;
    }

}
