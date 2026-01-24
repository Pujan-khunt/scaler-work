import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

    static class Info implements Comparable<Info> {
        int i;
        int j;
        int val;

        public Info(int i, int j, int val) {
            this.i = i;
            this.j = j;
            this.val = val;
        }

        @Override
        public int compareTo(Info other) {
            return Integer.compare(this.val, other.val);
        }
    }

    public int solve(ArrayList<ArrayList<Integer>> A, int B) {
        // Create a temporary list of all minimum values in all lists.
        List<Info> temp = new ArrayList<>();
        for (int i = 0; i < A.size(); i++) {
            temp.add(new Info(i, 0, A.get(i).get(0)));
        }

        // Create a min heap using heapification of first elements of all list in O(n) time.
        Queue<Info> minHeap = new PriorityQueue<>(temp);

        // O(B * Log B) time.
        for (int ptr = 0; ptr < B - 1; ptr++) {
            Info i = minHeap.poll();
            if (i.j + 1 < A.get(i.i).size()) {
                minHeap.offer(new Info(i.i, i.j + 1, A.get(i.i).get(i.j + 1)));
            }
        }
        return minHeap.poll().val;
    }

}
