package com.pratap.gscs.discount;

import com.pratap.gscs.model.Coupon;

/**
 * Strategy interface for different discount calculation strategies
 */
public interface DiscountStrategy {
    /**
     * returns discount amount for the given item quantity and item price using the coupon
     */
    double computeDiscount(Coupon coupon, int quantity, double unitPrice);
}