public class Solution {
    public int solve(String A) {

        int n = A.length();

        // Z[i]: length of the longest substring start from index i which perfectly matches the
        // prefix of the string
        int[] Z = new int[n];

        int L = 0;
        int R = 0;

        // Z[0] is skipped, since its always 'n'.
        for (int i = 1; i < n; i++) {
            if (i <= R) {
                Z[i] = Math.min(R - i + 1, Z[i - L]);
            }

            while (i + Z[i] < n && A.charAt(i + Z[i]) == A.charAt(Z[i])) {
                Z[i]++;
            }

            // Update z-box dimensions
            if (i + Z[i] - 1 > R) {
                L = i;
                R = i + Z[i] - 1;
            }
        }

        for (int i = 1; i < n; i++) {
            if (i + Z[i] == n) {
                return i;
            }
        }

        return n;
    }
}
