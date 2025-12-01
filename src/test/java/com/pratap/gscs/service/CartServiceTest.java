package com.pratap.gscs.service;

import com.pratap.gscs.discount.BundlePriceStrategy;
import com.pratap.gscs.discount.DiscountStrategy;
import com.pratap.gscs.discount.factory.DiscountStrategyFactory;
import com.pratap.gscs.dto.CartItemDTO;
import com.pratap.gscs.dto.CartResponse;
import com.pratap.gscs.dto.DiscountDTO;
import com.pratap.gscs.model.Coupon;
import com.pratap.gscs.model.CouponType;
import com.pratap.gscs.model.Item;
import com.pratap.gscs.util.GroceryStoreCommonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private ItemService itemService;
    @Mock
    private CouponService couponService;
    @Mock
    private DiscountStrategyFactory discountStrategyFactory;
    @Mock
    private GroceryStoreCommonUtils groceryStoreCommonUtils;
    @InjectMocks
    private CartService cartService;

    @Test
    void testComputeCart() {
        //GIVEN
        String input = "3 banana";
        Map<String, Integer> parsedMap = new HashMap<>();
        parsedMap.put("banana", 3);

        Mockito.when(groceryStoreCommonUtils.parseInput(Mockito.anyString())).thenReturn(parsedMap);
        Mockito.when(groceryStoreCommonUtils.capitalize(ArgumentMatchers.anyString())).thenAnswer(inv -> inv.getArgument(0));
        Mockito.when(groceryStoreCommonUtils.round(ArgumentMatchers.anyDouble())).thenAnswer(inv -> inv.getArgument(0));
        Item banana = new Item("banana", 10.0);
        Mockito.when(itemService.findByName("banana")).thenReturn(Optional.of(banana));

        //WHEN
        cartService.computeCart(input);

        //THEN
        Mockito.verify(groceryStoreCommonUtils).parseInput(Mockito.anyString());
        Mockito.verify(groceryStoreCommonUtils, Mockito.times(4)).round(Mockito.anyDouble());
        Mockito.verify(itemService, Mockito.times(2)).findByName(Mockito.anyString());
    }


    @Test
    void testComputeDiscount() {
        // GIVEN
        List<CartItemDTO> preDiscountItems = new ArrayList<>();
        preDiscountItems.add(new CartItemDTO("orange", 3, 10.0, 30.0));

        List<DiscountDTO> discountsApplied = new ArrayList<>();

        Item appleItem = new Item("orange", 10.0);
        Mockito.when(itemService.findByName("orange")).thenReturn(Optional.of(appleItem));

        Coupon coupon = new Coupon("Orange coupon", "orange", CouponType.BUNDLE_PRICE,null,null,3,25.0 ,true);
        Mockito.when(couponService.findActiveCoupon(Mockito.any())).thenReturn(Optional.of(coupon));

        DiscountStrategy strategy = Mockito.mock(BundlePriceStrategy.class);
        Mockito.when(discountStrategyFactory.getStrategy(CouponType.BUNDLE_PRICE)).thenReturn(strategy);
        Mockito.when(strategy.computeDiscount(coupon, 3, 10.0)).thenReturn(5.0);
        Mockito.when(groceryStoreCommonUtils.round(5.0)).thenReturn(5.0);

        // WHEN
        double totalDiscount = cartService.computeDiscount(preDiscountItems, discountsApplied);

        // THEN
        assertEquals(5.0, totalDiscount);
        assertEquals(1, discountsApplied.size());
        assertEquals("Orange coupon", discountsApplied.getFirst().getCouponName());
        assertEquals(5.0, discountsApplied.getFirst().getDiscountAmount());
    }
}