public class StripeGateway implements PaymentGateway {

    @Override
    public void processPayment(String accountId, double amount) {
        System.out.println("Processing standard Stripe payment...");
        System.out.println(" -> Charging account [" + accountId + "] $" + amount);
    }

}
