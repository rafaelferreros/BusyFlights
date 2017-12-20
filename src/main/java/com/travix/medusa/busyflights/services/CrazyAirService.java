package com.travix.medusa.busyflights.services;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrazyAirService implements BusyFlightsAdapter<CrazyAirResponse, CrazyAirRequest> {

    private static final String SUPPLIER = "CrazyAir";

    RestTemplate restTemplate;

    public CrazyAirService(){
        restTemplate = new RestTemplate();
    }

    public List<BusyFlightsResponse> getAllFlights(){

        List<BusyFlightsResponse> response = new ArrayList<>();

        String getUrl = "http://localhost:8080/api/crazy-air";
        ResponseEntity<CrazyAirResponse[]> getResponse = restTemplate.getForEntity(getUrl, CrazyAirResponse[].class);

        //TODO: needs response validation
        if(getResponse.getBody() != null){
            List<CrazyAirResponse> resultCrazy = Arrays.asList(getResponse.getBody());
            resultCrazy.forEach(crazyAirResponse -> response.add(parseBusyFlightsResponse(crazyAirResponse)));
        }

        return response;
    }

    @Override
    public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest request) {

        List<BusyFlightsResponse> response = new ArrayList<>();

        String getUrl = "http://localhost:8080/api/crazy-air";
        CrazyAirRequest crazyAirRequest = parseBusyFlightsRequest(request);
        ResponseEntity<CrazyAirResponse[]> getResponse = restTemplate.postForEntity(getUrl, crazyAirRequest, CrazyAirResponse[].class);

        //TODO: needs response validation
        if(getResponse.getBody() != null){
            List<CrazyAirResponse> resultCrazy = Arrays.asList(getResponse.getBody());
            resultCrazy.forEach(crazyAirResponse -> response.add(parseBusyFlightsResponse(crazyAirResponse)));
        }

        return response;
    }


    @Override
    public BusyFlightsResponse parseBusyFlightsResponse(CrazyAirResponse crazyAirResponse) {

        BusyFlightsResponse response = new BusyFlightsResponse();
        response.setAirline(crazyAirResponse.getAirline());
        response.setSupplier(SUPPLIER);
        response.setFare(crazyAirResponse.getPrice());
        response.setDepartureAirportCode(crazyAirResponse.getDepartureAirportCode());
        response.setDestinationAirportCode(crazyAirResponse.getDestinationAirportCode());

        LocalDateTime departure = LocalDateTime.parse(crazyAirResponse.getDepartureDate(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        response.setDepartureDate(departure.format(DateTimeFormatter.ISO_DATE_TIME));

        LocalDateTime arrival = LocalDateTime.parse(crazyAirResponse.getDepartureDate(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        response.setArrivalDate(arrival.format(DateTimeFormatter.ISO_DATE_TIME));

        return response;
    }

    @Override
    public CrazyAirRequest parseBusyFlightsRequest(BusyFlightsRequest request) {
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();

        crazyAirRequest.setDestination(request.getDestination());
        crazyAirRequest.setOrigin(request.getOrigin());

        return crazyAirRequest;
    }
}
