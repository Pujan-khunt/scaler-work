import java.util.List;

public class Main {

    public int knapSack(List<Integer> weights, List<Integer> values, int capacity) {
        int n = weights.size();
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else {
                    if (weights.get(i - 1) <= capacity) {
                    }
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {

    }
}
