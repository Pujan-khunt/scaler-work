import java.util.Arrays;

public class BinaryUpdates {
    int[] sgt;
    int n;
    int[] arr;

    public int[] solve(int length, int[][] B) {
        int[] A = new int[length];
        Arrays.fill(A, 1);
        this.n = A.length;
        this.arr = A;
        this.sgt = new int[4 * n];
        build(0, n - 1, 0);
        int len = 0, j = 0;
        for (int[] query : B)
            len += query[0] == 1 ? 1 : 0;
        int[] results = new int[len];
        for (int[] query : B) {
            switch (query[0]) {
                case 0:
                    update(0, n - 1, 0, query[1] - 1);
                    break;
                case 1:
                    // Verify existence of y'th one.
                    // Subtract 1 from query index to convert to 0-based indexing.
                    // Add 1 in final result to convert 0-based result into 1-based.
                    results[j++] =
                            query[1] - 1 >= sgt[0] ? -1 : queryFn(0, n - 1, 0, query[1] - 1) + 1;
            }
            break;
        }
        return results;
    }


    int queryFn(int L, int R, int idx, int y) {
        if (L == R) {
            // Since L and R represent the indices in the original array
            // We can return 'L' to get the index of the 'y'th one in the original array.
            return L;
        }
        int m = (L + R) / 2;
        int lc = 2 * idx + 1, rc = 2 * idx + 2;
        int leftVal = sgt[lc];

        if (y >= leftVal) {
            return queryFn(m + 1, R, rc, y - leftVal);
        }
        return queryFn(L, m, lc, y);
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

    void update(int L, int R, int idx, int newidx) {
        if (L == R) {
            sgt[idx] = 0;
            return;
        }
        int m = (L + R) / 2;
        int lc = 2 * idx + 1, rc = 2 * idx + 2;
        if (newidx <= m) {
            update(L, m, lc, newidx);
        } else {
            update(m + 1, R, rc, newidx);
        }
        sgt[idx] = sgt[lc] + sgt[rc];
    }
}
