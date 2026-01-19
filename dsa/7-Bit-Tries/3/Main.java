import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static class TrieNode {
    Map<Integer, TrieNode> map = new HashMap<>();
    int freq = 0;
    int sumIndex = 0;
  }

  public int solve(List<Integer> A) {
    int MOD = 1000 * 1000 * 1000 + 7;

    TrieNode root = new TrieNode();
    int runningXor = 0;
    long cnt = 0;

    for (int i = 0; i < A.size(); i++) {
      int value = A.get(i);
      insert(root, runningXor, i);
      runningXor ^= value;
      cnt += query(root, runningXor, i);
      cnt %= MOD;
    }
    long result = cnt % MOD;
    return (int) result;
  }

  private long query(TrieNode root, int value, int index) {
    TrieNode curr = root;
    for (int i = 31; i >= 0; i--) {
      int bit = (value & (1 << i)) == 0 ? 0 : 1;
      if (!curr.map.containsKey(bit)) {
        return 0;
      }
      curr = curr.map.get(bit);
    }
    long ans = (long) ((long) curr.freq * (long) index) - (long) curr.sumIndex;
    return ans;
  }

  private TrieNode insert(TrieNode root, int value, int index) {
    if (root == null) {
      root = new TrieNode();
    }
    TrieNode curr = root;
    for (int i = 31; i >= 0; i--) {
      int bit = (value & (1 << i)) == 0 ? 0 : 1;
      if (!curr.map.containsKey(bit)) {
        curr.map.put(bit, new TrieNode());
      }
      curr = curr.map.get(bit);
    }
    curr.freq++;
    curr.sumIndex += index;
    return root;
  }

  public static void main(String[] args) {
    Main m = new Main();

    List<Integer> l = new ArrayList<>(List.of(5, 2, 7));
    System.out.println(m.solve(l));

  }
}
