package com.sampleproject.modules.pilot.mapper;


import com.sampleproject.common.enums.PilotStatus;
import com.sampleproject.modules.pilot.dto.PilotRequest;
import com.sampleproject.modules.pilot.dto.PilotResponse;
import com.sampleproject.modules.pilot.entity.Pilot;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PilotMapper {

    public Pilot toEntity(PilotRequest request){
        if(request == null){
            return null;
        }

        return Pilot.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .status(PilotStatus.AVAILABLE)
                .build();
    }

    public PilotResponse toResponse(Pilot pilot) {
        if (pilot == null) {
            return null;
        }

        PilotResponse response = new PilotResponse();
        response.setId(pilot.getId());
        response.setName(pilot.getName());
        response.setPhone(pilot.getPhone());
        response.setStatus(pilot.getStatus());

        return response;
    }

    public List<PilotResponse> toResponseList(List<Pilot> pilots) {
        if (pilots == null) {
            return Collections.emptyList();
        }

        return pilots.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
