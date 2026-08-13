package com.sampleproject.modules.coupon.service;

import com.sampleproject.modules.coupon.dto.CouponRequest;
import com.sampleproject.modules.coupon.dto.CouponResponse;
import com.sampleproject.modules.coupon.entity.Coupon;
import com.sampleproject.modules.coupon.mapper.CouponMapper;
import com.sampleproject.modules.coupon.repository.CouponRepository;
import com.sampleproject.user.entity.User;
import com.sampleproject.user.service.UserService;
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
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final UserService userService;
    private final CurrentUserService currentUserService;

    public CouponResponse createCoupon(CouponRequest request){
        User couponOwner = this.userService.getUserById(request.getOwnerId());
        Coupon coupon = this.couponMapper.toEntity(request, couponOwner);
        return this.couponMapper.toResponse(this.couponRepository.save(coupon));
    }

    public void validateCoupon(String code){

    }

    public void useCoupon(String code){}

    public Page<CouponResponse> getSelfPaginatedList(RequestUtil request){
        Sort sort = Sort.by(request.getSortDir().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSortField());
        Pageable pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize(), sort);

        User currentUser = this.currentUserService.getCurrentUser();

        return this.couponRepository.getSelfPaginatedList(
                QueryHelper.formatLikeParamLower(request.getCode()),
                currentUser,
                pageable);
    }

    public CouponResponse getCoupon(Long id){
        Coupon coupon = this.couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        return this.couponMapper.toResponse(coupon);
    }

    public Page<CouponResponse> getPaginatedList(RequestUtil request) {
        Sort sort = Sort.by(request.getSortDir().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSortField());
        Pageable pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize(), sort);

        return this.couponRepository.getPaginatedList(
                QueryHelper.formatLikeParamLower(request.getCode()),
                pageable);
    }
}
