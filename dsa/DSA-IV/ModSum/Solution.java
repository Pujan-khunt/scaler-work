import java.util.ArrayList;

public class Solution {

    private final int MOD = 1000000007;

    public int solve(ArrayList<Integer> A) {

        int maxVal = 0;
        for (int num : A) {
            maxVal = Math.max(maxVal, num);
        }

        // We instantiate an integer array to securely hold the frequency count of every single
        // number.
        int[] freq = new int[maxVal + 1];
        for (int num : A) {
            freq[num]++;
        }

        long totalModuloSum = 0;

        for (int x = 1; x <= maxVal; x++) {
            // If the current number 'x' never actually appeared in our original array, we instantly
            // skip it to save computations.
            if (freq[x] == 0) {
                continue;
            }

            // --- INNER LOOP: REPRESENTS A[j] ---
            // We iterate again from 1 up to the maximum value to pair 'x' with every other possible
            // value 'y'.
            for (int y = 1; y <= maxVal; y++) {

                // Similarly, if 'y' does not exist in our array, we decisively skip it.
                if (freq[y] == 0) {
                    continue;
                }

                // We mathematically calculate the explicit modulo value for this specific
                // combination of 'x' and 'y'.
                long calculatedModulo = x % y;

                // Minor Optimization: If the modulo perfectly yields 0, adding it contributes
                // absolutely nothing to our sum, so we can skip the multiplication.
                if (calculatedModulo == 0) {
                    continue;
                }

                // We calculate exactly how many times the pair (x, y) would have appeared in an
                // O(N^2) brute-force approach.
                // This is strictly the mathematical product of their independent frequencies.
                // We cast to 'long' to thoroughly prevent overflow during the multiplication.
                long totalPairOccurrences = (long) freq[x] * freq[y];

                // We calculate the total sum contribution of this specific pair configuration.
                long contributionToSum = (totalPairOccurrences * calculatedModulo) % MOD;

                // We securely add this calculated contribution to our grand total, instantly
                // applying the modulo operator to ensure it remains bounded.
                totalModuloSum = (totalModuloSum + contributionToSum) % MOD;
            }
        }

        // After fully exhausting all possible unique value pairings, our accumulator securely holds
        // the final optimal answer.
        // We explicitly cast the computed, modulo-safeguarded 'long' back down to a standard 32-bit
        // 'int' as required by the return type.
        return (int) totalModuloSum;
    }
}
