package com.sampleproject.modules.pilot.repository;

import com.sampleproject.modules.pilot.dto.PilotResponse;
import com.sampleproject.modules.pilot.entity.Pilot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PilotRepository extends JpaRepository<Pilot, Long> {

    @Query("SELECT com.sampleproject.modules.pilot.dto.PilotResponse(p.id, p.name, " +
            "p.phone, p.status) " +
            "FROM Pilot p WHERE (:name IS NULL OR LOWER(p.name) LIKE :name) " +
            "AND (:phone IS NULL OR LOWER(p.phone) LIKE :phone)")
    Page<PilotResponse> getPaginatedList(@Param("name") String name,
                                         @Param("phone") String phone,
                                         Pageable pageable);
}
