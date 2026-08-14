package com.sampleproject.modules.vessel.repository;

import com.sampleproject.common.enums.VehicleStatus;
import com.sampleproject.modules.pilot.dto.PilotResponse;
import com.sampleproject.modules.vessel.dto.VehicleResponse;
import com.sampleproject.modules.vessel.entity.Vehicle;
import com.sampleproject.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT new com.sampleproject.modules.vessel.dto.VehicleResponse(v.id, v.registrationNumber, " +
            "v.name, v.type, v.capacity, v.owner.firstName, v.owner.lastName, v.status, v.createdAt, v.updatedAt) " +
            "FROM Vehicle v where v.status = :vehicleStatus")
    Page<VehicleResponse> getPaginatedPendingList(@Param("vehicleStatus") VehicleStatus vehicleStatus,
                                                  Pageable pageable);

    @Query("SELECT new com.sampleproject.modules.vessel.dto.VehicleResponse(v.id, v.registrationNumber, " +
            "v.name, v.type, v.capacity, v.owner.firstName, v.owner.lastName, v.status, v.createdAt, v.updatedAt) " +
            "FROM Vehicle v where v.owner = :user " +
            "AND (:name IS NULL OR LOWER(v.name) LIKE :name)")
    Page<VehicleResponse> getSelfPaginatedList(@Param("name") String name,
                                             @Param("user") User currentUser,
                                             Pageable pageable);
}
