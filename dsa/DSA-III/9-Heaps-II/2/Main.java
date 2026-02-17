import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * QUESTION: Given a list containing head pointers of N sorted link lists.
 * Merge these given sorted linked lists and return the single sorted linked list.
 */
public class Main {
    static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    // TC: O(M * N *  Log N), where M is the maximum length of the linked list.
    // SC: O(N) => Maximum size of heap is N.
    public ListNode mergeKLists(List<ListNode> lists) {
        if (lists == null || lists.isEmpty()) {
            return null;
        }

        // Build a min heap storing nodes based on integer value.
        Queue<ListNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
        for (ListNode node : lists) {
            if (node != null) {
                minHeap.offer(node);
            }
        }

        // If all linked lists are null, they can't be merged or are already merged.
        if (minHeap.isEmpty()) {
            return null;
        }

        // Merge all the lists by polling from the heap and getting the smallest value,
        // Then adding the next value in the list from where it was taken.
        ListNode start = minHeap.poll();
        if (start.next != null) {
            minHeap.offer(start.next);
        }
        ListNode curr = start;
        while (minHeap.size() > 0) {
            ListNode minNode = minHeap.poll();
            curr.next = minNode;
            curr = minNode;
            if (minNode.next != null) {
                minHeap.offer(minNode.next);
            }
        }

        return start;
    }

    public static void main(String[] args) {

    }
}
