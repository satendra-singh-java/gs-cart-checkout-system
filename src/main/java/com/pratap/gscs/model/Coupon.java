package com.pratap.gscs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coupon {
    //coupone name
    private String name;
    //should be matching to item name
    private String itemKey;
    //type of coupon
    private CouponType type;
    //buy qty for buyXgetY
    private Integer buyQty;
    //free qty for buyXgetY
    private Integer freeQty;
    //bundle qty for bundle price
    private Integer bundleQty;
    //bundle price for bundle price
    private Double bundlePrice;
    //whether the coupon is enabled
    private boolean enabled = true;
}