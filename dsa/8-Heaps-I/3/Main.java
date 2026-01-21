import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * QUESTION: Find the 'k' most frequent elements in the list of 'values'.
 *
 * Application of this question could be find the 'k' most frequent error codes in the logs
 * or find the k most frequently used hashtags to show to the user etc.
 *
 * These objects can be anything like error codes or hashtags or product ids, hence
 * they are being represented as an Generic 'T'.
 */
public class Main {
  public static class Info<T> {
    T obj;
    int freq;

    public Info(T obj, int freq) {
      this.obj = obj;
      this.freq = freq;
    }
  }

  public static class MinHeapComparator<T> implements Comparator<Info<T>> {
    @Override
    public int compare(Info<T> a, Info<T> b) {
      return Integer.compare(a.freq, b.freq);
    }
  }

  // TC: O(N * Log K)
  // O(N): Building frequency map (hashmap)
  // O(N * Log K): Inserting map entries and removing as size > k
  // O(K * Log K): Removing the answers (most frequent k elements)
  // SC: O(k) -> the PriorityQueue itself will carry 'k' items at the worst
  public static <T> List<T> topKFrequentElements(List<T> values, int k) {
    // Building the frequency map of objects in the list of values.
    Map<T, Integer> freqMap = new HashMap<>();
    for (T obj : values) {
      if (!freqMap.containsKey(obj)) {
        freqMap.put(obj, 1);
      } else {
        int prevValue = freqMap.get(obj);
        freqMap.put(obj, prevValue + 1);
      }
    }

    // Build a min-heap which will limited to a maximum size of 'k',
    // and at the end it will contain the 'k' most frequent values.
    PriorityQueue<Info<T>> minHeap = new PriorityQueue<>(new MinHeapComparator<T>());

    // Keep adding objects to the min heap.
    // If the size of the minHeap exceeds k, then remove the top most element (representing the object with minimum frequency)
    for (Map.Entry<T, Integer> entry : freqMap.entrySet()) {
      T obj = entry.getKey();
      int freq = entry.getValue();
      minHeap.add(new Info<T>(obj, freq));

      if (minHeap.size() > k) {
        minHeap.poll();
      }
    }

    // Pull off the remaining 'k' elements with maximum frequency.
    List<T> ansList = new ArrayList<>();
    while (minHeap.size() > 0) {
      T obj = minHeap.poll().obj;
      ansList.add(obj);
    }

    return ansList;
  }

  // Much more concise answer.
  public static <T> List<T> topKFrequentElements2(List<T> values, int k) {
    Map<T, Integer> freqMap = new HashMap<>();
    for (T val : values) {
      freqMap.put(val, freqMap.getOrDefault(val, 0) + 1);
    }

    PriorityQueue<Map.Entry<T, Integer>> minHeap = new PriorityQueue<>((a, b) -> {
      return a.getValue() - b.getValue();
    });

    for (Map.Entry<T, Integer> entry : freqMap.entrySet()) {
      minHeap.add(entry);

      if (minHeap.size() > k) {
        minHeap.poll();
      }
    }

    List<T> ansList = new ArrayList<>();
    while (minHeap.size() > 0) {
      ansList.add(minHeap.poll().getKey());
    }
    return ansList;
  }

  public static void main(String[] args) {
    List<Integer> nums = List.of(1, 1, 1, 2, 2, 3);
    int k = 2;
    List<Integer> ansList = topKFrequentElements(nums, k);
    for (Integer ans : ansList) {
      System.out.print(ans + " ");
    }
    System.out.println();
  }
}
