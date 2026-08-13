package com.sampleproject.modules.coupon.mapper;

import com.sampleproject.modules.coupon.dto.CouponRequest;
import com.sampleproject.modules.coupon.dto.CouponResponse;
import com.sampleproject.modules.coupon.entity.Coupon;
import com.sampleproject.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CouponMapper {

    public CouponResponse toResponse(Coupon coupon) {
        if (coupon == null) {
            return null;
        }

        CouponResponse response = new CouponResponse();

        response.setId(coupon.getId());
        response.setCode(coupon.getCode());
        response.setAmount(coupon.getAmount());
        response.setExpiryDate(coupon.getExpiryDate());
        response.setStatus(coupon.getStatus());

        if (coupon.getOwner() != null) {
            response.setOwnerFirstName(coupon.getOwner().getFirstName());
            response.setOwnerLastName(coupon.getOwner().getLastName());
        }

        return response;
    }

    public List<CouponResponse> toResponseList(List<Coupon> coupons) {
        if (coupons == null) {
            return Collections.emptyList();
        }

        return coupons.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Coupon toEntity(CouponRequest request, User owner) {
        if (request == null) {
            return null;
        }

        return Coupon.builder()
                .code(request.getCode())
                .amount(request.getAmount())
                .expiryDate(request.getExpiryDate())
                .status(request.getStatus())
                .owner(owner)
                .build();
    }

    public void updateEntity(Coupon coupon, CouponRequest request, User owner) {
        if (coupon == null || request == null) {
            return;
        }

        if (request.getCode() != null) {
            coupon.setCode(request.getCode());
        }

        if (request.getAmount() != null) {
            coupon.setAmount(request.getAmount());
        }

        if (request.getExpiryDate() != null) {
            coupon.setExpiryDate(request.getExpiryDate());
        }

        if (request.getStatus() != null) {
            coupon.setStatus(request.getStatus());
        }

        if (owner != null) {
            coupon.setOwner(owner);
        }
    }
}
