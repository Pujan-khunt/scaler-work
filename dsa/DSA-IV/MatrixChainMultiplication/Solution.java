import java.util.Arrays;

public class Solution {
    int[][] dp;
    int[] matrices;

    public int solve(int[] A) {
        int n = A.length;
        int matricesCount = n - 1;
        this.matrices = A;
        this.dp = new int[n][n];
        for (int[] row : dp)
            Arrays.fill(row, -1);
        // arr: [40, 20, 30, 10, 30], N = 5, number of matrices = 4
        // 1: 40 x 20
        // 2: 20 x 30
        // 3: 30 x 10
        // 4: 10 x 30 -> number of matrices = N - 1 = 4
        // index 1 represents the first matrix, index 4 (matricesCount) represents last matrix.
        // Hence we are essentially passing the first and the last index and not skipping anything.
        return minimumMultiplications(1, matricesCount);
    }

    private int minimumMultiplications(int startIndex, int endIndex) {
        // Matrix multiplication of a single matrix is not possible.
        if (startIndex == endIndex) {
            return 0;
        }

        if (dp[startIndex][endIndex] != -1) {
            return dp[startIndex][endIndex];
        }

        // We declare an integer variable to keep track of the absolute minimum cost discovered
        // among all possible partition points.
        // We initialize it to Integer.MAX_VALUE to ensure that the very first valid partition cost
        // we calculate will always be smaller and safely overwrite this value.
        int minimum = Integer.MAX_VALUE;

        // This loop essentially travels over all matrices.
        // partitionIndex can't be equal to endIndex, that would result in an empty right group.
        // Finds the partition index to split the group of (startIndex, endIndex) to:
        // Calculate total costs for all possible partitions and store the minimum.
        // left: (startIndex, partitionIndex)
        // OR [startIndex-1, startIndex], [startIndex, startIndex+1], ... [partitionIndex-1,
        // partitionIndex]
        // right: (partitionIndex + 1, endIndex)
        // OR [partitionIndex, partitionIndex+1], [partitionIndex+1, partitionIndex+2], ...
        // [endIndex-1, endIndex]
        for (int partitionIndex = startIndex; partitionIndex < endIndex; partitionIndex++) {
            // Dimensions: matrices[startIndex - 1] * matrices[partitionIndex]
            int left = minimumMultiplications(startIndex, partitionIndex);
            // Dimensions: matrices[partitionIndex] * matrices[endIndex]
            int right = minimumMultiplications(partitionIndex + 1, endIndex);
            // Cost to merge, since its also a matrix multiplication.
            int merge = matrices[startIndex - 1] * matrices[partitionIndex] * matrices[endIndex];
            minimum = Math.min(minimum, left + right + merge);
        }

        return dp[startIndex][endIndex] = minimum;
    }
}
