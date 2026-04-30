// The Adaptee contains some useful behavior, but its interface is incompatible
// with the existing client code. The Adaptee needs adaptation before the
// client code can use it.
//
// This class is generally in a 3rd party library which we cannot modify
public class CryptoAPI {

    public void sendCrypto(String walletAddress, double btcAmount) {
        System.out.println("Executing blockchain transaction...");
        System.out.println(
                " -> Transferring " + btcAmount + " BTC to wallet [" + walletAddress + "]");
    }

    public double getConversionRateUsdToBtc() {
        // Mock conversion rate: $100,000 USD = 1 BTC
        return 0.00001;
    }
}
