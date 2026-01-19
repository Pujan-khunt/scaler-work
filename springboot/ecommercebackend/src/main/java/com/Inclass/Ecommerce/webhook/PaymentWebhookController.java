package com.Inclass.Ecommerce.webhook;

import com.Inclass.Ecommerce.dto.PaymentWebhookRequest;
import com.Inclass.Ecommerce.repository.PaymentRepository;
import com.Inclass.Ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks/payment")
public class PaymentWebhookController {

    private final OrderService orderService;
    private final PaymentRepository paymentRepo;

    public PaymentWebhookController(OrderService orderService,
                                    PaymentRepository paymentRepo) {
        this.orderService = orderService;
        this.paymentRepo = paymentRepo;
    }

    @PostMapping
    public String handle(@RequestBody PaymentWebhookRequest request) {

        paymentRepo.findAll().stream()
                .filter(p -> p.getOrderId().equals(request.getOrderId()))
                .findFirst()
                .ifPresent(p -> {
                    p.setStatus(request.getStatus());
                    p.setPaymentId(request.getPaymentId());
                    paymentRepo.save(p);
                });

        if ("SUCCESS".equalsIgnoreCase(request.getStatus())) {
            orderService.markPaid(request.getOrderId());
        }

        return "Webhook processed";
    }
}
