import java.util.ArrayList;
import java.util.Arrays;

public class Solution {

    private final int INF = 1000000000;

    // A: Total vertices
    // B: Edge list
    // C: Source
    // D: Destination
    public int solve(int A, ArrayList<ArrayList<Integer>> B, int C, int D) {
        // Keeps track of the shortest distances from source C to every other
        // node in the graph. A + 1 for keeping 1-based indices.
        int[] shortestDistances = new int[A + 1];

        // Currently every vertex is unreachable from source.
        Arrays.fill(shortestDistances, INF);

        // Distance from source to source = 0
        shortestDistances[C] = 0;

        // The core geometric property of the Bellman-Ford algorithm dictates that the shortest path
        // in a simple graph (no cycles) can possess at most 'A - 1' edges.
        // Therefore, we meticulously loop exactly 'A - 1' times to ensure all possible shortest
        // paths are fully discovered and relaxed.
        for (int iteration = 1; iteration <= A - 1; iteration++) {
            // During each discrete iteration, we systematically evaluate every single directed edge
            // currently present in the provided list 'B'.
            for (ArrayList<Integer> edge : B) {

                int sourceNode = edge.get(0);
                int destinationNode = edge.get(1);
                int edgeWeight = edge.get(2);

                // Verify if local source node is reachable from 'C' (global source)
                if (shortestDistances[sourceNode] != INF) {
                    // New Distance = C -> local-source + local-source -> local-destination
                    int proposedDistance = shortestDistances[sourceNode] + edgeWeight;

                    // Update shortest distance to local-destination if less than
                    // current best distance.
                    if (proposedDistance < shortestDistances[destinationNode]) {
                        shortestDistances[destinationNode] = proposedDistance;
                    }
                }
            }
        }

        // After rigidly performing 'A - 1' complete graph relaxations, all valid non-cyclic
        // shortest paths must logically be fully established.
        // If we can successfully relax ANY edge even one more time, it mathematically proves the
        // undeniable existence of an infinite negative weight cycle.
        for (ArrayList<Integer> edge : B) {
            int sourceNode = edge.get(0);
            int destinationNode = edge.get(1);
            int edgeWeight = edge.get(2);

            if (shortestDistances[sourceNode] != INF) {
                int proposedDistance = shortestDistances[sourceNode] + edgeWeight;
                if (proposedDistance < shortestDistances[destinationNode]) {
                    // Proves the existence of negative weight cycle.
                    return INF;
                }
            }
        }

        // Finally, we rigorously evaluate the finalized optimal distance recorded specifically for
        // our required destination vertex 'D'.
        // If the distance remains identically equal to our initialized 'INFINITY', it conclusively
        // proves absolutely no valid path exists connecting 'C' to 'D'.
        if (shortestDistances[D] == INF) {

            // We rigorously return the explicitly requested error code of 10^9 to mathematically
            // signify unreachability.
            return INF;
        }

        // If a completely valid, finite, and optimally shortest path was successfully established,
        // we confidently return its definitive minimum cost.
        return shortestDistances[D];
    }
}
