public class Main {
    public static void main(String[] args) {
        String user = "User_JohnDoe";
        double cartTotalUsd = 25000.00; // $25,000

        // 1. Using a natively compatible gateway
        System.out.println("--- Checkout via Stripe ---");
        PaymentGateway stripe = new StripeGateway();
        CheckoutSystem standardCheckout = new CheckoutSystem(stripe);
        standardCheckout.checkout(user, cartTotalUsd);

        // 2. Using the incompatible CryptoApi via the Adapter
        System.out.println("\n--- Checkout via Crypto API ---");
        CryptoAPI thirdPartyCryptoApi = new CryptoAPI();

        // We wrap the incompatible API in our Adapter
        PaymentGateway cryptoAdapter = new CryptoAdapter(thirdPartyCryptoApi);

        // The CheckoutSystem accepts the adapter because it implements PaymentGateway
        CheckoutSystem cryptoCheckout = new CheckoutSystem(cryptoAdapter);
        cryptoCheckout.checkout(user, cartTotalUsd);
    }
}
