package com.sampleproject.modules.pilot.service;

import com.sampleproject.common.enums.PilotStatus;
import com.sampleproject.modules.coupon.entity.Coupon;
import com.sampleproject.modules.pilot.dto.PilotRequest;
import com.sampleproject.modules.pilot.dto.PilotResponse;
import com.sampleproject.modules.pilot.entity.Pilot;
import com.sampleproject.modules.pilot.mapper.PilotMapper;
import com.sampleproject.modules.pilot.repository.PilotRepository;
import com.sampleproject.util.QueryHelper;
import com.sampleproject.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PilotService {

    private final PilotRepository pilotRepository;
    private final PilotMapper pilotMapper;

    public Page<PilotResponse> getPaginatedList(RequestUtil request) {
        Sort sort = Sort.by(request.getSortDir().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSortField());
        Pageable pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize(), sort);

        return this.pilotRepository.getPaginatedList(
                QueryHelper.formatLikeParamLower(request.getName()),
                QueryHelper.formatLikeParam(request.getPhone()),
                pageable);
    }

    public PilotResponse createPilot(PilotRequest request){
        Pilot pilot = this.pilotMapper.toEntity(request);
        return this.pilotMapper.toResponse(this.pilotRepository.save(pilot));
    }

    public Pilot getById(Long id){
        return this.pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot not found with id: " + id));
    }

    public PilotResponse updatePilot(Long id, PilotRequest request) {

        Pilot pilot = this.pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot not found with id: " + id));

        if (request.getName() != null && !request.getName().isBlank()) {
            pilot.setName(request.getName());
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            pilot.setPhone(request.getPhone());
        }

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            try {
                pilot.setStatus(PilotStatus.valueOf(request.getStatus().trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Invalid pilot status: " + request.getStatus() +
                                ". Allowed values are: AVAILABLE, ASSIGNED, INACTIVE"
                );
            }
        }

        return this.pilotMapper.toResponse(this.pilotRepository.save(pilot));
    }

    public void deletePilot(Long id){
        this.pilotRepository.deleteById(id);
    }

    public void getAvailablePilots(){}

    public void assignPilot(){}

    public void releasePilot(){}
}
