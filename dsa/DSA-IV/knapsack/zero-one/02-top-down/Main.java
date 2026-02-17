import java.util.ArrayList;
import java.util.List;

public class Main {

    public int knapSack(List<Integer> weights, List<Integer> happiness, int capacity) {
        List<List<Integer>> cakes = new ArrayList<>();
        int n = weights.size();
        for (int i = 0; i < n; i++) {
            List<Integer> temp = new ArrayList<>();
            temp.add(weights.get(i));
            temp.add(happiness.get(i));
            cakes.add(temp);
        }
        // dp[i][j] = represents the maximum happiness in the bag of capacity 'j'
        // with access to elements from index '0' to index 'i'.
        int[][] dp = new int[n][capacity + 1];
        return helper(cakes, n - 1, capacity, dp);
    }

    private int helper(List<List<Integer>> cakes, int i, int capacity, int[][] dp) {
        if (capacity == 0) {
            return 0;
        }
        if (i == 0) {
            // If we can consume the 0'th cake, then consume it.
            if (cakes.get(0).get(0) <= capacity) {
                return cakes.get(0).get(1);
            }
            // else, don't.
            return 0;
        }

        // If eligible to consume the full i'th cake, then choose one which offers more happiness.
        if (cakes.get(i).get(0) <= capacity) {
            int consumeCake = cakes.get(i).get(1) + helper(cakes, i - 1, capacity - cakes.get(i).get(0), dp);
            int skipCake = helper(cakes, i - 1, capacity, dp);
            return dp[i][capacity] = Math.max(consumeCake, skipCake);
        }
        // If not eligible to consume the full i'th cake, then ignore it.
        return dp[i][capacity] = helper(cakes, i - 1, capacity, dp);
    }

    public static void main(String[] args) {

    }
}
