public class CheckoutSystem {
    private final PaymentGateway gateway;

    public CheckoutSystem(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void checkout(String userAccount, double totalAmount) {
        gateway.processPayment(userAccount, totalAmount);
    }
}
