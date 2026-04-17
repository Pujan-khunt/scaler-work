public class Solution {

    public int solve(String A, String B) {
        int lenA = A.length();
        int lenB = B.length();

        if (lenA > lenB) {
            return 0;
        }

        long MOD = 1000000007L;

        // We define a base multiplier strictly greater than the maximum possible length (10^5).
        // This flawlessly prevents "carry" collisions (e.g., 100003 'a's mathematically cannot sum
        // up to equal one 'b').
        long BASE = 100003L;

        // We precompute and store the unique hash value assigned to each of the 26 lowercase
        // English letters.
        long[] letterHashValues = new long[26];
        long currentPower = 1;
        for (int i = 0; i < 26; i++) {
            letterHashValues[i] = currentPower;
            currentPower = (currentPower * BASE) % MOD;
        }

        long targetHash = 0;
        for (int i = 0; i < lenA; i++) {
            int charIndex = A.charAt(i) - 'a';
            targetHash = (targetHash + letterHashValues[charIndex]) % MOD;
        }

        // We calculate the hash for our very first window in string B (from index 0 to lengthA -
        // 1).
        long currentWindowHash = 0;
        for (int i = 0; i < lenA; i++) {
            int charIndex = B.charAt(i) - 'a';
            currentWindowHash = (currentWindowHash + letterHashValues[charIndex]) % MOD;
        }

        int totalPermutationsFound = 0;

        if (currentWindowHash == targetHash) {
            totalPermutationsFound++;
        }

        for (int i = lenA; i < lenB; i++) {

            // Step 1: Remove the character that is falling out of the left side of our sliding
            // window.
            char characterGoingOut = B.charAt(i - lenA);

            // We mathematically subtract its unique value. We add MODULO before applying the final
            // modulo to strictly prevent negative values in Java.
            currentWindowHash =
                    (currentWindowHash - letterHashValues[characterGoingOut - 'a'] + MOD) % MOD;

            // Step 2: Add the brand new character that is entering the right side of our sliding
            // window.
            char characterComingIn = B.charAt(i);

            // We mathematically add its unique value to our rolling hash.
            currentWindowHash =
                    (currentWindowHash + letterHashValues[characterComingIn - 'a']) % MOD;

            // Step 3: We compare our dynamically updated window hash to our target hash.
            // If they are identically equal, we have definitively found a permutation!
            if (currentWindowHash == targetHash) {
                totalPermutationsFound++;
            }
        }

        // We confidently return the fully calculated count of all valid permutations.
        return totalPermutationsFound;
    }
}
