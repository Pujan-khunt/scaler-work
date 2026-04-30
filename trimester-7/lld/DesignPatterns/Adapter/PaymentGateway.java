// This is the domain specific interface that the client uses and expects
public interface PaymentGateway {
    void processPayment(String accountId, double amount);
}
