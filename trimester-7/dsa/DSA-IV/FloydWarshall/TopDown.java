import java.util.Arrays;

public class TopDown {
    private final int INF = 500000000;
    int[][] matrix;
    int[][][] dp;

    public int[][] solve(int[][] A) {
        this.matrix = A;
        int n = A.length;

        // k: maximum allowed intermediate vertex (from 0 to totalVertices - 1).
        // i: starting source vertex.
        // j: ending destination vertex.
        this.dp = new int[n][n][n];
        for (int[][] matrix : dp) {
            for (int[] row : matrix) {
                // -2: Not yet calculated
                // -1: No path exists
                Arrays.fill(row, -2);
            }
        }

        int[][] result = new int[n][n];
        for (int source = 0; source < n; source++) {
            for (int destination = 0; destination < n; destination++) {
                int shortestDistance = calcShortestPath(n - 1, source, destination);
                result[source][destination] = shortestDistance >= INF ? -1 : shortestDistance;
            }
        }
        return result;
    }

    private int calcShortestPath(int maxIntermediateNode, int source, int destination) {
        // No allowed vertices, only option remains for a direct path
        if (maxIntermediateNode == -1) {
            if (source == destination) {
                return 0;
            }
            if (matrix[source][destination] == -1) {
                return INF;
            }
            return matrix[source][destination];
        }

        if (dp[maxIntermediateNode][source][destination] != -2) {
            return dp[maxIntermediateNode][source][destination];
        }


        // Skipping 'k' to be an intermediate node.
        int skip = calcShortestPath(maxIntermediateNode - 1, source, destination);

        // Choosing 'k' to be an intermediate node.
        int take = 0;
        take += calcShortestPath(maxIntermediateNode - 1, source, maxIntermediateNode);
        take += calcShortestPath(maxIntermediateNode - 1, maxIntermediateNode, destination);

        return dp[maxIntermediateNode][source][destination] = Math.min(skip, take);
    }
}
