package com.travix.medusa.busyflights.repositories;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CrazyFlightSpecification implements Specification<CrazyAirResponse> {

    private CrazyAirRequest request;


    public CrazyFlightSpecification(CrazyAirRequest request){
        this.request = request;
    }

    @Override
    public Predicate toPredicate(Root<CrazyAirResponse> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();

        if(request.getDestination() != null)
            predicates.add(builder.equal(root.get("destinationAirportCode"), request.getDestination()));

        if(request.getOrigin() != null)
            predicates.add(builder.equal(root.get("departureAirportCode"), request.getOrigin()));

        //TODO: needs to implement predicates for dates.
        //if(request.getDepartureDate() != null)

        //if(request.getReturnDate() != null)


        return builder.and(predicates.toArray(new Predicate[predicates.size()]));


    }
}
