package com.pratap.gscs.controller;

import com.pratap.gscs.dto.CartRequest;
import com.pratap.gscs.dto.CartResponse;
import com.pratap.gscs.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle cart computation requests.
 */
@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    /**
     * Endpoint to compute the cart details based on the input.
     * @param req
     * @return
     */
    @PostMapping("/compute")
    public ResponseEntity<CartResponse> compute(@RequestBody CartRequest req){
        CartResponse resp = cartService.computeCart(req.getCartInput());
        return ResponseEntity.ok(resp);
    }
}