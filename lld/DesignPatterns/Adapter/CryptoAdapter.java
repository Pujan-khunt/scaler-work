public class CryptoAdapter implements PaymentGateway {

    private final CryptoAPI cryptoAPI;

    public CryptoAdapter(CryptoAPI cryptoAPI) {
        this.cryptoAPI = cryptoAPI;
    }

    @Override
    public void processPayment(String accountId, double amount) {
        System.out.println(
                "\n[Adapter]: Translating standard payment request to Crypto API format...");

        // 1. Translate the data format (Account ID to Wallet Address)
        // In reality, this might involve a database lookup.
        String mappedWalletAddress = "0xABC123_" + accountId;

        // 2. Translate the business logic (USD to BTC)
        double conversionRate = cryptoAPI.getConversionRateUsdToBtc();
        double btcAmount = amount * conversionRate;

        // 3. Call the Adaptee
        cryptoAPI.sendCrypto(mappedWalletAddress, btcAmount);
    }

}
