package com.pratap.gscs.discount.factory;

import com.pratap.gscs.discount.BundlePriceStrategy;
import com.pratap.gscs.discount.BuyGetFreeStrategy;
import com.pratap.gscs.discount.DiscountStrategy;
import com.pratap.gscs.model.CouponType;
import org.springframework.stereotype.Component;

/**
 * Factory to get the appropriate discount strategy based on coupon type.
 */
@Component
public class DiscountStrategyFactory {
    /**
     * Returns the discount strategy for the given coupon couponType.
     * @param couponType CouponType
     * @return DiscountStrategy
     */
    public DiscountStrategy getStrategy(CouponType couponType) {
        return switch (couponType) {
            case CouponType.BUY_GET_FREE -> new BuyGetFreeStrategy();
            case CouponType.BUNDLE_PRICE -> new BundlePriceStrategy();
            default -> throw new IllegalArgumentException("Unknown coupon couponType: " + couponType);
        };
    }
}