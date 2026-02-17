import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * QUESTION: Given N bags, each bag contains Bi chocolates. There is a kid and a magician.
 * In a unit of time, the kid can choose any bag i, and eat Bi chocolates from it, then the magician will fill the ith bag with floor(Bi/2) chocolates.
 * Find the maximum number of chocolates that the kid can eat in A units of time.
 *
 * NOTE: floor() function returns the largest integer less than or equal to a given number.
 * Return your answer modulo 1e9+7
 */
public class Main {
    public int nchoc(int A, List<Integer> B) {
        int MOD = 1000 * 1000 * 1000 + 7;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (Integer value : B) {
            maxHeap.offer(value);
        }

        long totalChocolates = 0;
        while (A-- > 0 && !maxHeap.isEmpty()) {
            int chocolates = maxHeap.poll();
            totalChocolates = (totalChocolates + chocolates) % MOD;
            int newChocolateQty = (int) Math.floor(chocolates / 2.0);
            if (newChocolateQty != 0) {
                maxHeap.offer(newChocolateQty);
            }
        }

        return (int) totalChocolates;
    }

    public static void main(String[] args) {

    }
}
