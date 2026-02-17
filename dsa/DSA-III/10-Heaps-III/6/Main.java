import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Que: Given 2 arrays. Print k smallest sum pairs.
 *
 * TestCase:
 * A = [2, 5, 8, 11, 13]
 * B = [3, 7, 9, 12, 15, 16, 20]
 * K = 5
 *
 * Answer: 
 * 2, 3
 * 5, 3
 * 2, 7
 * 2, 9
 * 8, 3
 */
public class Main {
    static class Info implements Comparable<Info> {
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
            return Integer.compare(this.sum, other.sum);
        }

        // This method needs to be overriden and implemented inorder for the hashset to work properly.
        // When the hashset checks if a provided object exists within it, it uses the equals method, and if not provided
        // it would compare the memory location of both, which can be different but the objects can be same according to logic.
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Info other = (Info) obj;
            return this.a_idx == other.a_idx && this.b_idx == other.b_idx;
        }

        // hashCode is required because when hashset adds a pojo within it, it uses memory location to determine the hashCode.
        // Since the 2 objects which are same according to your logic, have different memory locations will end up having different hashcodes.
        // So in a case where an object is already within the hashset, when queried to check if it exists, would be checked in a different bucket
        // since the hashcode would be different and hence would gives us a wrong answer of false that it doesn't.
        @Override
        public int hashCode() {
            return Objects.hash(this.a_idx, this.b_idx);
        }
    }

    // NOTE: TC = O(N * M * Log (N * M))
    public List<List<Integer>> bruteforce(List<Integer> A, List<Integer> B, int k) {
        List<Info> list = new ArrayList<>();
        for (int i = 0; i < A.size(); i++) {
            for (int j = 0; j < B.size(); j++) {
                list.add(new Info(i, j, A.get(i) + B.get(j)));
            }
        }

        Collections.sort(list, (a, b) -> Integer.compare(a.sum, b.sum));

        List<List<Integer>> ansList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            List<Integer> temp = new ArrayList<>();
            Info info = list.get(i);
            temp.add(info.a_idx);
            temp.add(info.b_idx);

            ansList.add(temp);
        }

        return ansList;
    }

    public List<List<Integer>> optimal(List<Integer> A, List<Integer> B, int k) {
        Set<Info> visitedSet = new HashSet<>();
        Queue<Info> minHeap = new PriorityQueue<>();
        minHeap.add(new Info(0, 0, A.get(0) + B.get(0)));

        List<List<Integer>> ansList = new ArrayList<>();
        while (k-- > 0) {
            Info info = minHeap.poll();
            List<Integer> temp = new ArrayList<>();
            temp.add(info.a_idx);
            temp.add(info.b_idx);
            ansList.add(temp);

            if (info.a_idx + 1 < A.size()) {
                Info ch1 = new Info(info.a_idx + 1, info.b_idx, A.get(info.a_idx + 1) + A.get(info.b_idx));
                if (!visitedSet.contains(ch1)) {
                    minHeap.offer(ch1);
                    visitedSet.add(ch1);
                }
            }

            if (info.b_idx + 1 < B.size()) {
                Info ch2 = new Info(info.a_idx, info.b_idx + 1, A.get(info.a_idx) + B.get(info.b_idx + 1));
                if (!visitedSet.contains(ch2)) {
                    minHeap.offer(ch2);
                    visitedSet.add(ch2);
                }
            }
        }

        return ansList;
    }

    public static void main(String[] args) {
        List<Integer> A = List.of(2, 5, 8, 11, 13);
        List<Integer> B = List.of(3, 7, 9, 12, 15, 16, 20);
        Main m = new Main();
        List<List<Integer>> ans = m.optimal(A, B, 5);
        for (List<Integer> val : ans) {
            System.out.println(A.get(val.get(0)) + ", " + B.get(val.get(1)));
        }
    }
}
