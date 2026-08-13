package com.sampleproject.modules.route.repository;

import com.sampleproject.modules.route.dto.RouteResponse;
import com.sampleproject.modules.route.entity.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("SELECT new com.sampleproject.modules.route.dto.RouteResponse(r.id, r.source, " +
            "r.destination, r.serviceFee, r.active) " +
            "FROM Route r WHERE (:destination IS NULL OR LOWER(r.destination) LIKE :destination) " +
            "AND (:source IS NULL OR LOWER(r.source) LIKE :source)")
    Page<RouteResponse> getPaginatedList(@Param("destination") String destination,
                                         @Param("source") String source,
                                         Pageable pageable);
}
