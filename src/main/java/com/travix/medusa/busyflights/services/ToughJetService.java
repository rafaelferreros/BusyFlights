package com.travix.medusa.busyflights.services;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToughJetService implements BusyFlightsAdapter<ToughJetResponse, ToughJetRequest> {

    RestTemplate restTemplate;
    private static final String SUPPLIER = "ToughJet";

    public ToughJetService(){
        restTemplate = new RestTemplate();
    }

    public List<BusyFlightsResponse> getAllFlights(){

        List<BusyFlightsResponse> response = new ArrayList<>();

        String getUrl = "http://localhost:8080/api/tough-jet";
        ResponseEntity<ToughJetResponse[]> getResponse = restTemplate.getForEntity(getUrl, ToughJetResponse[].class);

        //TODO: needs response validation
        if(getResponse.getBody() != null){
            List<ToughJetResponse> toughJetResponse = Arrays.asList(getResponse.getBody());
            toughJetResponse.forEach(toughJetResponseConsumer -> response.add(parseBusyFlightsResponse(toughJetResponseConsumer)));
        }

        return response;
    }

    @Override
    public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest request) {

        List<BusyFlightsResponse> response = new ArrayList<>();

        String getUrl = "http://localhost:8080/api/tough-jet";
        ToughJetRequest toughJetRequest = parseBusyFlightsRequest(request);
        ResponseEntity<ToughJetResponse[]> getResponse = restTemplate.postForEntity(getUrl, toughJetRequest, ToughJetResponse[].class);

        //TODO: needs response validation
        if(getResponse.getBody() != null){
            List<ToughJetResponse> resultToughJet = Arrays.asList(getResponse.getBody());
            resultToughJet.forEach(toughJetResponse -> response.add(parseBusyFlightsResponse(toughJetResponse)));
        }

        return response;
    }

    @Override
    public BusyFlightsResponse parseBusyFlightsResponse(ToughJetResponse toughJetResponse) {
        BusyFlightsResponse response = new BusyFlightsResponse();

        response.setAirline(toughJetResponse.getCarrier());
        response.setSupplier(SUPPLIER);

        double base = toughJetResponse.getBasePrice();
        double discount = toughJetResponse.getDiscount();
        double tax = toughJetResponse.getTax();
        double price = round((base * (1 - discount) * (1 + tax)),2);
        response.setFare(price);

        response.setDepartureAirportCode(toughJetResponse.getDepartureAirportName());
        response.setDestinationAirportCode(toughJetResponse.getArrivalAirportName());

        /*LocalDateTime departure = LocalDateTime.parse(toughJetResponse.getOutboundDateTime(),
                DateTimeFormatter.ISO_INSTANT);
        response.setDepartureDate(departure.format(DateTimeFormatter.ISO_DATE_TIME));

        LocalDateTime arrival = LocalDateTime.parse(toughJetResponse.getInboundDateTime(),
                DateTimeFormatter.ISO_INSTANT);
        response.setArrivalDate(arrival.format(DateTimeFormatter.ISO_DATE_TIME));
*/
        //TODO: needs to fix the date to ISO_DATE_TIME format
        response.setDepartureDate(toughJetResponse.getOutboundDateTime());
        response.setArrivalDate(toughJetResponse.getInboundDateTime());
        return response;
    }

    @Override
    public ToughJetRequest parseBusyFlightsRequest(BusyFlightsRequest request) {
        ToughJetRequest toughJetRequest = new ToughJetRequest();

        toughJetRequest.setTo(request.getDestination());
        toughJetRequest.setFrom(request.getOrigin());

        return toughJetRequest;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
