import java.util.ArrayList;
import java.util.List;

/**
 * Heap is an implementation of the {@code Heap} data structure using an {@code ArrayList}.
 */
public class Heap<T extends Comparable<T>> {
    private List<T> data;

    public Heap() {
        this.data = new ArrayList<>();
    }

    /**
     * Accepts a list and heapifies it in O(N)
     *
     * How is it O(N)?
     * 1. It assumes that all the leaf nodes (N/2) are already min heaps of size 1.
     * 2. Then starting from the last non-leaf node and travelling backwards till the root node, percolate down.
     *
     * This will ensure that maximum number of nodes(leaf nodes) need 0 steps to percolate down.
     * And the root node which is only a single node needs log N steps to percolate down.
     * Hence the TC will be linear.
     *
     * Mathematical Proof:
     * N/2 nodes -> 0 steps (swaps)
     * N/4 nodes -> 1 step
     * N/8 nodes -> 2 step
     * ...
     *
     * Total Steps = (N/2) * 0 + (N/4) * 1 + (N/8) * 2 + (N/16) * 3 + ...
     * This series is arithmetico-geometric series, which can be solved using shift and subtract method.
     *
     * Assume:
     *
     * S   = (N/2) * [ 1/2 + 2/4 + 3/8 + 4/16 + 5/32 + ... ]   - {1}
     * S/2 = (N/2) * [ 0   + 1/4 + 2/8 + 3/16 + 4/32 + ... ]   - {2}
     *
     * Subtract {2} from {1}
     *
     * S/2 = (N/2) * [1/2 + 1/4 + 1/8 + 1/16 + 1/32 + ...]
     *
     * Using the formula of Sum = a / (1 - r) for an infinite series.
     *
     * S = N * (0.5 / (1 - 0.5))
     *
     * S = N * (0.5 / 0.5)
     * 
     * S = N
     *
     * Hence TC: O(N)
     */
    public Heap(List<T> inputs) {
        this.data = new ArrayList<>(inputs);

        // Start from the last non-leaf node and walk backwards towards the root.
        for (int i = (this.data.size() / 2) - 1; i >= 0; i--) {
            sinkDown(i);
        }
    }

    /**
     * Moves the node at index 'i' DOWN the tree until it satisfies the heap property.
     */
    private void sinkDown(int index) {
        int listSize = this.data.size();
        int currIdx = index;

        while (true) {
            int leftChildIdx = 2 * currIdx + 1;
            int rightChildIdx = 2 * currIdx + 2;
            int smallestIdx = currIdx; // Assume current node is smallest initially.

            // Check if left child is smaller than current node, if it is valid.
            if (leftChildIdx < listSize && this.data.get(leftChildIdx).compareTo(this.data.get(smallestIdx)) < 0) {
                smallestIdx = leftChildIdx;
            }
            // Check if the right child is smaller than current node, if it is valid.
            if (rightChildIdx < listSize && this.data.get(rightChildIdx).compareTo(this.data.get(smallestIdx)) < 0) {
                smallestIdx = rightChildIdx;
            }

            // If the smallest out of curr, left and right child remains curr, means the heap is restored.
            if (smallestIdx == currIdx) {
                break;
            }

            // Otherwise, swap and continue sinking.
            swap(smallestIdx, currIdx);
            currIdx = smallestIdx;
        }
    }

    /**
     * Inserts the element in the heap.
     *
     * @param val The value to be inserted in the heap.
     *
     * @implNote
     * 1. Insert the value at the end of the list. <br/>
     * 2. Percolate up (Heapify from bottom to top), bringing the inserted element at its correct position.
     */
    public void insert(T val) {
        this.data.add(val);
        swimUp(this.data.size() - 1);
    }

    private void swimUp(int index) {
        int currIdx = index;

        while (currIdx > 0) {
            int parentIdx = (currIdx - 1) / 2;

            if (this.data.get(currIdx).compareTo(this.data.get(parentIdx)) < 0) {
                swap(currIdx, parentIdx);
                currIdx = parentIdx;
            } else {
                break;
            }
        }
    }

    /**
     * Swaps 2 elements in the heap when provided with their indexes.
     */
    private void swap(int i, int j) {
        T temp = this.data.get(i);
        this.data.set(i, this.data.get(j));
        this.data.set(j, temp);
    }

    public T peek() {
        return this.data.isEmpty() ? null : this.data.get(0);
    }

    public T delete() {
        if (this.data.isEmpty()) {
            return null;
        }

        // Save the root, to return it later.
        // Now the root is marked as removed.
        T rootValue = this.data.get(0);

        // Delete if only 1 element, else update root to last and sink down from root.
        int lastIdx = this.data.size() - 1;
        if (lastIdx > 0) {
            // Move last element to the root.
            T lastVal = this.data.remove(lastIdx);
            this.data.set(lastIdx, lastVal);

            // Fix the order starting from the root.
            sinkDown(0);
        } else {
            this.data.remove(0);
        }

        return rootValue;
    }

    public boolean isEmpty() {
        return this.data.size() == 0;
    }
}
