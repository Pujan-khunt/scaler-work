import java.util.Arrays;

public class Solution {
    public int numTrees(int A) {
        // dp[i]: number of unique BSTs possible with 'n' nodes.
        // Range: 0 to A
        int[] dp = new int[A + 1];
        Arrays.fill(dp, -1);
        return calculateUniqueBSTsTopDown(A, dp);
    }

    private int calculateUniqueBSTsTopDown(int numberOfNodes, int[] dp) {
        // Only 1 way to form a tree with nodes, by placing nothing.
        if (numberOfNodes == 0 || numberOfNodes == 1) {
            return 1;
        }

        if (dp[numberOfNodes] != -1) {
            return dp[numberOfNodes];
        }

        int total = 0;

        // Iterate over all nodes and provide each of them the opportunity to be the root.
        for (int root = 1; root <= numberOfNodes; root++) {
            // All elements smaller than root will be present in the left subtree.
            int left = root - 1;

            // All elements larger than root will be present in the right subtree.
            int right = numberOfNodes - root;

            int uniqueLeftSubtrees = calculateUniqueBSTsTopDown(left, dp);
            int uniqueRightSubtrees = calculateUniqueBSTsTopDown(right, dp);

            int treesWithCurrentRoot = uniqueLeftSubtrees * uniqueRightSubtrees;
            total += treesWithCurrentRoot;
        }

        return dp[numberOfNodes] = total;
    }
}
