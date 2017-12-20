package com.travix.medusa.busyflights.controllers;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.repositories.CrazyFlightSpecification;
import com.travix.medusa.busyflights.repositories.ToughJetFlightRepository;
import com.travix.medusa.busyflights.repositories.ToughJetFlightSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tough-jet", produces = "application/hal+json")
public class ToughJetController {

    @Autowired
    private ToughJetFlightRepository toughJetFlightRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<ToughJetResponse> getFligths() {
        return toughJetFlightRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<ToughJetResponse> searchFligths(@RequestBody ToughJetRequest toughJetRequest) {

        ToughJetFlightSpecification toughJetSpecification = new ToughJetFlightSpecification(toughJetRequest);
        return toughJetFlightRepository.findAll(toughJetSpecification);

    }
}
