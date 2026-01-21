import java.util.ArrayList;
import java.util.List;

public class Main {
    // Heapifying array (Min-Heap)
    // O(N * Log N) => Per index we apply a insertion/percolate up operation (Log N)
    public static void heapify(List<Integer> list) {
        int size = list.size();

        for (int i = 1; i < size; i++) {
            int parentIdx = (i - 1) / 2;
            int currIdx = i;

            while (parentIdx >= 0 && list.get(currIdx) < list.get(parentIdx)) {
                swap(list, currIdx, parentIdx);
                currIdx = parentIdx;
                parentIdx = (currIdx - 1) / 2;
            }
        }
    }

    private static void swap(List<Integer> list, int i, int j) {
        Integer temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.addAll(List.of(10, 5, 3, 7, 12, 13, 6, 2));
        heapify(list);
        for (Integer val : list) {
            System.out.print(val + " ");
        }
        System.out.println();
    }
}
