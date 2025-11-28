package com.pratap.gscs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private String name;
    private Integer quantity;
    private Double unitPrice;
    private Double lineTotal;
}