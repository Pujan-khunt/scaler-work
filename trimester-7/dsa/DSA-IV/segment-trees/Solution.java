public class Solution {
    private Node[] sgt;
    private int[] arr;
    private int n;

    static class Node {
        int[] sortedArr;

        public Node(int[] sortedElements) {
            this.sortedArr = sortedElements;
        }
    }

    public int[] solve(int[] A, int[][] B) {
        this.arr = A;
        this.n = A.length;
        this.sgt = new Node[4 * n + 1];
        buildTree(1, 1, n);
        long minValue = Integer.MAX_VALUE;
        long maxValue = Integer.MIN_VALUE;
        for (int value : A) {
            minValue = Math.min(minValue, value);
            maxValue = Math.max(maxValue, value);
        }
        int[] results = new int[B.length];
        for (int i = 0; i < B.length; i++) {
            int qL = B[i][0], qR = B[i][1];
            long s = minValue, e = maxValue;
            int requiredK = B[i][2];
            long kthSmallestCandidate = -1;

            while (s <= e) {
                long m = s + (e - s) / 2;
                int cnt = cntLessThanOrEqual(1, 1, n, qL, qR, (int) m);
                if (cnt >= requiredK) {
                    kthSmallestCandidate = m;
                    e = m - 1;
                } else {
                    s = m + 1;
                }
            }
            results[i] = (int) kthSmallestCandidate;
        }
        return results;
    }

    // Merge 2 sorted arrays into a single sorted array.
    private Node combineNodes(Node leftChild, Node rightChild) {
        int[] leftArr = leftChild.sortedArr;
        int[] rightArr = rightChild.sortedArr;
        int[] mergedArr = new int[leftArr.length + rightArr.length];
        int i = 0, j = 0, k = 0;

        while (i < leftArr.length && j < rightArr.length) {
            mergedArr[k++] = leftArr[i] <= rightArr[j] ? leftArr[i++] : rightArr[j++];
        }
        // Merge remaining elements
        while (i < leftArr.length) {
            mergedArr[k++] = leftArr[i++];
        }
        while (j < rightArr.length) {
            mergedArr[k++] = rightArr[j++];
        }
        return new Node(mergedArr);
    }

    private void buildTree(int idx, int L, int R) {
        if (L == R) {
            int[] singleElementSortedArray = new int[] {arr[L - 1]};
            sgt[idx] = new Node(singleElementSortedArray);
            return;
        }

        int m = (R + L) / 2;
        int lc = 2 * idx;
        int rc = 2 * idx + 1;
        buildTree(lc, L, m);
        buildTree(rc, m + 1, R);
        sgt[idx] = combineNodes(sgt[lc], sgt[rc]);
    }

    private int cntLessThanOrEqual(int idx, int L, int R, int qL, int qR, int val) {
        if (L > qR || R < qL) {
            return 0;
        }
        if (L >= qL && R <= qR) {
            return calculateUpperBoundCount(sgt[idx].sortedArr, val);
        }

        int m = (R + L) / 2;
        int lc = 2 * idx, rc = 2 * idx + 1;
        int leftCnt = cntLessThanOrEqual(lc, L, m, qL, qR, val);
        int rightCnt = cntLessThanOrEqual(rc, m + 1, R, qL, qR, val);
        return leftCnt + rightCnt;
    }

    private int calculateUpperBoundCount(int[] sortedArray, int targetValue) {
        int s = 0, e = sortedArray.length - 1;
        int ans = 0;

        while (s <= e) {
            int mid = (e + s) / 2;

            if (sortedArray[mid] <= targetValue) {
                ans = mid + 1;
                s = mid + 1;
            } else {
                e = mid - 1;
            }
        }
        return ans;
    }
}
