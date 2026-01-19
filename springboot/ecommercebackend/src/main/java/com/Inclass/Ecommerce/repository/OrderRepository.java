package com.Inclass.Ecommerce.repository;

import com.Inclass.Ecommerce.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {}
