package com.travix.medusa.busyflights.repositories;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ToughJetFlightRepository extends
        JpaRepository<ToughJetResponse, Integer>,
        JpaSpecificationExecutor<ToughJetResponse> {
}
