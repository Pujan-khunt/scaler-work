import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QUESTION: Given an array of integers A, find and return the maximum result of
 * A[i] XOR A[j], where i, j are the indexes of the array.
 *
 */
public class Main {
  static class TrieNode {
    Map<Integer, TrieNode> map;
    boolean isEnd;

    public TrieNode() {
      this.map = new HashMap<>();
      this.isEnd = false;
    }
  }

  // TC: O(N * Log M), where N is the size of the list of integers and Log M is the number of bits per integer.
  public int solve(List<Integer> A) {
    // Create trie and insert all integers.
    TrieNode root = new TrieNode();
    for (int num : A) {
      insert(root, num);
    }

    // For every integer try to find the complement(bitwise)
    int globalMax = 0;
    for (int num : A) {
      int localMax = 0;
      TrieNode curr = root;
      for (int i = 0; i < 32; i++) {
        int digit = getIthBit(num, 31 - i);
        int complement = 1 - digit;

        if (curr.map.containsKey(complement)) {
          localMax = setIthBit(localMax, 31 - i);
          curr = curr.map.get(complement);
        } else {
          curr = curr.map.get(digit);
        }
      }
      globalMax = Math.max(globalMax, localMax);
    }

    return globalMax;
  }

  private TrieNode insert(TrieNode root, int num) {
    if (root == null) {
      root = new TrieNode();
    }
    TrieNode currNode = root;
    for (int i = 0; i < 32; i++) {
      int digit = getIthBit(num, 31 - i);
      if (!currNode.map.containsKey(digit)) {
        currNode.map.put(digit, new TrieNode());
      }
      currNode = currNode.map.get(digit);
    }
    return root;
  }

  private int getIthBit(int num, int i) {
    return (num & (1 << i)) == 0 ? 0 : 1;
  }

  private int setIthBit(int num, int i) {
    return (num | (1 << i));
  }

  public static void main(String[] args) {
    Main m = new Main();
    Integer[] arr = { 1, 2, 3, 4, 5 };
    List<Integer> list = Arrays.asList(arr);
    System.out.println(Arrays.toString(arr) + ": " + m.solve(list));

    Integer[] arr2 = { 5, 17, 100, 11 };
    List<Integer> list2 = Arrays.asList(arr2);
    System.out.println(Arrays.toString(arr2) + ": " + m.solve(list2));
  }
}
