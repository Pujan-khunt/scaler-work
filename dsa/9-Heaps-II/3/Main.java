import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * QUESTION: Given an integer array B of size N.
 * You need to find the Ath largest element in the subarray [1 to i], where i varies from 1 to N.
 * In other words, find the Ath largest element in the sub-arrays [1 : 1], [1 : 2], [1 : 3], ...., [1 : N].
 *
 * NOTE: If any subarray [1 : i] has less than A elements, then the output should be -1 at the ith index.
 */
public class Main {
    /**
     * Time Complexity: {@code O(N Log N)}
     */
    public static List<Integer> solve(int k, List<Integer> list) {
        List<Integer> ansList = new ArrayList<>();
        Queue<Integer> minHeap = new PriorityQueue<>();

        // For the subarrays from [1:1], [1:2], ... [1:A-1] will have answer = -1
        // since there are no 'A' number of elements to find the Ath largest element from.
        // 
        // Then for the subarray [1:A], the answer would be the root node of the min heap.
        // Because the min heap of A nodes, will have the root node as the smallest value
        // and the Ath largest value in a tree of A values is the smallest value.
        // The smallest value can be obtained from the root of the min heap.
        for (int i = 0; i < k; i++) {
            minHeap.offer(list.get(i));

            // Enough elements to find the Ath largest.
            // Ath largest would be the minimum of the A values
            // which will be at the root of the min heap.
            if (i == k - 1) {
                ansList.add(minHeap.peek());
            }
            // Not enough elements to find Ath largest.
            else {
                ansList.add(-1);
            }
        }

        // Build the heap such that it contains the A largest values 
        // and the root node contains the minimum of those A largest values.
        // Essentially the root node will have the Ath largest value, 
        // since there are exactly A nodes in the heap.
        for (int i = k; i < list.size(); i++) {
            int curr = list.get(i);

            // If the current element is greater than the A largest values, then it must be placed in the heap
            // and the smallest value must be removed from the tree (root node).
            if (curr > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(curr);
            }

            // Otherwise, ignore the curr element, since it doesn't contribute towards the answer.

            // Get the A'th largest value from the heap and add it to the answer list.
            ansList.add(minHeap.peek());
        }

        return ansList;
    }

    public static void main(String[] args) {

    }
}
