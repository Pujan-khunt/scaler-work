public class MaxSumQueries {
    Node[] sgt;
    int[] arr;
    int n;

    static class Node {
        int maxSubarraySum;
        int totalSum;
        int maxPrefixSum;
        int maxSuffixSum;

        public Node(int maxSubarraySum, int totalSum, int maxPrefixSum, int maxSuffixSum) {
            this.maxSubarraySum = maxSubarraySum;
            this.totalSum = totalSum;
            this.maxPrefixSum = maxPrefixSum;
            this.maxSuffixSum = maxSuffixSum;
        }
    }

    public int[] solve(int[] A, int[][] B) {
        this.arr = A;
        this.n = A.length;
        this.sgt = new Node[4 * n];
        buildTree(0, n - 1, 0);
        int len = 0, j = 0;
        for (int[] query : B) {
            len += query[0] == 2 ? 1 : 0;
        }
        int[] results = new int[len];
        for (int[] query : B) {
            switch (query[0]) {
                case 1:
                    updateTree(0, n - 1, 0, query[1] - 1, query[2]);
                    break;
                case 2:
                    Node result = queryTree(0, n - 1, 0, query[1] - 1, query[2] - 1);
                    results[j++] = result.maxSubarraySum;
                    break;
            }
        }
        return results;
    }

    Node combineNodes(Node lc, Node rc) {
        if (lc == null) {
            return rc;
        }
        if (rc == null) {
            return lc;
        }

        int totalSum = lc.totalSum + rc.totalSum;

        int totalMaxPrefix = Math.max(lc.maxPrefixSum, lc.totalSum + rc.maxPrefixSum);

        int totalMaxSuffix = Math.max(rc.maxSuffixSum, rc.totalSum + lc.maxSuffixSum);

        int combinedSum = lc.maxSuffixSum + rc.maxPrefixSum;
        int combinedMSS = Math.max(Math.max(lc.maxSubarraySum, rc.maxSubarraySum), combinedSum);

        return new Node(combinedMSS, totalSum, totalMaxPrefix, totalMaxSuffix);
    }

    void buildTree(int L, int R, int idx) {
        if (L == R) {
            sgt[idx] = new Node(arr[L], arr[L], arr[L], arr[L]);
            return;
        }
        int m = (R + L) / 2;
        int lc = 2 * idx + 1, rc = 2 * idx + 2;
        buildTree(L, m, lc);
        buildTree(m + 1, R, rc);
        sgt[idx] = combineNodes(sgt[lc], sgt[rc]);
    }

    void updateTree(int L, int R, int idx, int arrIdx, int arrVal) {
        if (L == R) {
            sgt[idx] = new Node(arrVal, arrVal, arrVal, arrVal);
            return;
        }
        int m = (R + L) / 2;
        int lc = 2 * idx + 1, rc = 2 * idx + 2;
        if (arrIdx <= m) {
            updateTree(L, m, lc, arrIdx, arrVal);
        } else {
            updateTree(m + 1, R, rc, arrIdx, arrVal);
        }
        sgt[idx] = combineNodes(sgt[lc], sgt[rc]);
    }

    Node queryTree(int L, int R, int idx, int qL, int qR) {
        // No overlap
        if (L > qR || R < qL) {
            return null;
        }
        // Complete overlap
        if (L >= qL && R <= qR) {
            return sgt[idx];
        }
        // Partial Overlap
        int m = L + (R - L) / 2;
        int lc = 2 * idx + 1, rc = 2 * idx + 2;
        Node left = queryTree(L, m, lc, qL, qR);
        Node right = queryTree(m + 1, R, rc, qL, qR);
        return combineNodes(left, right);
    }
}
