import java.util.ArrayList;
import java.util.List;

public class Main {
    // Heapifying array (Min-Heap)
    // O(N * Log N) => Per index we apply a insertion/percolate up operation (Log N)
    public static void heapify(List<Integer> list) {
        int size = list.size();

        for(int i = 1; i < size; i++) {
            swimUp(list, i);
        }
    }

    private static void swimUp(List<Integer> list, int index) {
        int currIdx = index;

        while(currIdx > 0) {
            int parentIdx = (currIdx - 1) / 2;

            if(list.get(currIdx).compareTo(list.get(parentIdx)) < 0) {
                swap(list, currIdx, parentIdx);
                currIdx = parentIdx;
            } else {
                break;
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
