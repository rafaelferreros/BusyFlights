package com.travix.medusa.busyflights.repositories;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrazyAirFlightRepository extends
        JpaRepository<CrazyAirResponse, Integer> ,
        JpaSpecificationExecutor<CrazyAirResponse> {
}
