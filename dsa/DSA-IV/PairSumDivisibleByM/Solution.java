import java.util.ArrayList;

public class Solution {
    int MOD = 1000000007;

    public int solve(ArrayList<Integer> A, int B) {

        // We fundamentally rely on the mathematical properties of modular arithmetic to solve this
        // efficiently.
        // Specifically, for any two numbers X and Y, (X + Y) % B == 0 holds true strictly if and
        // only if:
        // Case 1: (X % B) == 0 AND (Y % B) == 0 (Both remainders are perfectly 0).
        // Case 2: (X % B) + (Y % B) == B (The sum of their respective remainders exactly equals the
        // divisor B).

        // To count these pairs efficiently without an O(N^2) brute-force nested loop, we will track
        // the frequency of each possible remainder.
        // The possible remainders when dividing any integer by 'B' range strictly from 0 up to (B -
        // 1).
        // Therefore, we instantiate a long array named 'remainderFrequencies' exactly of size 'B'
        // to securely hold these counts.
        // We explicitly use 'long' data types here because the frequency of a remainder could be up
        // to 100,000, and multiplying two frequencies could cause a standard 32-bit integer to
        // overflow.
        long[] remainderFrequencies = new long[B];

        for (int num : A) {
            // No need to handle negative remainder case, since A[i] >= 1
            remainderFrequencies[num % B]++;
        }

        // We declare a 'long' variable named 'totalValidPairs' to rigorously accumulate the grand
        // total of valid pairs discovered.
        long totalValidPairs = 0;

        // Numbers that leave a remainder of 0 are already perfectly divisible by B.
        // To form a valid sum divisible by B, a number with remainder 0 can only ever be
        // mathematically paired with another number that also has a remainder of 0.
        long countOfZeroRemainders = remainderFrequencies[0];

        // If we have 'N' items that can pair with each other, the total number of unique pairs is
        // dictated by combinations: N * (N - 1) / 2.
        long pairsFromZeroRemainders = (countOfZeroRemainders * (countOfZeroRemainders - 1)) / 2;

        pairsFromZeroRemainders = pairsFromZeroRemainders % MOD;

        totalValidPairs = (totalValidPairs + pairsFromZeroRemainders) % MOD;

        for (int i = 1; i <= B / 2; i++) {
            if (i == B - i) {
                // Similar to the remainder 0 case, numbers with exactly half the remainder can only
                // pair with other numbers holding the exact same half remainder.
                long countOfHalfRemainders = remainderFrequencies[i];

                // We use the same combinatorics formula N * (N - 1) / 2 to find the strictly unique
                // pairs among these identical half-remainders.
                long pairsFromHalfRemainders =
                        (countOfHalfRemainders * (countOfHalfRemainders - 1)) / 2;

                // We meticulously apply the modulo operator to this isolated pair count.
                pairsFromHalfRemainders = pairsFromHalfRemainders % MOD;

                // We append this safely calculated count to our grand total, simultaneously
                // applying the modulo to guarantee the sum stays within bounds.
                totalValidPairs = (totalValidPairs + pairsFromHalfRemainders) % MOD;
            }
            // If we are dealing with standard, distinct complementary remainders (e.g., remainder 1
            // pairs with remainder B-1)...
            else {

                // We explicitly extract the frequency of the first remainder component 'i'.
                long countOfFirstRemainder = remainderFrequencies[i];

                // We explicitly extract the frequency of the mathematically required complementary
                // remainder component 'B - i'.
                long countOfComplementaryRemainder = remainderFrequencies[B - i];

                // Because any item from the first group can pair uniquely with any item from the
                // complementary group, the total valid pairs is their direct mathematical product.
                long pairsFromDistinctRemainders =
                        (countOfFirstRemainder * countOfComplementaryRemainder);

                // We meticulously apply the modulo operator to this isolated product count.
                pairsFromDistinctRemainders = pairsFromDistinctRemainders % MOD;

                // We append this explicitly distinct pair count to our grand total, again securely
                // applying the modulo to prevent overflow.
                totalValidPairs = (totalValidPairs + pairsFromDistinctRemainders) % MOD;
            }
        }

        // After thoroughly traversing and mathematically pairing all possible remainder
        // frequencies, our accumulator securely holds the final optimal answer.
        // Because our method signature strictly dictates returning a 32-bit integer, we explicitly
        // cast the fully computed, safely modulo'd 'long' back down to an 'int'.
        return (int) totalValidPairs;
    }
}
