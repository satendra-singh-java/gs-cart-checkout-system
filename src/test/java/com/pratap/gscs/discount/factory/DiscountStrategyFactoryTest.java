package com.pratap.gscs.discount.factory;

import com.pratap.gscs.discount.BundlePriceStrategy;
import com.pratap.gscs.discount.BuyGetFreeStrategy;
import com.pratap.gscs.discount.DiscountStrategy;
import com.pratap.gscs.model.CouponType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiscountStrategyFactoryTest {
    @InjectMocks private DiscountStrategyFactory discountStrategyFactory;

    @Test
    void getStrategy_returns_success_when_BUY_GET_FREE() {
        DiscountStrategy strategy = discountStrategyFactory.getStrategy(CouponType.BUY_GET_FREE);
        assertInstanceOf(BuyGetFreeStrategy.class, strategy);
    }

    @Test
    void getStrategy_returns_success_when_BUNDLE_PRICE() {
        DiscountStrategy strategy = discountStrategyFactory.getStrategy(CouponType.BUNDLE_PRICE);
        assertInstanceOf(BundlePriceStrategy.class, strategy);
    }
}