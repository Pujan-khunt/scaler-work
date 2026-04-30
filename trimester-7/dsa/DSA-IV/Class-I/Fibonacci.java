public class Fibonacci {

    // TC: O(2^N) -> Each function makes 2 recursive calls
    // SC: O(N)   -> The maximum height of the call stack will be N.
    int calculateFibonacciRecursive(int n) {
        if (n < 0) throw new IllegalStateException(
            "fibonacci(" + n + ") doesn't exist"
        );
        if (n <= 1) return n;
        return (
            calculateFibonacciRecursive(n - 1) +
            calculateFibonacciRecursive(n - 2)
        );
    }

    // TC: O(N) -> There can be at max N+1 possible states of the dp array.
    // SC: O(N) -> O(N) for dp array and O(N) for call stack. O(N) + O(N) = O(N)
    int calculateFibonacciTopDownDP(int n) {
        if (n < 0) throw new IllegalStateException(
            "fibonacci(" + n + ") doesn't exist"
        );
        // We can use the default value of 0 to represent the state of
        // "not yet calculated" in the dp array since no value other
        // than n = 0 can have fibonnaci(n) as 0.
        int[] dp = new int[n + 1];
        return helperTopDownDP(dp, n);
    }

    int helperTopDownDP(int[] dp, int n) {
        if (n < 0) throw new IllegalStateException(
            "fibonacci(" + n + ") doesn't exist"
        );
        if (n <= 1) return n;
        if (dp[n] != 0) return dp[n];
        return dp[n] = helperTopDownDP(dp, n - 1) + helperTopDownDP(dp, n - 2);
    }

    // TC: O(N) -> There can be at max N+1 possible states of the dp array.
    // SC: O(N) -> O(N) for the dp array, but O(1) for call stack. O(N) + O(1) = O(N)
    int calculateFibonacciBottomUp(int n) {
        if (n < 0) throw new IllegalStateException(
            "fibonacci(" + n + ") doesn't exist"
        );
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // TC: O(N) -> Loop iterates from 2 -> N
    // SC: O(1) -> Only 2 variables are used.
    int calculateFibonacciSpaceOptimizedBottomUp(int n) {
        if (n < 0) throw new IllegalStateException(
            "fibonacci(" + n + ") doesn't exist"
        );
        int prev = 0; // represents dp[i-2] and currently dp[0];
        int curr = 1; // represents dp[i-1] and currently dp[1];
        for (int i = 2; i <= n; i++) {
            // Store dp[i-1] for later use.
            int temp = curr;

            // dp[i] = dp[i-1] + dp[i-2]
            // dp[i-1] (curr) becomes dp[i]
            curr = curr + prev;

            // dp[i-2] = dp[i-1]
            // dp[i-2] (prev) becomes dp[i-1]
            prev = temp;
        }

        // After the loop ends for i = 2
        // curr becomes dp[i]
        // prev becomes dp[i-1]

        // Hence when the loop finishes its last iteration at i = n
        // curr becomes dp[n]
        // prev becomes dp[n-1]
        return curr;
    }

    int calculateFibonacciMatrixExponentiation(int n) {
        if (n < 0) throw new IllegalStateException(
            "fibonacci(" + n + ") doesn't exist"
        );
        int[][] T = { { 1, 1 }, { 1, 0 } };
        int[][] tPowerN = calculatePowerBinaryExponentiation(T, n);
        return tPowerN[1][0];
    }

    // TC: O(Log N) -> Binary Exponentiation takes Log N time.
    // SC: O(1)     -> The matrices don't increase in size based on N.
    int[][] calculatePowerBinaryExponentiation(int[][] matrix, int power) {
        if (power == 0) {
            return new int[][] { { 0, 0 }, { 0, 0 } };
        }
        if (power == 1) {
            return matrix;
        }
        int[][] val = calculatePowerBinaryExponentiation(matrix, power / 2); // get value of a^(b/2)
        val = multiplicateMatrices(val, val); // square the sqrt (a^b = (a^(b/2))^2)
        if ((power & 1) == 1) {
            // a^b = a^(b-1) * a, when power is odd
            val = multiplicateMatrices(matrix, val);
        }
        return val;
    }

    int[][] multiplicateMatrices(int[][] A, int[][] B) {
        int common = A[0].length;
        int rows = A.length;
        int cols = B[0].length;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            // Iterating through rows of A
            for (int j = 0; j < cols; j++) {
                // Iterating through cols of B
                // Add all values when i'th row of A is multipled with j'th column of B
                for (int k = 0; k < common; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Fibonacci f = new Fibonacci();
        int i = 0;
        while (true) {
            System.out.println(
                "n: " + i + ", result: " + f.calculateFibonacciRecursive(i++)
            );
        }
    }
}
