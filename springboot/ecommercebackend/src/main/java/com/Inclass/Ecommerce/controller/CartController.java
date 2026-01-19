package com.Inclass.Ecommerce.controller;

import com.Inclass.Ecommerce.dto.AddToCartRequest;
import com.Inclass.Ecommerce.model.CartItem;
import com.Inclass.Ecommerce.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public CartItem addToCart(@RequestBody AddToCartRequest request) {

        CartItem item = new CartItem();
        item.setUserId(request.getUserId());
        item.setProductId(request.getProductId());
        item.setQuantity(request.getQuantity());

        return service.add(item);
    }

    @GetMapping("/{userId}")
    public List<CartItem> getUserCart(@PathVariable String userId) {
        return service.getUserCart(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public String clearCart(@PathVariable String userId) {
        service.clear(userId);
        return "Cart cleared successfully";
    }
}
