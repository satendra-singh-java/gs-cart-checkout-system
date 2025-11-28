package com.pratap.gscs.service;

import com.pratap.gscs.model.Coupon;
import com.pratap.gscs.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing coupons.
 */
@Service
@AllArgsConstructor
public class CouponService {
    private final CouponRepository repo;

    public List<Coupon> getAllCoupons(){
        return repo.findAll();
    }

    public Optional<Coupon> findActiveCoupon(String itemKey) {
        return repo.findActiveCoupon(itemKey);
    }
}