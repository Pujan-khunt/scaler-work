import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.xml.internal.utils.Trie;

/**
 * TrieNode implementation
 */
public class Main {

  static class TrieNode {
    Map<Character, TrieNode> map;
    boolean isEnd;

    public TrieNode(Map<Character, TrieNode> map, boolean isEnd) {
      this.map = map;
      this.isEnd = isEnd;
    }

    public TrieNode() {
      this.map = new HashMap<>();
      this.isEnd = false;
    }
  }

  // TC: O(L), where L is the length of the string
  public TrieNode insert(TrieNode root, String word) {
    if (root == null) {
      return new TrieNode();
    }

    TrieNode currNode = root;

    for (int i = 0; i < word.length(); i++) {
      char currChar = word.charAt(i);
      // 1. Check if the character exists.
      if (!currNode.map.containsKey(currChar)) {
        // 2. If not, then create it.
        currNode.map.put(currChar, new TrieNode());
      }
      // 3. Move to that node.
      currNode = currNode.map.get(currChar);
    }
    // 4. Mark word as completed.
    currNode.isEnd = true;

    return root;
  }

  public TrieNode insert2(TrieNode root, String word) {
    if (root == null) {
      return new TrieNode();
    }
    TrieNode currNode = root;
    for (char currChar : word.toCharArray()) {
      currNode = currNode.map.computeIfAbsent(currChar, k -> new TrieNode());
    }
    currNode.isEnd = true;
    return root;
  }

  // TC: O(L)
  public boolean search(TrieNode root, String word) {
    if (root == null) {
      return false;
    }

    TrieNode currNode = root;
    for (int i = 0; i < word.length(); i++) {
      char currChar = word.charAt(i);
      if (!currNode.map.containsKey(currChar)) {
        return false;
      }
      currNode = currNode.map.get(currChar);
    }

    // Also need to confirm that the last empty node has isEnd = true which says if
    // the word exists or not.
    return currNode.isEnd;
  }

  public static void main(String[] args) {

  }
}
