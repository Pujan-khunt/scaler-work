public class Solution {
    public int[][] solve(int[][] A) {
        int n = A.length;

        // dp[k][i][j] represents the shortest distance to visit 'j' from 'i'
        // using ONLY vertices from the set {0, 1, 2, ... k} as intermediate stops.

        // 'k'th city acts as an intermediary
        for (int k = 0; k < n; k++) {
            // Iterate over all possible sources.
            for (int i = 0; i < n; i++) {
                // Iterate over all possible destinations.
                for (int j = 0; j < n; j++) {
                    // Now the current path is from i -> k and k -> j, where
                    // k acts as the intermediary.

                    // Verify existence of both sub paths i -> k and k -> j
                    if (A[i][k] != -1 && A[k][j] != -1) {
                        int distanceWithIntermediate = A[i][k] + A[k][j];

                        // New route (i -> j, j -> k) is valid only when:
                        // 1. No route exists from i -> j i.e. A[i][j] = -1
                        // 2. new distance is better (smaller) then current best (smallest)
                        // distance.
                        if (A[i][j] == -1 || distanceWithIntermediate < A[i][j]) {
                            A[i][j] = distanceWithIntermediate;
                        }
                    }
                }
            }
        }
        return A;
    }
}
