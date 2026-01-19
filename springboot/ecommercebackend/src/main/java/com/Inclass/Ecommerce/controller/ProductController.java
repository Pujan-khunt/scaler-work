package com.Inclass.Ecommerce.controller;

import com.Inclass.Ecommerce.model.Product;
import com.Inclass.Ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        return service.create(p);
    }

    @GetMapping
    public List<Product> all() {
        return service.getAll();
    }
}
