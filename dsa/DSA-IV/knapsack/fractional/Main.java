/*
 *
 * Fractional KnapSack:
 *
 * You have N elements where each element has a weight and a value associated to it.
 * You also have a bag of capacity C.
 * The goal is to maximize/minimize the value in the bag while keeping the total
 * weight of the bag <= its capacity C.
 *
 * The thing which makes it 'fractional' is that the element/item can be divided into per unit weight.
 *
 * Que: You go to a cake shop with a bag of weight capacity C. You can choose from upto N cakes.
 * Each cake has a weight 'w' and a happiness value 'h' associated to it. Find the maximum happiness
 * that you can put into the bag.
 *
 * NOTE: The cakes can be divided into per unit weight.
 *
 * TC:
 * H = [3, 8, 10, 2, 5]
 * W = [10, 4, 20, 8, 15]
 * H/w = 0.3, 2, 0.5, 0.25, 0.33
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public double knapSack(
        List<Integer> weight,
        List<Integer> happiness,
        int capacity
    ) {
        List<List<Integer>> cakes = new ArrayList<>();
        int n = weight.size();
        for (int i = 0; i < n; i++) {
            List<Integer> temp = new ArrayList<>();
            temp.add(weight.get(i));
            temp.add(happiness.get(i));
            cakes.add(temp);
        }

        Collections.sort(cakes, (a, b) -> {
            double t1 = a.get(1) / (double) a.get(0);
            double t2 = b.get(1) / (double) b.get(0);
            return Double.compare(t2, t1);
        });

        int bag = 0,
            i = 0;
        double happy = 0;

        // If the bag can handle the full cake
        while (bag + cakes.get(i).get(0) <= capacity) {
            // Then consume the entire cake.
            bag += cakes.get(i).get(0);
            happy += cakes.get(i).get(1);

            // Move onto the next cake.
            i++;
        }
        // If all the cakes have not yet been consumed and we still have non-zero capacity left to fill the bag, only then add the fractional cake.
        if (i < n && bag < capacity) {
            happy +=
                (capacity - bag) *
                ((double) cakes.get(i).get(1) / cakes.get(i).get(0));
        }
        return happy;
    }

    public static void main(String[] args) {
        Main solver = new Main();

        // Test Case from comments
        List<Integer> H = List.of(3, 8, 10, 2, 5);
        List<Integer> W = List.of(10, 4, 20, 8, 15);
        int C = 40; // Example capacity

        // Note: With C=40, we expect:
        // 1. Item index 1 (H=8, W=4, Ratio=2.0) -> Take all. Cap=36. Val=8.
        // 2. Item index 2 (H=10, W=20, Ratio=0.5) -> Take all. Cap=16. Val=18.
        // 3. Item index 4 (H=5, W=15, Ratio=0.33) -> Take all. Cap=1. Val=23.
        // 4. Item index 0 (H=3, W=10, Ratio=0.30) -> Take 1/10th. Val=23 + 0.3 = 23.3.

        double result = solver.knapSack(W, H, C);
        System.out.println("Maximum Happiness: " + result);
    }
}
