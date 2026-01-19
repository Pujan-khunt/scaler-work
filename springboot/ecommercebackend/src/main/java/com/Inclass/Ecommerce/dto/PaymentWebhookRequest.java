package com.Inclass.Ecommerce.dto;

import lombok.Data;

@Data
public class PaymentWebhookRequest {
    private String orderId;
    private String status; // SUCCESS / FAILED
    private String paymentId;
}
