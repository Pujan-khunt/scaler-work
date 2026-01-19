package com.Inclass.Ecommerce.repository;

import com.Inclass.Ecommerce.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {}
