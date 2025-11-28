package com.pratap.gscs.service;

import com.pratap.gscs.discount.DiscountStrategy;
import com.pratap.gscs.discount.factory.DiscountStrategyFactory;
import com.pratap.gscs.dto.CartItemDTO;
import com.pratap.gscs.dto.CartResponse;
import com.pratap.gscs.dto.DiscountDTO;
import com.pratap.gscs.model.Coupon;
import com.pratap.gscs.model.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class to handle shopping cart computations.
 */

@Service
@AllArgsConstructor
public class CartService {
    private final ItemService itemService;
    private final CouponService couponService;
    private final DiscountStrategyFactory factory;

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
        Map<String,Integer> cartMap = parseInput(cartInput);

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
            preDiscountItems.add(new CartItemDTO(capitalize(item.getName()), quantity, item.getPrice(), round(line)));
            subtotal += line;
        }

        // compute listOfDiscountsApplied
        List<DiscountDTO> listOfDiscountsApplied = new ArrayList<>();
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
            DiscountStrategy strategy = factory.getStrategy(c.getType());
            double d = strategy.computeDiscount(c, qty, item.getPrice());
            if (d > 0.0) {
                totalDiscount += d;
                listOfDiscountsApplied.add(new DiscountDTO(c.getName(), round(d)));
            }
        }

        //compute final amount
        double finalAmount = round(Math.max(0.0, subtotal - totalDiscount));

        //build response
        CartResponse resp = new CartResponse();
        resp.setPreDiscountItems(preDiscountItems);
        resp.setSubtotal(round(subtotal));
        resp.setDiscounts(listOfDiscountsApplied);
        resp.setTotalDiscount(round(totalDiscount));
        resp.setFinalAmount(finalAmount);
        return resp;
    }

    /**
     * The method takes a string input representing a shopping cart,
     * where each item is represented as "quantity itemName" and items are separated by commas.
     * It parses the input and returns a map with item names as keys (in lowercase)
     * and their total quantities as values.
     *
     * if multiple entries of same item exist, their quantities are summed up.
     *
     * @param input
     * @return
     */
    private Map<String,Integer> parseInput(String input) {
        if (input==null) return Collections.emptyMap();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(s -> s.split("\\s+", 2))
                .filter(a -> a.length==2)
                .map(a -> new AbstractMap.SimpleEntry<>(a[1].toLowerCase().trim(), Integer.parseInt(a[0])))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
    }

    /**
     * The method takes a double value v as input
     * and returns a double value rounded to the nearest hundredth
     * i.e two decimal places.
     *
     * @param v
     * @return
     */
    private static double round(double v){
        return Math.round(v*100.0)/100.0;
    }

    /**
     * The method capitalizes the first letter of the input string s.
     * @param s
     * @return
     */
    private static String capitalize(String s){
        if (s==null || s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}