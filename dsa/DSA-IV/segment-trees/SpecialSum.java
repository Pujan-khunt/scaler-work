public class SpecialSum {
    private final long MOD = 1000000007L;
    private Node[] sgt;
    private int[] arr;

    static class Node {
        // A[L] + A[L+1] + ... + A[R].
        long standardSum;
        // L * A[L] + (L+1) * A[L+1] + ... + R * A[R], 1-based
        long weightedSum;

        // 1 * A[L] + 2 * A[L+1] + 3 * A[L+2] + ... (R-1) * A[R] is equal to
        // weightedSum - ((L - 1) * standardSum)

        public Node(long standardSum, long weightedSum) {
            this.standardSum = standardSum;
            this.weightedSum = weightedSum;
        }
    }

    public int[] solve(int[] A, int[][] B) {
        this.arr = A;
        int n = A.length;
        this.sgt = new Node[4 * n];
        buildTree(1, 1, n);
        int len = 0, j = 0;
        for (int i = 0; i < B.length; i++) {
            if (B[i][0] == 2) {
                len++;
            }
        }
        int[] results = new int[len];
        for (int i = 0; i < B.length; i++) {
            switch (B[i][0]) {
                case 1:
                    int updateIndex = B[i][1];
                    int newValue = B[i][2];
                    updateTree(1, 1, n, updateIndex, newValue);
                    break;
                case 2:
                    // B[i][1 or 2] are 1 based indices
                    Node result = queryTree(1, 1, n, B[i][1], B[i][2]);
                    // required relative sum = weightedSum - ((L - 1) * standardSum).
                    long offset = B[i][1] - 1;
                    long subtractTerm = (offset * result.standardSum) % MOD;
                    long requiredRelativeSum = (result.weightedSum - subtractTerm + MOD) % MOD;
                    results[j++] = (int) requiredRelativeSum;
                    break;
            }
        }
        return results;
    }

    private Node combineNodes(Node leftChild, Node rightChild) {
        if (leftChild == null) {
            return rightChild;
        }
        if (rightChild == null) {
            return leftChild;
        }
        long combinedStandardSum = (leftChild.standardSum + rightChild.standardSum) % MOD;
        long combinedWeightedSum = (leftChild.weightedSum + rightChild.weightedSum) % MOD;
        return new Node(combinedStandardSum, combinedWeightedSum);
    }

    private void buildTree(int idx, int L, int R) {
        if (L == R) {
            // Convert to 0 based
            long val = arr[L - 1] % MOD;
            long initialStandardSum = val;
            long initialWeightedSum = (val * L) % MOD;
            sgt[idx] = new Node(initialStandardSum, initialWeightedSum);
            return;
        }
        int m = (R + L) / 2;
        int lc = 2 * idx, rc = 2 * idx + 1;
        buildTree(lc, L, m);
        buildTree(rc, m + 1, R);
        sgt[idx] = combineNodes(sgt[lc], sgt[rc]);
    }

    private void updateTree(int idx, int L, int R, int arrIdx, int arrVal) {
        if (L == R) {
            long newVal = arrVal % MOD;
            long newStandardSum = newVal;
            long newWeightedSum = (newVal * L) % MOD;
            sgt[idx] = new Node(newStandardSum, newWeightedSum);
            return;
        }
        int m = (R + L) / 2;
        int lc = 2 * idx;
        int rc = 2 * idx + 1;
        if (arrIdx <= m) {
            updateTree(lc, L, m, arrIdx, arrVal);
        } else {
            updateTree(rc, m + 1, R, arrIdx, arrVal);
        }
        sgt[idx] = combineNodes(sgt[lc], sgt[rc]);
    }

    private Node queryTree(int idx, int L, int R, int qL, int qR) {
        if (L > qR || R < qL) {
            return null;
        }
        if (L >= qL && R <= qR) {
            return sgt[idx];
        }
        int m = (R + L) / 2;
        // Calculate based on 1-based indexing
        int lc = 2 * idx;
        int rc = 2 * idx + 1;

        Node left = queryTree(lc, L, m, qL, qR);
        Node right = queryTree(rc, m + 1, R, qL, qR);
        return combineNodes(left, right);
    }
}
