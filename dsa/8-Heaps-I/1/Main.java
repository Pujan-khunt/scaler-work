import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
 * Given length of N.
 * import java.util.PriorityQueue;
 * The cost of merging 2 ropes is equal to the sum of of their lengths.
 * Find the minimum cost required to merge all the ropes into one rope.
 *
 * Eg. ropes = [1, 4, 2, 5], answer = 22
 * Explanation:
 * 1. Merge 2 and 1, to get cost = 3
 * 2. Merge 3 and 4 to get cost = 7
 * 3. Merge 7 and 5 to get cost = 12
 *
 * Total cost = 3 + 7 + 12 = 22
 */
public class Main {

    /**
     * The main understanding required to solve this problem is
     * to understand that the minimum cost will be obtained only when
     * the minimum and the second minimum elements are combined each time 
     * until the last element remains.
     *
     * Why and How?
     * For the testcase = [1, 4, 2, 5] the answer goes like this:
     * answer (1st step) = {1} + {2}
     * answer (2nd step) = {(1 + 2)} + {4}
     * answer (3rd step) = {(1 + 2 + 4)} + {5}
     *
     * It can be clearly seen that the elements that are picked first(1 and 2) are getting repeated the most number of times.
     * Since we want to establish the minimum cost possible, we need to ensure that these elements which are going to be repeated
     * the most amount of times are as small as possible.
     *
     * Hence we need to combine the elements which have the smallest magnitude first.
     *
     * TC: O(N * Log N)
     * SC: O(N)
     *
     * ======================================================================================
     * Mathematical Proof of why merging smaller numbers will give minimum cost by Kshitij Sir:
     * ======================================================================================
     *
     * Let there be 3 numbers a, b and c.
     * We assume the following relative ordering between them: a < b < c
     *
     * When merging them, we have 3 choices.
     * m(a, b)  => represents merging a and b giving total cost of a + b for this operation.
     * m(ab, c) => represents merging ab(merged a and b) and c giving a total cost of (a+b) + c for this operation.
     *
     * 1) Merge a and b
     * m(a, b) => a + b
     * m(ab, c) => (a + b) + c
     * Total Cost = (a + b + c) + (a + b)
     *
     * 2) Merge b and c
     * m(b, c) => b + c
     * m(bc, a) => (b + c) + a
     * Total Cost = (a + b + c) + (b + c)
     *
     * 3) Merge a and c
     * m(a, c) => c + a
     * m(ac, b) => (c + a) + b
     * Total Cost = (a + b + c) + (c + a)
     *
     *
     * It can be clearly seen that (a + b + c) is common between all of them, hence:
     *
     * Merge a and b gives the relative cost of => a + b
     * Merge b and c gives the relative cost of => b + c
     * Merge c and a gives the relative cost of => c + a
     *
     * Since b < c,
     * 0 < c - b        [When subtracting b from both sides]
     * a + b < a + c    [When adding a + b to both sides]
     * 
     * Since a < b,
     * 0 < b - a        [When subtracting a from both sides]
     * a + c < b + c    [When adding a + c to both sides]
     * 
     *
     * Therefore, the following holds true:
     * a + b < c + a < b + c
     */
    public static int getMinCost(List<Integer> ropes) {
        if (ropes == null || ropes.size() <= 1) {
            return 0;
        }

        // MinHeap to fetch the minimum and second minimum values
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Insert all values of the list to the priority queue
        for (Integer ropeLength : ropes) {
            pq.add(ropeLength);
        }

        int totalCost = 0;

        while (pq.size() != 1) {
            int minimum = pq.poll();
            int secondMinimum = pq.poll();

            int cost = minimum + secondMinimum;
            pq.add(cost);
            totalCost += cost;
        }

        return totalCost;
    }

    public static void main(String[] args) {
        List<Integer> ropes = List.of(1, 4, 2, 5);
        int cost = getMinCost(ropes);
        System.out.println(cost);
    }
}
