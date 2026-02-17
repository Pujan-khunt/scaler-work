import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * QUESTION: Given a binary matrix. Find the number of unique rows.
 *
 * NOTE: the rows and columns of the matrix can be of size 10^5
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

  public ArrayList<ArrayList<Integer>> uniqueRow(int arr[][], int rows, int cols) {
    ArrayList<ArrayList<Integer>> ans = new ArrayList<>();

    // Create a Trie and insert all rows inside it.
    TrieNode root = new TrieNode();
    for (int[] row : arr) {
      insert(root, row);
    }

    for (int[] row : arr) {
      TrieNode currNode = root;
      // For every row, get the last empty trie node.
      for (int i = 0; i < row.length; i++) {
        currNode = currNode.map.get(row[i]);
      }
      // If the isEnd = true, meaning it hasn't been added to the list.
      // Add to the list and set it to false, to prevent adding it again.
      if (currNode.isEnd) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int element : row) {
          temp.add(element);
        }
        ans.add(temp);
        currNode.isEnd = false;
      }
    }

    return ans;
  }

  private TrieNode insert(TrieNode root, int[] row) {
    if (root == null) {
      root = new TrieNode();
    }
    TrieNode curr = root;
    for (int i = 0; i < row.length; i++) {
      if (!curr.map.containsKey(row[i])) {
        curr.map.put(row[i], new TrieNode());
      }
      curr = curr.map.get(row[i]);
    }
    curr.isEnd = true;
    return root;
  }

  public static void main(String[] args) {
    Main m = new Main();
    int[][] arr = { { 1, 1, 0, 1 }, { 1, 0, 0, 1 }, { 1, 1, 0, 1 } };
    int rows = arr.length;
    int cols = arr[0].length;
    ArrayList<ArrayList<Integer>> list = m.uniqueRow(arr, rows, cols);
    for (ArrayList<Integer> temp : list) {
      System.out.println(temp.toString());
    }
  }
}
