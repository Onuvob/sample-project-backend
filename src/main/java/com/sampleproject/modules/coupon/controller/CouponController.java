package com.sampleproject.modules.coupon.controller;

import com.sampleproject.modules.coupon.dto.CouponResponse;
import com.sampleproject.modules.coupon.dto.CouponValidationRequest;
import com.sampleproject.modules.coupon.service.CouponService;
import com.sampleproject.util.ApiResponse;
import com.sampleproject.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getCoupons(RequestUtil request){

        Page<CouponResponse> data = this.couponService.getSelfPaginatedList(request);
        Map<String, Object> response = new HashMap<>();

        response.put("objectList", data.getContent());
        response.put("currentPage", data.getNumber());
        response.put("totalPages", data.getTotalPages());
        response.put("totalItems", data.getTotalElements());

        response.put("sortField", request.getSortField());
        response.put("sortDir", request.getSortDir());
        response.put("reverseSortDir", (request.getSortDir().equals("asc") ? "desc" : "asc"));
        return ResponseEntity.ok(ApiResponse.success("Pilots retrieve successfully", response));
    }

//    @PostMapping("/validate")
//    public ResponseEntity<ApiResponse<CouponResponse>> validateCoupon(@RequestBody CouponValidationRequest validationRequest){
//        try{
//            CouponResponse couponResponse = this.couponService.validateCoupon(validationRequest);
//            return ResponseEntity.ok(ApiResponse.success("Coupon validated successfully", couponResponse));
//        }catch (Exception e){
//            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
//        }
//    }
}
