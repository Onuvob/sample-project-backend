package com.sampleproject.modules.vessel.service;

import com.sampleproject.common.enums.PilotStatus;
import com.sampleproject.common.enums.VehicleStatus;
import com.sampleproject.modules.pilot.dto.PilotResponse;
import com.sampleproject.modules.vessel.dto.VehicleRequest;
import com.sampleproject.modules.vessel.dto.VehicleResponse;
import com.sampleproject.modules.vessel.entity.Vehicle;
import com.sampleproject.modules.vessel.mapper.VehicleMapper;
import com.sampleproject.modules.vessel.repository.VehicleRepository;
import com.sampleproject.user.entity.User;
import com.sampleproject.util.CurrentUserService;
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
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final CurrentUserService currentUserService;
    private final VehicleMapper vehicleMapper;

    public VehicleResponse createVehicle(VehicleRequest request){
        User user = this.currentUserService.getCurrentUser();

        Vehicle vehicle = this.vehicleMapper.toEntity(request, user);
        return this.vehicleMapper.toResponse(this.vehicleRepository.save(vehicle));
    }

    public Vehicle getById(Long id){
        return this.vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }

    public VehicleResponse updateVehicle(Long id, VehicleRequest request){
        Vehicle vehicle = this.vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        if(request.getRegistrationNumber() != null){
            vehicle.setRegistrationNumber(request.getRegistrationNumber());
        }
        if(request.getName() != null){
            vehicle.setName(request.getName());
        }
        if(request.getType() != null){
            vehicle.setType(request.getType());
        }
        if(request.getCapacity() != null){
            vehicle.setCapacity(request.getCapacity());
        }
        return this.vehicleMapper.toResponse(this.vehicleRepository.save(vehicle));
    }

    public void deleteVehicle(Long id){
        this.vehicleRepository.deleteById(id);
    }

    public VehicleResponse getVehicle(Long id){
        Vehicle vehicle = this.vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        return this.vehicleMapper.toResponse(vehicle);
    }

    public void approveVehicle(Long id){
        Vehicle vehicle = this.vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        vehicle.setStatus(VehicleStatus.APPROVED);
        this.vehicleRepository.save(vehicle);
    }

    public void rejectVehicle(Long id){
        Vehicle vehicle = this.vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot not found with id: " + id));
        vehicle.setStatus(VehicleStatus.REJECTED);
        this.vehicleRepository.save(vehicle);
    }

    public Page<VehicleResponse> getPendingVehicles(RequestUtil request){
        Sort sort = Sort.by(request.getSortDir().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSortField());
        Pageable pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize(), sort);

        return this.vehicleRepository.getPaginatedPendingList(VehicleStatus.PENDING, pageable);
    }

    public Page<VehicleResponse> getSelfPaginatedList(RequestUtil request) {

        Sort sort = Sort.by(request.getSortDir().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSortField());
        Pageable pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize(), sort);

        User currentUser = this.currentUserService.getCurrentUser();

        return this.vehicleRepository.getSelfPaginatedList(QueryHelper.formatLikeParamLower(request.getName()),
                currentUser,
                pageable);
    }
}
