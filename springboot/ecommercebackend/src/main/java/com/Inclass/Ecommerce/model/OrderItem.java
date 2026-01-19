package com.Inclass.Ecommerce.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private String productId;
    private Integer quantity;
    private Double price;
}
