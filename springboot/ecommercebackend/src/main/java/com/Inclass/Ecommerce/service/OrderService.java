package com.Inclass.Ecommerce.service;

import com.Inclass.Ecommerce.model.*;
import com.Inclass.Ecommerce.repository.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public OrderService(CartRepository c, ProductRepository p, OrderRepository o) {
        this.cartRepo = c;
        this.productRepo = p;
        this.orderRepo = o;
    }

    public Order createOrder(String userId) {
        List<CartItem> cartItems = cartRepo.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = 0;

        // First calculate total
        for (CartItem c : cartItems) {
            Product p = productRepo.findById(c.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            total += p.getPrice() * c.getQuantity();
        }

        // Then create order items
        List<OrderItem> items = cartItems.stream().map(c -> {
            Product p = productRepo.findById(c.getProductId()).orElseThrow();

            // Reduce stock
            p.setStock(p.getStock() - c.getQuantity());
            productRepo.save(p);

            return new OrderItem(
                    p.getId(),
                    c.getQuantity(),
                    p.getPrice()
            );
        }).collect(Collectors.toList());

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus("CREATED");
        order.setCreatedAt(Instant.now());
        order.setItems(items);

        cartRepo.deleteByUserId(userId);
        return orderRepo.save(order);
    }


    public Order getOrder(String id) {
        return orderRepo.findById(id).orElseThrow();
    }

    public void markPaid(String orderId) {
        Order o = getOrder(orderId);
        o.setStatus("PAID");
        orderRepo.save(o);
    }
}
