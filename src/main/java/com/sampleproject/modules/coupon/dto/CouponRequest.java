package com.sampleproject.modules.coupon.dto;

import com.sampleproject.common.enums.CouponStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CouponRequest {
    @NotBlank(message = "Code must not be blank")
    private String code;
    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;
    @NotNull(message = "Expiry date must not be null")
    @FutureOrPresent(message = "Expiry date must be today or in the future")
    private LocalDate expiryDate;
    @NotNull(message = "Status must not be null")
    private CouponStatus status;
    @NotNull(message = "Owner id must not be null")
    private Long ownerId;
}
