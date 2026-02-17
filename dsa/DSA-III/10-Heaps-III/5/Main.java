import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Que: Given an array A, representing seats in each row of a stadium.
 * You need to sell tickets to B people.
 * Each seat costs equal to the number of vacant seats in the row it belongs to.
 * The task is to maximize profit by selling the tickets to B people.
 */
public class Main {
    // NOTE: TC: O(N * Log N)
    public int profitMax(List<Integer> A, int B) {
        Collections.sort(A, Collections.reverseOrder());
        int ans = 0, i = 0;
        while (B-- > 0) {
            ans += A.get(i);
            A.set(0, A.get(i) - 1);
            if (i + 1 < A.size() && A.get(i) < A.get(i + 1)) {
                i++;
            }
        }
        return ans;
    }

    public int profitMax2(List<Integer> A, int B) {
        int n = A.size();
        List<Integer> maxHeap = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            maxHeap.add(A.get(i));
        }

        // Heapify the array.
        for (int i = (n / 2) - 1; i >= 0; i--) {
            percolateDown(maxHeap, i);
        }

        int ans = 0;
        while (B-- > 0) {
            int profit = maxHeap.get(0);
            ans += profit;

            if (profit == 0) {
                break;
            }

            maxHeap.set(0, profit - 1);
            percolateDown(maxHeap, 0);
        }
        return ans;
    }

    private void percolateDown(List<Integer> heap, int index) {
        int currIdx = index;
        int heapSize = heap.size();

        while (true) {
            int leftChildIdx = 2 * currIdx + 1;
            int rightChildIdx = 2 * currIdx + 2;
            int largestIdx = currIdx;

            if (leftChildIdx < heapSize && heap.get(leftChildIdx).compareTo(heap.get(largestIdx)) > 0) {
                largestIdx = leftChildIdx;
            }
            if (rightChildIdx < heapSize && heap.get(rightChildIdx).compareTo(heap.get(largestIdx)) > 0) {
                largestIdx = rightChildIdx;
            }

            if (largestIdx == currIdx) {
                break;
            }

            Integer temp = heap.get(largestIdx);
            heap.set(largestIdx, heap.get(currIdx));
            heap.set(currIdx, temp);

            currIdx = largestIdx;
        }
    }

    public static void main(String[] args) {

    }
}
