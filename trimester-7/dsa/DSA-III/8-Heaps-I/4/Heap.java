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
     * Inserts the element in the heap.
     *
     * @param val The value to be inserted in the heap.
     *
     * @implNote
     * 1. Insert the value at the end of the list. <br/>
     * 2. Percolate up (Heapify from bottom to top), bringing the inserted element at its correct position.
     */
    public void insert(T val) {
        // Insert the value at the last index.
        this.data.add(val);

        // Bring the last index to its correct position.
        int currIdx = this.data.size() - 1;

        while (currIdx > 0) {
            int parentIdx = (currIdx - 1) / 2;

            if (this.data.get(currIdx).compareTo(this.data.get(parentIdx)) < 0) {
                // Bring val one step closer to its correct position.
                swap(currIdx, parentIdx);

                // Move up the tree.
                currIdx = parentIdx;
            } else {
                // The heap property is satisfied and `val` has reached its correct position.
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
        if (this.data.isEmpty()) {
            return null;
        }
        return this.data.get(0);
    }

    public T delete() {
        if (this.data.isEmpty()) {
            return null;
        }

        // Save the root, to return it later.
        // Now the root is marked as removed.
        T rootValue = this.data.get(0);

        // Edge case: If only 1 element, then remove and return it.
        if (this.data.size() == 1) {
            return this.data.removeLast();
        }

        // Replace root with last element.
        T lastVal = this.data.removeLast();
        this.data.set(0, lastVal);

        // Start from root and percolate down (heapify from top to bottom)
        int currIdx = 0;
        int listSize = this.data.size();

        while (true) {
            int leftChildIdx = currIdx * 2 + 1;
            int rightChildIdx = currIdx * 2 + 2;
            int smallestIdx = currIdx; // Assume current node is smallest initially.

            // Check if left child is smaller than current node.
            if (leftChildIdx < listSize && this.data.get(leftChildIdx).compareTo(this.data.get(smallestIdx)) < 0) {
                smallestIdx = leftChildIdx;
            }
            // Check if the right child is smaller than current node.
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

        return rootValue;
    }

    public boolean isEmpty() {
        return this.data.size() == 0;
    }
}
