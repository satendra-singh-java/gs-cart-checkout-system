package com.pratap.gscs.repository;

import com.pratap.gscs.config.CouponLoader;
import com.pratap.gscs.model.Coupon;
import com.pratap.gscs.model.CouponType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CouponRepositoryTest {
    @InjectMocks
    CouponRepository couponRepository;
    @Mock
    private CouponLoader couponLoader;

    @BeforeAll
    public static void init(){
        CouponRepository.COUPON_RECORDS.clear();
        CouponRepository.COUPON_RECORDS.add(new Coupon("banana b2g1f","banana", CouponType.BUY_GET_FREE,2,1,null,null,true));
    }

    @Test
    void findAll() {
        List<Coupon> coupons = couponRepository.findAll();
        assertEquals(1,coupons.size());
    }

    @Test
    void findActiveCoupon() {
        Optional<Coupon> result = couponRepository.findActiveCoupon("banana");
        assertTrue(result.isPresent());
        assertEquals("banana", result.get().getItemKey());
    }
}