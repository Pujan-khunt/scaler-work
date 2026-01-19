package com.Inclass.Ecommerce.repository;

import com.Inclass.Ecommerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {}
