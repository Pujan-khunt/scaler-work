import java.util.ArrayList;
import java.util.List;

class MinimumRangeQuery {
    int[] sgt;
    int[] arr;

    public int[] solve(int[] arr, int[][] queries) {
        int n = arr.length;
        this.sgt = new int[4 * n];
        this.arr = arr;
        build(0, 0, n - 1);
        List<Integer> results = new ArrayList<>();
        for (int[] queryArr : queries) {
            if (queryArr[0] == 0) {
                update(0, 0, n - 1, queryArr[1] - 1, queryArr[2]);
            } else if (queryArr[0] == 1) {
                results.add(query(0, 0, n - 1, queryArr[1] - 1, queryArr[2] - 1));
            }
        }
        return results.stream().mapToInt(Integer::intValue).toArray();
    }

    void build(int idx, int L, int R) {
        if (L == R) {
            sgt[idx] = arr[R];
            return;
        }

        int mid = (L + R) / 2;
        int lc = 2 * idx + 1;
        int rc = 2 * idx + 2;

        build(lc, L, mid);
        build(rc, mid + 1, R);

        sgt[idx] = Math.min(sgt[lc], sgt[rc]);
    }

    void update(int idx, int L, int R, int arrIdx, int arrVal) {
        if (L == R) {
            arr[arrIdx] = sgt[idx] = arrVal;
            return;
        }

        int mid = (L + R) / 2;
        int lc = 2 * idx + 1;
        int rc = 2 * idx + 2;

        if (arrIdx <= mid) {
            update(lc, L, mid, arrIdx, arrVal);
        } else {
            update(rc, mid + 1, R, arrIdx, arrVal);
        }

        sgt[idx] = Math.min(sgt[lc], sgt[rc]);
    }

    int query(int idx, int L, int R, int l, int r) {
        // No Overlap
        if (R < l || r < L) {
            return Integer.MAX_VALUE;
        }
        // Complete Overlap
        if (l <= L && R <= r) {
            return sgt[idx];
        }

        int mid = (L + R) / 2;
        int lc = 2 * idx + 1;
        int rc = 2 * idx + 2;

        int left = query(lc, L, mid, l, r);
        int right = query(rc, mid + 1, R, l, r);
        return Math.min(left, right);
    }
}
