import java.util.Arrays;

/**
 * Que: Find the 'K' largest elements in an array.
 */
public class Main {

    // TC: O(N Log K)
    public int[] findKLargest(int[] arr, int k) {
        int arrSize = arr.length;
        if (k >= arrSize) {
            return Arrays.copyOf(arr, arrSize);
        }

        // Copy the first 'K' elements of the array.
        int[] minHeap = new int[k];
        for (int i = 0; i < k; i++) {
            minHeap[i] = arr[i];
        }

        // Heapify in O(K) time.
        for (int i = (k / 2) - 1; i >= 0; i--) {
            percolateDown(minHeap, i);
        }

        // For every element beyond K to n, check if it deserves a place in the
        // heap. i.e. it is greater than the minimum of the heap.
        for (int i = k; i < arrSize; i++) {
            int curr = arr[i];
            int minimumFromHeap = minHeap[0];

            if (curr > minimumFromHeap) {
                minHeap[0] = curr;
                percolateDown(minHeap, 0);
            }
        }

        return minHeap;
    }

    private void percolateDown(int[] arr, int index) {
        int currIdx = index;
        int arrSize = arr.length;

        while (true) {
            int leftChildIdx = 2 * currIdx + 1;
            int rightChildIdx = 2 * currIdx + 2;
            int smallestIdx = currIdx; // Assuming curr is the smallest out of left, right and curr.

            // Check if left child is smaller than curr.
            if (isValidIdx(leftChildIdx, arrSize) && arr[leftChildIdx] < arr[smallestIdx]) {
                smallestIdx = leftChildIdx;
            }
            // Check if the right child is smaller than curr.
            if (isValidIdx(rightChildIdx, arrSize) && arr[rightChildIdx] < arr[smallestIdx]) {
                smallestIdx = rightChildIdx;
            }

            // If curr remains to be the smallest, means the heap is restored.
            if (currIdx == smallestIdx) {
                break;
            }

            // Otherwise swap curr with smaller element to not violate min heap property.
            swap(arr, currIdx, smallestIdx);
            // Keep going down, until the heap is restored.
            currIdx = smallestIdx;
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private boolean isValidIdx(int index, int size) {
        return index < size;
    }

    public static void main(String[] args) {

    }
}
