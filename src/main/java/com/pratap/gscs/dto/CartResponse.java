package com.pratap.gscs.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    private List<CartItemDTO> preDiscountItems;
    private Double subtotal;
    private List<DiscountDTO> discounts;
    private Double totalDiscount;
    private Double finalAmount;
}