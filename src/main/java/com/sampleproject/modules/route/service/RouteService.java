package com.sampleproject.modules.route.service;

import com.sampleproject.common.enums.VehicleStatus;
import com.sampleproject.modules.route.dto.RouteRequest;
import com.sampleproject.modules.route.dto.RouteResponse;
import com.sampleproject.modules.route.entity.Route;
import com.sampleproject.modules.route.mapper.RouteMapper;
import com.sampleproject.modules.route.repository.RouteRepository;
import com.sampleproject.modules.vessel.entity.Vehicle;
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
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    public RouteResponse createRoute(RouteRequest request){
        Route route = this.routeMapper.toEntity(request);

        return this.routeMapper.toResponse(this.routeRepository.save(route));
    }

    public Route getById(Long id){
        return this.routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with id: " + id));
    }

    public RouteResponse updateRoute(Long id, RouteRequest request){
        Route route = this.routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with id: " + id));

        route = this.routeMapper.updateEntity(route, request);

        return this.routeMapper.toResponse(this.routeRepository.save(route));
    }

    public void deleteRoute(Long id){
        this.routeRepository.deleteById(id);
    }

    public Page<RouteResponse> getPaginatedList(RequestUtil request){
        Sort sort = Sort.by(request.getSortDir().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSortField());
        Pageable pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize(), sort);

        return this.routeRepository.getPaginatedList(QueryHelper.formatLikeParamLower(request.getDestination()),
                QueryHelper.formatLikeParamLower(request.getSource()),
                pageable);
    }

    public RouteResponse getRoute(Long id){
        Route route = this.routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with id: " + id));

        return this.routeMapper.toResponse(route);
    }
}
