public class Longest_Palindromic_Subsequence {

    public static void main(String[] args) {
        Longest_Palindromic_Subsequence lps =
            new Longest_Palindromic_Subsequence();
        System.out.println(lps.LLPSSpaceOptimizedBottomUpDP("bbbab"));
    }

    // Length of Longest Palindromic Subsequence
    // TC: O(2^N)
    // SC: O(N)
    int LLPSRecursive(String s) {
        return helperRecursive(s, 0, s.length() - 1);
    }

    int helperRecursive(String s, int low, int high) {
        if (low > high) {
            return 0;
        }
        if (low == high) {
            return 1;
        }
        if (s.charAt(low) == s.charAt(high)) {
            return 2 + helperRecursive(s, low + 1, high - 1);
        }
        return Math.max(
            helperRecursive(s, low + 1, high),
            helperRecursive(s, low, high - 1)
        );
    }

    int LLPSTopDownDP(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        return helperTopDownDP(s, 0, n - 1, dp);
    }

    int helperTopDownDP(String s, int low, int high, int[][] dp) {
        if (low > high) {
            return 0;
        }
        if (low == high) {
            return 1;
        }
        if (dp[low][high] != 0) {
            return dp[low][high];
        }

        if (s.charAt(low) == s.charAt(high)) {
            return dp[low][high] = 2 + helperRecursive(s, low + 1, high - 1);
        }
        return dp[low][high] = Math.max(
            helperRecursive(s, low + 1, high),
            helperRecursive(s, low, high - 1)
        );
    }

    int LLPSBottomUpDP(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    dp[i][j] = 0;
                } else if (i == j) {
                    dp[i][j] = 1;
                } else {
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = 2 + dp[i + 1][j - 1];
                    } else {
                        dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    int LLPSSpaceOptimizedBottomUpDP(String s) {
        int n = s.length();
        int[] curr = new int[n];
        int[] prev = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    curr[j] = 0;
                } else if (i == j) {
                    curr[j] = 1;
                } else {
                    if (s.charAt(i) == s.charAt(j)) {
                        curr[j] = 2 + prev[j - 1];
                    } else {
                        curr[j] = Math.max(prev[j], curr[j - 1]);
                    }
                }
            }
            prev = curr;
            curr = new int[n];
        }
        return prev[n - 1];
    }
}
