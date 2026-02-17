import java.util.List;

public class Main {
    public static void main(String[] args) {
        Heap<Integer> minHeap = new Heap<>();
        List<Integer> list = List.of(1, 4, 2, 6, 3, 9, 7, 8, 5);

        for (Integer val : list) {
            minHeap.insert(val);
        }

        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.delete() + " ");
        }
        System.out.println();
    }
}
