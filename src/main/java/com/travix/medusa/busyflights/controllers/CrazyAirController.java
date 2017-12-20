package com.travix.medusa.busyflights.controllers;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.repositories.CrazyAirFlightRepository;
import com.travix.medusa.busyflights.repositories.CrazyFlightSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/crazy-air", produces = "application/hal+json")
public class CrazyAirController {

    @Autowired
    private CrazyAirFlightRepository crazyAirFlightRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<CrazyAirResponse> getFligths() {

        return crazyAirFlightRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<CrazyAirResponse> searchFligths(@RequestBody CrazyAirRequest crazyAirRequest) {

        CrazyFlightSpecification surveySpecification = new CrazyFlightSpecification(crazyAirRequest);
        return crazyAirFlightRepository.findAll(surveySpecification);

    }


}