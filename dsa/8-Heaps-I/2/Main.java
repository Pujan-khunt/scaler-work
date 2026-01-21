import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * QUESTION: Find the K closest points to origin. Imagine you are building a
 * food delivery app. You have a list of restaurants represented by their X and
 * Y coordinates on a map (assuming you are at location [0,0]). You need to find
 * the k closest restaurants to show the user.
 *
 * Input: points: A 2D array where points[i] = [x, y] represents a restaurant's
 * location k: the number of closest restaurants to return.
 */
public class Main {

  private static double getDistanceFromOrigin(List<Integer> point) {
    int x = point.get(0);
    int y = point.get(1);
    return Math.sqrt(x * x + y * y);
  }

  public static class Info {
    List<Integer> point;
    double distanceFromOrigin;

    public Info(List<Integer> point, double distanceFromOrigin) {
      this.point = point;
      this.distanceFromOrigin = distanceFromOrigin;
    }
  }

  public static class MinDistanceComparator implements Comparator<Info> {
    @Override
    public int compare(Info a, Info b) {
      return (a.distanceFromOrigin - b.distanceFromOrigin) > 0 ? 1 : -1;
    }
  }

  // TC: O(N * Log N) which can be further improved.
  // What we did here is just a heap sort and collected the first K elements.
  // We could have used any sorting algorithm for this.
  // It can definitely be improved.
  public static List<List<Integer>> findKClosestPoints(List<List<Integer>> points, int k) {
    // Create a min-heap based on the distanceFromOrigin field.
    PriorityQueue<Info> pq = new PriorityQueue<>(new MinDistanceComparator());

    // Insert all <point, distanceFromOrigin> pairs into the heap.
    for (List<Integer> point : points) {
      double distanceFromOrigin = getDistanceFromOrigin(point);
      pq.add(new Info(point, distanceFromOrigin));
    }

    List<List<Integer>> kClosestPointsList = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      List<Integer> point = pq.poll().point;
      kClosestPointsList.add(point);
    }

    return kClosestPointsList;
  }

  public static class MaxDistanceComparator implements Comparator<Info> {
    @Override
    public int compare(Info a, Info b) {
      return Double.compare(b.distanceFromOrigin, a.distanceFromOrigin);
    }
  }

  // TC: O(N * Log K)
  // Since the maximum size of the heap is K, insertion and deletion cost O(Log k) at worst per operation.
  // And we added N elements and removed N - K elements from the heap and then again K elements from the heap,
  // so the TC becomes: O(N * Log K) + O((N - K) * Log K) + O (K * Log K)
  // which is essentially O(N * Log N)
  public static List<List<Integer>> findKClosestPointsOptimized(List<List<Integer>> points, int k) {
    // Instead of creating a min-heap, we now create a max heap.
    PriorityQueue<Info> pq = new PriorityQueue<>(new MaxDistanceComparator());

    // Another way of creating the same PriorityQueue would be:
    // PriorityQueue<Info> maxHeap = new PriorityQueue<>((a, b) -> {
    //   int distA = a.point.get(0) * a.point.get(0) + a.point.get(1) * a.point.get(1);
    //   int distB = b.point.get(0) * b.point.get(0) + b.point.get(1) * b.point.get(1);
    //   return Integer.compare(distB, distA);
    // });

    for (List<Integer> point : points) {
      // Maintain the size of the heap to be K.
      // At the end only the K minimum elements remain, since we popped off the Top N-K elements using pq.poll N - K times.
      if (pq.size() >= k) {
        pq.poll();
      }
      double distanceFromOrigin = getDistanceFromOrigin(point);
      pq.add(new Info(point, distanceFromOrigin));
    }

    // Pull the remaining K closest points (K Info objects which have minimum distance from the origin)
    List<List<Integer>> ansList = new ArrayList<>();
    while (pq.size() > 0) {
      List<Integer> closestPoint = pq.poll().point;
      ansList.add(closestPoint);
    }

    return ansList;
  }

  public static void main(String[] args) {
    List<List<Integer>> points = new ArrayList<>();
    points.add(List.of(1, 3));
    points.add(List.of(-2, 2));
    int k = 1;
    List<List<Integer>> answers = findKClosestPointsOptimized(points, k);
    for (List<Integer> answer : answers) {
      System.out.println("X: " + answer.get(0) + ", Y: " + answer.get(1));
    }
  }
}
