import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * Same question as the fractional knapsack. except the part where you would divide the cake into
     * fractions. In 0/1 knapsack you can either take the item entirely and reject it entirely, there
     * is no middle ground.
     *
     * Here the greedy approach fails.
     */
    public int knapSack(List<Integer> weights, List<Integer> happiness, int capacity) {
        List<List<Integer>> cakes = new ArrayList<>();
        int n = weights.size();
        for (int i = 0; i < n; i++) {
            List<Integer> temp = new ArrayList<>();
            temp.add(weights.get(i));
            temp.add(happiness.get(i));
            cakes.add(temp);
        }
        return helper(cakes, n - 1, capacity);
    }

    private int helper(List<List<Integer>> list, int idx, int capacity) {
        if (capacity == 0) {
            return 0;
        }
        if (idx == 0) {
            if (list.get(0).get(0) <= capacity) {
                return list.get(0).get(1);
            }
            return 0;
        }

        if (list.get(idx).get(0) <= capacity) {
            return Math.max(list.get(idx).get(1) + helper(list, idx - 1, capacity - list.get(idx).get(0)),
                    helper(list, idx - 1, capacity));
        }
        return helper(list, idx - 1, capacity);
    }

    public static void main(String[] args) {

    }
}
