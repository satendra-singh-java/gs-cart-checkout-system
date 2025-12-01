package com.pratap.gscs.discount;

import com.pratap.gscs.model.Coupon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BuyGetFreeStrategyTest {
    @InjectMocks
    private BuyGetFreeStrategy strategy;

    @Test
    void computeDiscount_returns_success() {
        Coupon coupon = new Coupon();
        coupon.setBuyQty(2);
        coupon.setFreeQty(1);
        int quantity = 3;
        double unitPrice = 10.0;
        double expectedDiscount = 10.0;

        double result = strategy.computeDiscount(coupon,quantity,unitPrice);

        assertEquals(expectedDiscount,result);
    }

    @Test
    void computeDiscount_returns_success_when_BuyQty_null_in_db() {
        Coupon coupon = new Coupon();
        coupon.setBuyQty(null);
        coupon.setFreeQty(1);
        int quantity = 3;
        double unitPrice = 10.0;
        double expectedDiscount = 0.0;

        double result = strategy.computeDiscount(coupon,quantity,unitPrice);

        assertEquals(expectedDiscount,result);
    }

    @Test
    void computeDiscount_returns_success_when_freeQty_null_in_db() {
        Coupon coupon = new Coupon();
        coupon.setBuyQty(2);
        coupon.setFreeQty(null);
        int quantity = 3;
        double unitPrice = 10.0;
        double expectedDiscount = 0.0;

        double result = strategy.computeDiscount(coupon,quantity,unitPrice);

        assertEquals(expectedDiscount,result);
    }
}