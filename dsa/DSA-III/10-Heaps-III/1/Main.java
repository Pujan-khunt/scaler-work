import java.util.ArrayList;
import java.util.List;

/**
 * QUESTION: Find the K smallest elements from the given list of numbers.
 */

public class Main {
    /**
     *
     * Approach 1: Sort and pick first K.
     * TC: O(N * Log N) + O(K)
     * SC: O(Log N) -> Quick Sort, O(N) -> Merge sort
     * This naive approach is to sort the entire list which takes O(N * Log N) time.
     * Then pick the first K elements.
     * 
     * Approach 2: Create min heap in O(N) time, then delete K times.
     * TC: O(N) + O(K * Log N)
     * SC: O(N) => Size of the copy array (because of no modification), otherwise SC: O(1) since the heap is the array which is given in the input.
     * Also sometimes there is a constraint of no modification to the input received like the array in this case.
     *
     * Approach 3: Create a max heap to maintain K smallest elements inside it.
     * We create a max heap of size K. 
     *
     * Que: Why max heap?
     * Ans: because we need the largest element from the K smallest elements.
     *
     * Que: Why do we need the largest element from the K smallest elements?
     * Ans: We want to check whether the `curr` element of the list belongs to the group of K smallest elements.
     * For that we need to compare the `curr` element to the largest element from the K smallest elements.
     * If `curr` is smaller than it belongs otherwise, it doesn't. Hence to find the largest element from the 
     * group of K smallest elements in the most optimal way, we create a max heap.
     * 
     * TC: O(K) {for creating the heap} + O((N - K) * Log K) = O(N * Log K)
     * SC: O(K) for heap size (since we need to copy those k elements if input modification is not allowed).
     * Otherwise SC: O(1), since no extra space is being used.
     */
    public List<Integer> findKSmallest(List<Integer> list, int k) {
        int listSize = list.size();
        if (k >= listSize) {
            return new ArrayList<>(list);
        }

        // Building the max heap of size K in O(K) time and O(K) space.
        List<Integer> maxHeap = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            maxHeap.add(list.get(i));
        }

        // Heapify the initial K elements.
        for (int i = (k / 2) - 1; i >= 0; i--) {
            percolateDown(maxHeap, i);
        }

        // Iterate through the list of numbers and keep adding numbers which have smaller value
        // than the largest value from the group of K smallest numbers. Otherwise ignore them.
        // At then end, the heap should contain the final K smallest numbers.
        for (int i = k; i < listSize; i++) {
            Integer curr = list.get(i);
            Integer currMax = maxHeap.get(0);

            // If the curr number is smaller than the than the largest  of our K smallest,
            // it deserves a spot in the heap.
            if (curr < currMax) {
                // Directly replacing the root (old largest from K smallest) with 
                maxHeap.set(0, curr);

                // Percolate down the new root to its correct position.
                percolateDown(maxHeap, 0);
            }
        }

        return maxHeap;
    }

    private void percolateDown(List<Integer> heap, int index) {
        int currIdx = index;
        int heapSize = heap.size();

        while (currIdx < heapSize) {
            int leftChildIdx = 2 * currIdx + 1;
            int rightChildIdx = 2 * currIdx + 2;
            int largestIdx = currIdx;

            if (leftChildIdx < heapSize && heap.get(leftChildIdx).compareTo(heap.get(largestIdx)) > 0) {
                largestIdx = leftChildIdx;
            }
            if (rightChildIdx < heapSize && heap.get(rightChildIdx).compareTo(heap.get(largestIdx)) > 0) {
                largestIdx = rightChildIdx;
            }

            // Heap is restored, since curr is larger than both children.
            if (largestIdx == currIdx) {
                break;
            }

            swap(heap, currIdx, largestIdx);
            currIdx = largestIdx;
        }
    }

    private void swap(List<Integer> list, int i, int j) {
        Integer temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static void main(String[] args) {

    }
}
