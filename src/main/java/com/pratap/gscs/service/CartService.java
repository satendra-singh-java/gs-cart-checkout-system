package com.pratap.gscs.service;

import com.pratap.gscs.discount.DiscountStrategy;
import com.pratap.gscs.discount.factory.DiscountStrategyFactory;
import com.pratap.gscs.dto.CartItemDTO;
import com.pratap.gscs.dto.CartResponse;
import com.pratap.gscs.dto.DiscountDTO;
import com.pratap.gscs.model.Coupon;
import com.pratap.gscs.model.Item;
import com.pratap.gscs.util.GroceryStoreCommonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class to handle shopping cart computations.
 */

@Service
@AllArgsConstructor
public class CartService {
    private final ItemService itemService;
    private final CouponService couponService;
    private final DiscountStrategyFactory discountStrategyFactory;
    private final GroceryStoreCommonUtils groceryStoreCommonUtils;

    /**
     * The method takes a string input representing a shopping cart,
     * where each item is represented as "quantity itemName" and items are separated by commas.
     * It computes the cart details including pre-discount items, subtotal, discounts, total discount, and final amount.
     *
     * @param cartInput
     * @return
     */
    public CartResponse computeCart(String cartInput) {
        // parse input: "3 Banana, 2 orange, 1 Apple, 2 orange"
        Map<String,Integer> cartMap = groceryStoreCommonUtils.parseInput(cartInput);

        // compute pre-discount items and subtotal
        List<CartItemDTO> preDiscountItems = new ArrayList<>();
        double subtotal = 0.0;

        Map<String, Integer> unresolvedItems = new HashMap<>();
        for (Map.Entry<String,Integer> e : cartMap.entrySet()) {
            String key = e.getKey();
            int quantity = e.getValue();
            Optional<Item> optionalItem = itemService.findByName(key);
            if (optionalItem.isEmpty()) {
                unresolvedItems.put(key, quantity);
                continue;
            }
            Item item = optionalItem.get();
            double line = quantity * item.getPrice();
            preDiscountItems.add(new CartItemDTO(groceryStoreCommonUtils.capitalize(item.getName()), quantity, item.getPrice(), groceryStoreCommonUtils.round(line)));
            subtotal += line;
        }

        // compute listOfDiscountsApplied
        List<DiscountDTO> listOfDiscountsApplied = new ArrayList<>();
        double totalDiscount = computeDiscount(preDiscountItems,listOfDiscountsApplied);

        //compute final amount
        double finalAmount = groceryStoreCommonUtils.round(Math.max(0.0, subtotal - totalDiscount));

        //build response
        CartResponse resp = new CartResponse();
        resp.setPreDiscountItems(preDiscountItems);
        resp.setSubtotal(groceryStoreCommonUtils.round(subtotal));
        resp.setDiscounts(listOfDiscountsApplied);
        resp.setTotalDiscount(groceryStoreCommonUtils.round(totalDiscount));
        resp.setFinalAmount(finalAmount);
        return resp;
    }

    /**
     * The method to calculate the discount.
     * @param preDiscountItems
     * @param listOfDiscountsApplied
     * @return
     */
    public double computeDiscount(List<CartItemDTO> preDiscountItems, List<DiscountDTO> listOfDiscountsApplied){
        double totalDiscount = 0.0;
        // for each cart item that has coupons
        for (CartItemDTO cartItemDTO : preDiscountItems) {
            String itemKey = cartItemDTO.getName().toLowerCase();
            int qty = cartItemDTO.getQuantity();
            Optional<Item> optionalItem = itemService.findByName(itemKey);
            if (optionalItem.isEmpty()) continue;
            Item item = optionalItem.get();

            Optional<Coupon> optionalCoupons = couponService.findActiveCoupon(itemKey);
            if (optionalCoupons.isEmpty()) continue;
            Coupon c = optionalCoupons.get();
            DiscountStrategy strategy = discountStrategyFactory.getStrategy(c.getType());
            double d = strategy.computeDiscount(c, qty, item.getPrice());
            if (d > 0.0) {
                totalDiscount += d;
                listOfDiscountsApplied.add(new DiscountDTO(c.getName(), groceryStoreCommonUtils.round(d)));
            }
        }
        return totalDiscount;
    }
}