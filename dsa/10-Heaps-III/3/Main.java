import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Que: Given a nearly sorted array. Sort it. The array is K-sorted.
 * Which means that every element is atmost 'K' positions from its correct position.
 *
 * Testcase: Arr = [6, 5, 3, 2, 8, 10, 9], K = 3
 */
public class Main {
    // TC: O(K) for building the heap + O(N * Log K) for sliding window iteration = O(N * Log K)
    // SC: O(K) for the size of the heap.
    public void sortKsorted(int[] arr, int k) {
        List<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < k + 1; i++) {
            tempList.add(arr[i]);
        }

        // Create min heap in O(K) time for the first K + 1 elements.
        Queue<Integer> minHeap = new PriorityQueue<Integer>(tempList);
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            arr[i] = minHeap.poll().intValue();
            if (i + k + 1 < n) {
                minHeap.offer(arr[i + k + 1]);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = { 6, 5, 3, 2, 9, 10, 8 };
        Main m = new Main();
        m.sortKsorted(arr, 3);
        System.out.println(Arrays.toString(arr));
    }
}
