package com.pratap.gscs.repository;

import com.pratap.gscs.model.Coupon;
import com.pratap.gscs.config.CouponLoader;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing coupons.
 */
@Repository
@AllArgsConstructor
public class CouponRepository {
    public static final List<Coupon> COUPON_RECORDS = new ArrayList<>();

    private CouponLoader couponLoader;

    /**
     * Initializes the coupon data from the CouponLoader after construction.
     */
    @PostConstruct
    public void initData() {
        List<Coupon> coupons = couponLoader.getLoadedCoupons();
        COUPON_RECORDS.addAll(coupons);
    }

    /**
     * Finds and returns all coupons.
     * @return
     */
    public List<Coupon> findAll() {
        return COUPON_RECORDS;
    }

    /**
     * finds and returns an first active coupon for a given item key.
     * @param itemKey
     * @return
     */
    public Optional<Coupon> findActiveCoupon(String itemKey) {
        return COUPON_RECORDS.stream().filter(c ->
                c.getItemKey().equalsIgnoreCase(itemKey) && c.isEnabled()).findFirst();
    }
}