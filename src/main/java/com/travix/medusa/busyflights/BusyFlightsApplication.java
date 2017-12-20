package com.travix.medusa.busyflights;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.repositories.CrazyAirFlightRepository;
import com.travix.medusa.busyflights.repositories.ToughJetFlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@SpringBootApplication
public class BusyFlightsApplication {

    private static String[] AIRPORTS = { "AAA", "BBB", "CCC", "DDD", "EEE",
                                         "FFF", "GGG", "CCS", "PTY", "JFK"};

    private static String[] AIRLINES = { "COPA", "LUFTHANSA", "AMERICAN", "AIRLINES", "FAKEONE"};


    public static void main(String[] args) {
		SpringApplication.run(BusyFlightsApplication.class, args);
	}


    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

	private static CrazyAirResponse getRandomCrazyAirFlight(){
		Random r = new Random();

		CrazyAirResponse crazyAirResponse = new CrazyAirResponse();
		Double value = r.doubles(1,500.0, 2000.0).findFirst().getAsDouble();

		crazyAirResponse.setPrice(round(value,2));

        LocalDateTime date = LocalDateTime.now();
        date = date.plusDays(r.longs(1,1,30).findFirst().getAsLong());
		crazyAirResponse.setDepartureDate(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        date = date.plusDays(r.longs(1,1,30).findFirst().getAsLong());
        crazyAirResponse.setArrivalDate(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        crazyAirResponse.setDepartureAirportCode(AIRPORTS[r.nextInt(10)]);
        crazyAirResponse.setDestinationAirportCode(AIRPORTS[r.nextInt(10)]);
        crazyAirResponse.setCabinclass(r.nextBoolean() ? "E" : "B");
        crazyAirResponse.setAirline(AIRLINES[r.nextInt(5)]);

		return crazyAirResponse;
	}

    private static ToughJetResponse getRandomToughJetFlight(){
        Random r = new Random();

        ToughJetResponse toughJetResponse = new ToughJetResponse();
        Double valuePrice = r.doubles(1,500.0, 2000.0).findFirst().getAsDouble();
        toughJetResponse.setBasePrice(round(valuePrice, 2));

        Double valueTax = r.doubles(1,0, 0.2).findFirst().getAsDouble();
        toughJetResponse.setTax(round(valueTax, 2));

        Double valueDiscount = r.doubles(1,0, 0.3).findFirst().getAsDouble();
        toughJetResponse.setDiscount(round(valueDiscount, 2));

        Instant instant = Instant.now();

        instant = instant.plus(r.longs(1,1,30).findFirst().getAsLong(), ChronoUnit.DAYS);

        toughJetResponse.setOutboundDateTime(DateTimeFormatter.ISO_INSTANT.format(instant));

        instant = instant.plus(r.longs(1,1,30).findFirst().getAsLong(), ChronoUnit.DAYS);

        toughJetResponse.setInboundDateTime(DateTimeFormatter.ISO_INSTANT.format(instant));

        toughJetResponse.setDepartureAirportName(AIRPORTS[r.nextInt(10)]);
        toughJetResponse.setArrivalAirportName(AIRPORTS[r.nextInt(10)]);

        toughJetResponse.setCarrier(AIRLINES[r.nextInt(5)]);

        return toughJetResponse;
    }

	//populating database
	@Bean
	CommandLineRunner init(CrazyAirFlightRepository crazyAirFlightRepository,
                           ToughJetFlightRepository toughJetFlightRepository) {

		return (evt) ->{

		    for(int i = 0; i<100; i++){
                crazyAirFlightRepository.save(getRandomCrazyAirFlight());
                toughJetFlightRepository.save(getRandomToughJetFlight());
            }
		};

	}

}
