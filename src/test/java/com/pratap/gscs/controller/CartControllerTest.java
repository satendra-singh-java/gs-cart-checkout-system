package com.pratap.gscs.controller;

import com.pratap.gscs.dto.CartRequest;
import com.pratap.gscs.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    @InjectMocks
    private CartController cartController;

    @Mock private CartService cartService;

    @Test
    void testComputeCart(){
        CartRequest req = new CartRequest();
        req.setCartInput("3 Banana");
        cartController.compute(req);
        Mockito.verify(cartService).computeCart(Mockito.anyString());
    }

}