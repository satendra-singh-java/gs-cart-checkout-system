package com.pratap.gscs.discount;

import com.pratap.gscs.model.Coupon;

/**
 * Buy X Get Y Free discount strategy implementation
 */
public class BuyGetFreeStrategy implements DiscountStrategy {

    /**
     * Computes discount for buy X get Y free coupon.
     * @param coupon
     * @param quantity
     * @param unitPrice
     * @return
     */
    @Override
    public double computeDiscount(Coupon coupon, int quantity, double unitPrice) {
        if (coupon.getBuyQty() == null || coupon.getFreeQty() == null) return 0.0;
        // for buy 2 get 1 free : block size = 3
        int blockSize = coupon.getBuyQty() + coupon.getFreeQty();

        // number of blocks in the order for the item
        int blocksCount = quantity / blockSize;

        // total free items in the cart for the item
        int freeItems = blocksCount * coupon.getFreeQty();

        // free its price as discount
        return freeItems * unitPrice;
    }
}