package com.sampleproject.modules.coupon.dto;

import com.sampleproject.common.enums.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponResponse {
    private Long id;
    private String code;
    private Double amount;
    private LocalDate expiryDate;
    private CouponStatus status;
    private String ownerFirstName;
    private String ownerLastName;
}
