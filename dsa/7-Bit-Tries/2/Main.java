import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static class TrieNode {
    Map<Character, TrieNode> map;
    boolean isEnd;

    public TrieNode() {
      this.map = new HashMap<>();
      this.isEnd = false;
    }
  }

  public String solve(List<String> A, List<String> B) {
    // Create a Trie and insert all the words in the dictionary.
    TrieNode root = new TrieNode();
    for (String str : A) {
      insert(root, str);
    }

    StringBuilder sb = new StringBuilder();
    for (String str : B) {
      boolean possible = dfs(root, str, 1, 0);
      sb.append(possible ? "1" : "0");
    }

    return sb.toString();
  }

  private boolean dfs(TrieNode root, String word, int flag, int index) {
    if (index == word.length()) {
      return flag == 0 && root.isEnd;
    }
    if (flag < 0) {
      return false;
    }

    char ch = word.charAt(index);

    // No more mistakes are accepted now.
    if (flag == 0) {
      if (root.map.containsKey(ch)) {
        if (dfs(root.map.get(ch), word, flag, index + 1)) {
          return true;
        }
      }
    }
    // Can afford to make mistakes.
    else {
      for (char letter : root.map.keySet()) {
        if (dfs(root.map.get(letter), word, letter == word.charAt(index) ? flag : flag - 1, index + 1)) {
          return true;
        }
      }
    }
    // Necessary to be false
    // Will only reach here if all of the dfs function calls returns false in the else block above.
    return false;
  }

  private TrieNode insert(TrieNode root, String word) {
    if (root == null) {
      root = new TrieNode();
    }
    TrieNode curr = root;
    for (char ch : word.toCharArray()) {
      if (!curr.map.containsKey(ch)) {
        curr.map.put(ch, new TrieNode());
      }
      curr = curr.map.get(ch);
    }
    curr.isEnd = true;
    return root;
  }

  public static void main(String[] args) {
    Main m = new Main();

    List<String> A2 = new ArrayList<>(List.of("hello", "world"));
    List<String> B2 = new ArrayList<>(List.of("hella", "pello", "pella"));
    String str2 = m.solve(A2, B2);
    System.out.println("Answer: " + str2);

    List<String> A = new ArrayList<>(List.of("data", "circle", "cricket"));
    List<String> B = new ArrayList<>(List.of("date", "circel", "crikket", "data", "circl"));
    String str = m.solve(A, B);
    System.out.println("Answer: " + str);
  }
}
