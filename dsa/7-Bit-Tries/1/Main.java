import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QUESTION: Given an array, A of integers of size N. Find the subarray AL,
 * AL+1, AL+2, ... AR with 1<=L<=R<=N, which has maximum XOR value.
 *
 * NOTE: If there are multiple subarrays with the same maximum value, return the
 * subarray with minimum length. If the length is the same, return the subarray
 * with the minimum starting index.
 */
public class Main {
  public static class TrieNode {
    Map<Integer, TrieNode> map = new HashMap<>();
    int index = -1;
  }

  public ArrayList<Integer> solve(ArrayList<Integer> A) {
    // Build prefix xor array.
    List<Integer> prefixXor = new ArrayList<>();
    prefixXor.add(0);
    int runningXor = 0;
    for (int val : A) {
      runningXor ^= val;
      prefixXor.add(runningXor);
    }

    // Initialize Trie and insert the initial '0' value which corresponds to index -1
    TrieNode root = new TrieNode();
    insert(root, 0, -1);

    int maxXor = Integer.MIN_VALUE;
    int minLen = Integer.MAX_VALUE;
    int start = -1, end = -1;

    for (int i = 0; i < A.size(); i++) {
      int currentVal = prefixXor.get(i + 1);

      // Find the best index 'j' already in the Trie that maximizes (currentVal ^ prefixXor[j])
      int matchedIndex = query(root, currentVal);
      // Retrieve the value of prefixXor[j] from the prefixXor list.
      int matchedVal = prefixXor.get(matchedIndex + 1);
      // Calculate the maximized value of (currentVal ^ prefixXor[j])
      int currentXor = currentVal ^ matchedVal;

      // Define the subarray starting and ending points.
      // The subarray is defined from [matchedIndex + 1, i]
      int startIdx = matchedIndex + 1;
      int endIdx = i;
      int len = endIdx - startIdx + 1;

      // Update global answers if local answer is larger
      if (currentXor > maxXor) {
        maxXor = currentXor;
        start = startIdx;
        end = endIdx;
        minLen = len;
      }
      // If the local and global answers are the same,
      // then rank according to the one with minimum length
      else if (currentXor == maxXor) {
        if (len < minLen) {
          start = startIdx;
          end = endIdx;
          minLen = len;
        }
      }

      // Insert the currentVal into Trie for future iterations.
      insert(root, currentVal, i);
    }

    ArrayList<Integer> ansList = new ArrayList<>();
    ansList.add(start + 1);
    ansList.add(end + 1);
    return ansList;
  }

  // It finds the maximum possible complement of "value" and returns its index
  // from the prefixXor list.
  private int query(TrieNode root, int value) {
    TrieNode curr = root;
    for (int i = 31; i >= 0; i--) {
      int bit = getIthBit(value, i);
      int complement = 1 - bit;

      // If we can move towards the complement,
      // then do it (XOR will be 1 at this bit).
      if (curr.map.containsKey(complement)) {
        curr = curr.map.get(complement);
      } else {
        // Otherwise go towards the same bit (XOR will be 0 at this bit).
        curr = curr.map.get(bit);
      }
    }
    // Return index stored at the leaf node.
    return curr.index;
  }

  // Inserts the value and its index in prefixXor list in the Trie.
  // It stores the index in the leaf node.
  private void insert(TrieNode root, int value, int index) {
    TrieNode curr = root;
    for (int i = 31; i >= 0; i--) {
      int bit = getIthBit(value, i);
      if (!curr.map.containsKey(bit)) {
        curr.map.put(bit, new TrieNode());
      }
      curr = curr.map.get(bit);
    }
    curr.index = index;
  }

  private int getIthBit(int value, int i) {
    return (value & (1 << i)) == 0 ? 0 : 1;
  }

  public static void main(String[] args) {
    Main m = new Main();

    ArrayList<Integer> list = new ArrayList<>(List.of(15, 25, 23));
    ArrayList<Integer> ans = m.solve(list);
    System.out.println(ans.toString());

    list = new ArrayList<>(List.of(8));
    ans = m.solve(list);
    System.out.println(ans.toString());
  }
}
