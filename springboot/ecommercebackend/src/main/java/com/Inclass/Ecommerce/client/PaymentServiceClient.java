package com.Inclass.Ecommerce.client;

import com.Inclass.Ecommerce.dto.PaymentWebhookRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentServiceClient {

    private final RestTemplate restTemplate;

    public PaymentServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void triggerWebhook(String orderId) {
        PaymentWebhookRequest req = new PaymentWebhookRequest();
        req.setOrderId(orderId);
        req.setStatus("SUCCESS");
        req.setPaymentId("pay_mock_123");

        restTemplate.postForObject(
                "http://localhost:8080/api/webhooks/payment",
                req,
                String.class
        );
    }
}
