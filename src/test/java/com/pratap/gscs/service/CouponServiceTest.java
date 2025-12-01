package com.pratap.gscs.service;

import com.pratap.gscs.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {
    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository repo;

    @Test
    void getAllCoupons() {
        couponService.getAllCoupons();
        Mockito.verify(repo).findAll();
    }

    @Test
    void findActiveCoupon() {
        couponService.findActiveCoupon("banana");
        Mockito.verify(repo).findActiveCoupon(Mockito.anyString());
    }
}