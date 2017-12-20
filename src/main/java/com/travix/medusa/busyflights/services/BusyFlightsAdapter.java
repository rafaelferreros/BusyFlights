package com.travix.medusa.busyflights.services;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;

import java.util.List;

public interface BusyFlightsAdapter<TResponse, TRequest> {

    BusyFlightsResponse parseBusyFlightsResponse(TResponse response);

    TRequest parseBusyFlightsRequest(BusyFlightsRequest request);

    List<BusyFlightsResponse> getAllFlights();

    List<BusyFlightsResponse> searchFlights(BusyFlightsRequest request);



}
