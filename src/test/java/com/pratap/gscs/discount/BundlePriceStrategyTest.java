package com.pratap.gscs.discount;

import com.pratap.gscs.model.Coupon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BundlePriceStrategyTest {

    @Test
    void computeDiscount_returns_success() {
        BundlePriceStrategy strategy = new BundlePriceStrategy();
        Coupon coupon = new Coupon();
        coupon.setBundleQty(3);
        coupon.setBundlePrice(50.0);
        int quantity = 7;
        double unitPrice = 20.0;
        double expectedDiscount =20.0;
        double result = strategy.computeDiscount(coupon,quantity,unitPrice);
        assertEquals(expectedDiscount,result);
    }

    @Test
    void computeDiscount_returns_success_when_bundle_quantity_null_in_db() {
        BundlePriceStrategy strategy = new BundlePriceStrategy();
        Coupon coupon = new Coupon();
        coupon.setBundleQty(null);
        coupon.setBundlePrice(50.0);
        int quantity = 7;
        double unitPrice = 20.0;
        double expectedDiscount = 0.0;
        double result = strategy.computeDiscount(coupon,quantity,unitPrice);
        assertEquals(expectedDiscount,result);
    }

    @Test
    void computeDiscount_returns_success_when_bundle_price_null_in_db() {
        BundlePriceStrategy strategy = new BundlePriceStrategy();
        Coupon coupon = new Coupon();
        coupon.setBundleQty(3);
        coupon.setBundlePrice(null);
        int quantity = 7;
        double unitPrice = 20.0;
        double expectedDiscount = 0.0;
        double result = strategy.computeDiscount(coupon,quantity,unitPrice);
        assertEquals(expectedDiscount,result);
    }
}