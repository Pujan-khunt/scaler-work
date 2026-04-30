public class topdown {

    private static int lps(String s, int low, int high, int[][] dp) {
        // Palindrome of a single character string is always the character itself.
        if (low == high) {
            return 1;
        }

        // Case when s = "AA", low = 0, high = 1
        // next iteration low -> low + 1 and high = high - 1
        // result: low = 1, high = 0
        if (low > high) {
            return 0;
        }

        // Since there exists no string with lps = 0, we can safely use it for verifying existence.
        // 0 can the answer only when the string is empty which is not part of the constraint.
        // This eliminates redundancies and only computes the answer for at max n * n strings.
        // Redundancy Proof:
        // S = "ABCB", lps = max(ABC, BCB) [root]
        // S = "ABC", lps = max(AB, BC) [left of root]
        // S = "BCB", lps = max(BC, CB) [right of root]
        // S = "BC" repeats in both parts of the tree. Hence proved.
        if (dp[low][high] != 0) {
            return dp[low][high];
        }

        // Prefer to consume characters at both ends to produce the longest palindrome
        if (s.charAt(low) == s.charAt(high)) {
            return dp[low][high] = 2 + lps(s, low + 1, high - 1, dp);
        }

        return dp[low][high] = Math.max(lps(s, low + 1, high, dp), lps(s, low, high - 1, dp));
    }

    public static void main(String[] args) {
        String s = "acbadd";
        int[][] dp = new int[s.length()][s.length()];
        int answer = lps(s, 0, s.length() - 1, dp);
        System.out.println(answer);
    }
}
