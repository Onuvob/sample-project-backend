package com.sampleproject.modules.vessel.mapper;


import com.sampleproject.common.enums.VehicleStatus;
import com.sampleproject.modules.vessel.dto.VehicleRequest;
import com.sampleproject.modules.vessel.dto.VehicleResponse;
import com.sampleproject.modules.vessel.entity.Vehicle;
import com.sampleproject.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleMapper {

    public Vehicle toEntity(VehicleRequest request, User owner) {
        if (request == null) {
            return null;
        }

        return Vehicle.builder()
                .registrationNumber(request.getRegistrationNumber())
                .name(request.getName())
                .type(request.getType())
                .capacity(request.getCapacity())
                .status(VehicleStatus.PENDING)
                .owner(owner)
                .build();
    }

    public VehicleResponse toResponse(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        VehicleResponse response = new VehicleResponse();

        response.setId(vehicle.getId());
        response.setRegistrationNumber(vehicle.getRegistrationNumber());
        response.setName(vehicle.getName());
        response.setType(vehicle.getType());
        response.setCapacity(vehicle.getCapacity());

        if (vehicle.getOwner() != null) {
            response.setOwnerFirstName(vehicle.getOwner().getFirstName());
            response.setOwnerLastName(vehicle.getOwner().getLastName());
        }

        return response;
    }

    public List<VehicleResponse> toResponseList(List<Vehicle> vehicles) {
        if (vehicles == null) {
            return Collections.emptyList();
        }

        return vehicles.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
