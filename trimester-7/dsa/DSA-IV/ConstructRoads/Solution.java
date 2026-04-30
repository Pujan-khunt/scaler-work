import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {
    public int solve(int A, int[][] B) {

        long MOD = 1000000007L;

        List<List<Integer>> countryAdjacencyList = new ArrayList<>();

        for (int i = 0; i <= A; i++) {

            countryAdjacencyList.add(new ArrayList<>());
        }

        for (int[] road : B) {

            // We extract the first city structurally connected by the current road.
            int cityU = road[0];

            // We extract the exact second city structurally connected by the same road.
            int cityV = road[1];

            // Because the roads represent undirected bidirectional connections, we mathematically
            // add 'cityV' to the neighbor list of 'cityU'.
            countryAdjacencyList.get(cityU).add(cityV);

            // Symmetrically, we mathematically add 'cityU' to the completely corresponding neighbor
            // list of 'cityV'.
            countryAdjacencyList.get(cityV).add(cityU);
        }

        // We declare an array to persistently track the assigned 'color' (or set partition) of
        // every single city to ensure the bipartite property.
        // Again, we allocate 'A + 1' spaces to safely and seamlessly accommodate the 1-based
        // indexing of the cities.
        int[] cityColors = new int[A + 1];
        Arrays.fill(cityColors, -1);

        // We strictly declare a long variable to meticulously count exactly how many cities
        // ultimately belong to the first bipartite set (Set 0).
        long citiesInSetZero = 0;

        // We strictly declare a long variable to meticulously count exactly how many cities
        // ultimately belong to the second bipartite set (Set 1).
        long citiesInSetOne = 0;

        // We gracefully instantiate a LinkedList to function as our primary Queue for
        // mathematically executing the Breadth-First Search (BFS) traversal.
        Queue<Integer> bfsQueue = new LinkedList<>();

        // We confidently select city '1' to be the absolute starting point of our BFS, adding it
        // directly into the queue.
        // (A tree is definitively a fully connected graph, so any starting node will eventually
        // mathematically reach all others).
        bfsQueue.add(1);

        // We programmatically assign the starting city strictly to Set 0 by formally changing its
        // color from -1 to 0.
        cityColors[1] = 0;

        // Because we just decisively placed a city into Set 0, we increment our 'citiesInSetZero'
        // tracking counter by exactly 1.
        citiesInSetZero++;

        // We initiate a standard while-loop that will persistently execute strictly as long as our
        // BFS queue contains at least one city.
        while (!bfsQueue.isEmpty()) {

            // We systematically dequeue the city currently sitting at the absolute front of the
            // queue to process its direct neighbors.
            int currentCity = bfsQueue.poll();

            // We initiate an enhanced for-loop to iterate identically through every single
            // physically connected neighbor of the 'currentCity'.
            for (int neighborCity : countryAdjacencyList.get(currentCity)) {

                // We strictly evaluate if the neighboring city has completely avoided being visited
                // so far (its color rigidly equals -1).
                if (cityColors[neighborCity] == -1) {

                    // The core property of a valid bipartite graph heavily dictates that connected
                    // nodes MUST belong to completely opposite sets.
                    // We assign the neighbor the exact opposite color of the 'currentCity' using
                    // the simple mathematical toggle: 1 - color.
                    cityColors[neighborCity] = 1 - cityColors[currentCity];

                    // We logically evaluate if the dynamically assigned color for this specific
                    // neighbor was 0.
                    if (cityColors[neighborCity] == 0) {

                        // If it successfully was assigned to Set 0, we increment our tracking
                        // counter for Set 0.
                        citiesInSetZero++;
                    }
                    // If the color was strictly not 0, it is mathematically guaranteed to be 1.
                    else {

                        // Therefore, we meticulously increment our tracking counter exclusively
                        // designated for Set 1.
                        citiesInSetOne++;
                    }

                    // We confidently enqueue this freshly colored neighbor so that its own
                    // unvisited neighbors can be processed in subsequent iterations.
                    bfsQueue.add(neighborCity);
                }
            }
        }

        // The absolute maximum number of roads that can theoretically exist in any bipartite graph
        // is the product of the sizes of its two disjoint sets.
        // We multiply our carefully tracked counts and apply the modulo operation to rigidly
        // prevent intermediate overflow.
        long maximumPossibleRoads = (citiesInSetZero * citiesInSetOne) % MOD;

        // The problem description clearly states that the country currently forms a tree, meaning
        // it already possesses exactly (N - 1) roads.
        // We mathematically calculate the number of existing roads and apply the modulo to maintain
        // mathematical consistency.
        long currentlyExistingRoads = (A - 1) % MOD;

        // The maximum number of *new* roads the king can logically construct is strictly the
        // maximum possible roads minus the already existing roads.
        // Because modular subtraction in Java can yield a negative integer, we mathematically add
        // the MODULO_CONSTANT before applying a final modulo.
        long maximumNewRoadsToBuild = (maximumPossibleRoads - currentlyExistingRoads + MOD) % MOD;

        // We conclusively cast the firmly computed, safely modulo'd 'long' answer back down to a
        // standard 32-bit 'int' and return it to the caller.
        return (int) maximumNewRoadsToBuild;
    }
}
