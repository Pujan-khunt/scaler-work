import java.util.ArrayList;

public class Solution {

    public int[] solve(String A, String B) {
        if (A == null || B == null || B.length() > A.length()) {
            return new int[] {-1};
        }
        String combinedString = B + "$" + A;

        // We extract the total length of our newly formed combined string.
        int n = combinedString.length();

        // We initialize our Z-array to systematically store the prefix match lengths.
        int[] Z = new int[n];
        int L = 0, R = 0;
        for (int i = 1; i < n; i++) {
            if (i <= R) {
                Z[i] = Math.min(R - i + 1, Z[i - L]);
            }
            while (i + Z[i] < n && combinedString.charAt(Z[i]) == combinedString.charAt(i + Z[i])) {
                Z[i]++;
            }
            if (i + Z[i] - 1 > R) {
                L = i;
                R = i + Z[i] - 1;
            }
        }


        ArrayList<Integer> result = new ArrayList<>();

        int patternLength = B.length();
        for (int i = patternLength + 1; i < n; i++) {
            if (Z[i] == patternLength) {
                int originalIndex = i - patternLength - 1;
                result.add(originalIndex);
            }
        }

        // If our result list remains completely empty, the pattern 'B' does not occur in text 'A'.
        // We return an array containing strictly -1 to represent failure.
        if (result.isEmpty()) {
            return new int[] {-1};
        }

        // We dynamically instantiate our final integer array with the exact size of our discovered
        // matches.
        int[] finalResults = new int[result.size()];

        // We sequentially transfer the valid indices from our dynamic list into our static array.
        for (int i = 0; i < result.size(); i++) {
            finalResults[i] = result.get(i);
        }

        // We confidently return the fully populated array of exact starting indices.
        return finalResults;
    }
}
