// Longest Palindromic Substring
class LPS {

    // Iterate over all possible substrings (n * (n + 1)) / 2
    // If the substring is palindrome then measure its length to maintain max length.
    // TC: O(N^3) -> N^2 substrings where to process each substr it takes O(N) time.
    // SC: O(1)
    int LengthOfLPSBruteforce(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int n = str.length();
        int ans = 1;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // substr from i to j (both inclusive)
                if (isPalindrome(str, i, j)) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }

    // TC: O(N)
    // SC: O(1)
    boolean isPalindrome(String s, int low, int high) {
        while (low <= high) {
            if (s.charAt(low) != s.charAt(high)) {
                return false;
            }
            low++;
            high--;
        }
        return true;
    }

    // TC: O(2^N)
    // SC: O(1)
    int LengthOfLPSRecursive(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return helperRecursive(str, 0, str.length() - 1);
    }

    int helperRecursive(String str, int low, int high) {
        if (low > high) {
            return 0;
        }
        if (isPalindrome(str, low, high)) {
            return high - low + 1;
        }

        int leftLPS = helperRecursive(str, low + 1, high);
        int rightLPS = helperRecursive(str, low, high - 1);

        return Math.max(leftLPS, rightLPS);
    }

    // TC: O(N^3) -> DP States = N^2 and to process each dp state
    // we need an isPalindrome function to run.
    // SC: O(N^2) -> N^2 possible dp states.
    int LengthOfLPSTopDownDP(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int n = str.length();
        // We can use the default value of 0 to represent the state of "not yet calculated"
        // Since the length of the longest palindromic substring can never be less than 1
        int[][] dp = new int[n][n];
        return helperTopDownDP(str, 0, str.length() - 1, dp);
    }

    int helperTopDownDP(String str, int low, int high, int[][] dp) {
        if (low > high) {
            return 0;
        }
        if (dp[low][high] != 0) {
            return dp[low][high];
        }

        if (isPalindrome(str, low, high)) {
            return dp[low][high] = high - low + 1;
        }

        int leftLPS = helperRecursive(str, low + 1, high);
        int rightLPS = helperRecursive(str, low, high - 1);

        return dp[low][high] = Math.max(leftLPS, rightLPS);
    }

    // TC: O(N^3)
    // SC: O(N^2)
    int LengthOfLPSBottomUpDP(String str) {
        int n = str.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    dp[i][j] = 0;
                } else if (i == j) {
                    dp[i][j] = 1;
                } else {
                    if (isPalindrome(str, i, j)) {
                        dp[i][j] = j - i + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    // TC: O(N^3)
    // SC: O(N)
    int LengthOfLPSSpaceOptimizedBottomUp(String str) {
        int n = str.length();
        int[] prev = new int[n];
        int[] curr = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    curr[j] = 0;
                } else if (i == j) {
                    curr[j] = 1;
                } else {
                    if (isPalindrome(str, i, j)) {
                        curr[j] = j - i + 1;
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

    int LengthOfLPSisPalindromic(String str) {
        int n = str.length();
        boolean[][] isPalindromic = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i > j) {
                    isPalindromic[i][j] = false;
                } else if (i == j) {
                    isPalindromic[i][j] = true;
                } else {
                    isPalindromic[i][j] =
                        isPalindromic[i + 1][j - 1] &&
                        str.charAt(i) == str.charAt(j);
                }
            }
        }
        int ans = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isPalindromic[i][j]) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }

    int LengthOfLPSMunchavSoup(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        for (char c : str.toCharArray()) {
            sb.append(c);
            sb.append('#');
        }

        char[] arr = sb.toString().toCharArray();
        int n = arr.length;
        int[] radii = new int[n];
        int rightMostCenter = 0;
        int rightMostBoundary = 0;
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            if (i < rightMostBoundary) {
                radii[i] = Math.min(
                    rightMostBoundary - i,
                    radii[2 * rightMostCenter - i]
                );
            }

            while (
                i - radii[i] - 1 >= 0 &&
                i + radii[i] + 1 < n &&
                arr[i - radii[i] - 1] == arr[i + radii[i] + 1]
            ) {
                radii[i]++;
            }

            if (i + radii[i] > rightMostBoundary) {
                rightMostCenter = i;
                rightMostBoundary = i + radii[i];
            }

            maxLen = Math.max(maxLen, radii[i]);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        LPS l = new LPS();
        int length = l.LengthOfLPSMunchavSoup("abcaabaadaa");
        System.out.println(length);
    }
}
