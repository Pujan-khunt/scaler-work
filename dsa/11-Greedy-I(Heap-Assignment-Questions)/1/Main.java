import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Que: Given two integers, A and B, of size N each.
 * Find the maximum N elements from the sum combinations (A[i] + B[j]) formed from the elements
 * in arrays A and B.
 */
public class Main {

    private static class Info implements Comparable<Info> {
        int a_idx;
        int b_idx;
        int sum;

        public Info(int a_idx, int b_idx, int sum) {
            this.a_idx = a_idx;
            this.b_idx = b_idx;
            this.sum = sum;
        }

        @Override
        public int compareTo(Info other) {
            return Integer.compare(other.sum, this.sum);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Info other = (Info) obj;
            return this.a_idx == other.a_idx && this.b_idx == other.b_idx;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.a_idx, this.b_idx);
        }
    }

    public ArrayList<Integer> solve(List<Integer> A, List<Integer> B) {
        Collections.sort(A, Collections.reverseOrder());
        Collections.sort(B, Collections.reverseOrder());

        Queue<Info> maxHeap = new PriorityQueue<>();
        ArrayList<Integer> ansList = new ArrayList<>();
        Set<Info> visitedSet = new HashSet<>();
        maxHeap.offer(new Info(0, 0, A.get(0) + B.get(0)));

        for (int j = 0; j < A.size(); j++) {
            Info i = maxHeap.poll();
            ansList.add(i.sum);

            if (i.a_idx + 1 < A.size()) {
                Info ch = new Info(i.a_idx + 1, i.b_idx, A.get(i.a_idx + 1) + B.get(i.b_idx));
                if (!visitedSet.contains(ch)) {
                    visitedSet.add(ch);
                    maxHeap.offer(ch);
                }
            }

            if (i.b_idx + 1 < B.size()) {
                Info ch = new Info(i.a_idx, i.b_idx + 1, A.get(i.a_idx) + B.get(i.b_idx + 1));
                if (!visitedSet.contains(ch)) {
                    visitedSet.add(ch);
                    maxHeap.offer(ch);
                }
            }
        }

        return ansList;
    }

}
