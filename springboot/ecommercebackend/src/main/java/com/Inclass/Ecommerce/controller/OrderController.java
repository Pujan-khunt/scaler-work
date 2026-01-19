package com.Inclass.Ecommerce.controller;

import com.Inclass.Ecommerce.dto.CreateOrderRequest;
import com.Inclass.Ecommerce.model.Order;
import com.Inclass.Ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        return service.createOrder(request.getUserId());
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        return service.getOrder(orderId);
    }
}
