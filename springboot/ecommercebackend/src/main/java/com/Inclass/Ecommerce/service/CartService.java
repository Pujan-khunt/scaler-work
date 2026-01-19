package com.Inclass.Ecommerce.service;

import com.Inclass.Ecommerce.model.CartItem;
import com.Inclass.Ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository repo;

    public CartService(CartRepository repo) {
        this.repo = repo;
    }

    public CartItem add(CartItem item) {
        return repo.save(item);
    }

    public List<CartItem> getUserCart(String userId) {
        return repo.findByUserId(userId);
    }

    public void clear(String userId) {
        repo.deleteByUserId(userId);
    }
}
