package com.pratap.gscs.controller;

import com.pratap.gscs.dto.CartResponse;
import com.pratap.gscs.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to handle cart computation requests.
 */
@Controller
@RequestMapping("/api/cart")
@AllArgsConstructor
public class SimpleCartController {
    private final CartService cartService;

    /**
     * compute cart and return view.
     * @param cart
     * @param model
     * @return
     */
    @PostMapping
    public String compute(@RequestParam String cart, Model model){
        CartResponse resp = cartService.computeCart(cart);
        model.addAttribute("preDiscountItems",resp.getPreDiscountItems());
        model.addAttribute("subTotal", resp.getSubtotal());
        model.addAttribute("discountCoupons",resp.getDiscounts());
        model.addAttribute("totalDiscount",resp.getTotalDiscount());
        model.addAttribute("finalAmount",resp.getFinalAmount());
        return "shopping-bill";
    }

    /**
     * Return bill form view.
     * @return
     */
    @GetMapping
    public String billingForm(){
        return "bill-form";
    }
}