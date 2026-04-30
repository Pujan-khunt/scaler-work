public class BobAndQueries {
    int[] sgt;
    int n;
    int[] arr;

    public int[] solve(int length, int[][] B) {
        int[] A = new int[length];
        this.n = A.length;
        this.arr = A;
        this.sgt = new int[4 * n];
        build(0, n - 1, 0);
        int len = 0, j = 0;
        for (int[] query : B)
            len += query[0] == 3 ? 1 : 0;
        int[] results = new int[len];
        for (int[] query : B) {
            switch (query[0]) {
                case 1:
                    update(0, n - 1, 0, query[1] - 1, A[query[1] - 1] + 1);
                    break;
                case 2:
                    update(0, n - 1, 0, query[1] - 1, Math.max(A[query[1] - 1] - 1, 0));
                    break;
                case 3:
                    results[j++] = queryFn(0, n - 1, 0, query[1] - 1, query[2] - 1);
                    break;
            }
        }
        return results;
    }

    int queryFn(int L, int R, int idx, int qL, int qR) {
        // No overlap
        if (qR < L || R < qL) {
            return 0;
        }
        // Complete overlap
        if (qL <= L && R <= qR) {
            return sgt[idx];
        }
        // Partial overlap
        int m = (L + R) / 2;
        int lc = 2 * idx + 1, rc = 2 * idx + 2;
        int left = queryFn(L, m, lc, qL, qR);
        int right = queryFn(m + 1, R, rc, qL, qR);
        return left + right;
    }

    void build(int L, int R, int idx) {
        if (L == R) {
            sgt[idx] = arr[L];
            return;
        }
        int m = (L + R) / 2;
        int lc = 2 * idx + 1, rc = 2 * idx + 2;
        build(L, m, lc);
        build(m + 1, R, rc);
        sgt[idx] = sgt[lc] + sgt[rc];
    }

    void update(int L, int R, int idx, int newidx, int newval) {
        if (L == R) {
            sgt[idx] = arr[newidx] = newval;
            return;
        }
        int m = (L + R) / 2;
        int lc = 2 * idx + 1, rc = 2 * idx + 2;
        if (newidx <= m) {
            update(L, m, lc, newidx, newval);
        } else {
            update(m + 1, R, rc, newidx, newval);
        }
        sgt[idx] = sgt[lc] + sgt[rc];
    }
}

