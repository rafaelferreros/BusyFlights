package com.travix.medusa.busyflights.repositories;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ToughJetFlightSpecification implements Specification<ToughJetResponse> {

    private ToughJetRequest request;


    public ToughJetFlightSpecification(ToughJetRequest request){
        this.request = request;
    }


    @Override
    public Predicate toPredicate(Root<ToughJetResponse> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();

        if(request.getTo() != null)
            predicates.add(builder.equal(root.get("arrivalAirportName"), request.getTo()));

        if(request.getFrom() != null)
            predicates.add(builder.equal(root.get("departureAirportName"), request.getFrom()));

        //TODO: needs to implement predicates for dates.
        //if(request.getInboundDate() != null)

        //if(request.getOutboundDate() != null)


        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
