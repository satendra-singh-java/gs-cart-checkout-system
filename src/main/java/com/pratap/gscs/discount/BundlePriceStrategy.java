package com.pratap.gscs.discount;

import com.pratap.gscs.model.Coupon;

/**
 * Strategy to compute discount for bundle price coupons
 */
public class BundlePriceStrategy implements DiscountStrategy {
    /**
     * Computes discount for bundle price coupon.
     * @param coupon
     * @param quantity
     * @param unitPrice
     * @return
     */
    @Override
    public double computeDiscount(Coupon coupon, int quantity, double unitPrice) {
        if (coupon.getBundleQty() == null || coupon.getBundlePrice() == null) return 0.0;
        // number of bundles present in the order for the item
        int bundles = quantity / coupon.getBundleQty();
        // normal price for the bundles
        double normal = bundles * coupon.getBundleQty() * unitPrice;
        // offer price for the bundles
        double offer = bundles * coupon.getBundlePrice();
        // discount is the difference of normal and offer price
        return Math.max(0.0, normal - offer);
    }
}