package com.travix.medusa.busyflights.controllers;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.services.BusyFlightsAdapter;
import com.travix.medusa.busyflights.services.CrazyAirService;
import com.travix.medusa.busyflights.services.ToughJetService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = "/api/busy-flights", produces = "application/hal+json")

public class BusyFlightsController {


    @RequestMapping(method = RequestMethod.GET)
    public List<BusyFlightsResponse> getAllFligths() {

        List<BusyFlightsResponse> response = new ArrayList<>();

        CrazyAirService crazyAirService = new CrazyAirService();
        response.addAll(crazyAirService.getAllFlights());

        ToughJetService toughJetService = new ToughJetService();
        response.addAll(toughJetService.getAllFlights());

        return response;
    }


    @RequestMapping(method = RequestMethod.POST)
    public List<BusyFlightsResponse> searchFligths(@RequestBody BusyFlightsRequest busyFlightsRequest) {

        List<BusyFlightsResponse> response = new ArrayList<>();

        //TODO add dependency injection.
        List<BusyFlightsAdapter> services = new ArrayList<>();
        services.add(new CrazyAirService());
        services.add(new ToughJetService());

        if(busyFlightsRequest != null)
        for(BusyFlightsAdapter service : services)
        {
            List result = service.searchFlights(busyFlightsRequest);
            if(result != null)
                response.addAll(result);
        }

        response.sort(new Comparator<BusyFlightsResponse>() {
            @Override
            public int compare(BusyFlightsResponse p1, BusyFlightsResponse p2) {
                if (p1.getFare() < p2.getFare()) return -1;
                if (p1.getFare() > p2.getFare()) return 1;
                return 0;
            }
        });

        return response;
    }

}
