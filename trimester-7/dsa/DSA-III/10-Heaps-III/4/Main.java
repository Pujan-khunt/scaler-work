import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Que: Given a stream of integers. With every new integer. Return the median.
 */
public class Main {
    public List<Integer> runningMedian(List<Integer> list) {
        Queue<Integer> minHeap = new PriorityQueue<>();
        Queue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        List<Integer> ansList = new ArrayList<>();
        for (Integer val : list) {
            maxHeap.offer(val);
            minHeap.offer(maxHeap.poll());
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
            ansList.add(maxHeap.peek());
        }

        return ansList;
    }

    public static void main(String[] args) {
    }
}
