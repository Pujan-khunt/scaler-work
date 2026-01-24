/**
 * Que: K closest points to origin.
 */
public class Main {

    /**
     * TC: O(B * Log B) for creating heap + O((N - B) * Log B) = O(N Log B)
     *
     * Can be slightly improved time wise (not time complexity wise) if you heapify the first B elements,
     * rather than inserting them. This can be done by creating a separate class which is an alias for the
     * ArrayList<Integer> which implements the Comparable and has a compareTo function with the max heap logic
     * and then heapifies the first B elements by providing it in the constructor of PriorityQueue.
     */
    public ArrayList<ArrayList<Integer>> solve(ArrayList<ArrayList<Integer>> A, int B) {
        Queue<ArrayList<Integer>> maxHeap = new PriorityQueue<>((a, b) -> {
            int Ax = a.get(0), Ay = a.get(1);
            int Bx = b.get(0), By = b.get(1);

            return Integer.compare(Bx * Bx + By * By, Ax * Ax + Ay * Ay);
        });
        for(int i = 0; i < B; i++) {
            maxHeap.offer(A.get(i));
        }

        for(int i = B; i < A.size(); i++) {
            int Ax = A.get(i).get(0);
            int Ay = A.get(i).get(1);
            int distA = Ax * Ax + Ay * Ay;
            
            int Hx = maxHeap.peek().get(0);
            int Hy = maxHeap.peek().get(1);
            int distH = Hx * Hx + Hy * Hy;

            if(distA < distH) {
                maxHeap.poll();
                maxHeap.offer(A.get(i));
            }
        }

        ArrayList<ArrayList<Integer>> ansList = new ArrayList<>();
        while(!maxHeap.isEmpty()) {
            ansList.add(maxHeap.poll());
        }
        return ansList;
    }
}

