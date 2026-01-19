import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * QUESTION: Given a list of N words, find the shortest unique prefix to
 * represent each word in the list.
 *
 * NOTE: Assume that no word is the prefix of another. In other words, the
 * representation is always possible
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

  public ArrayList<String> prefix(ArrayList<String> A) {
    ArrayList<String> prefixes = new ArrayList<>();

    // Create a trie and insert all words.
    TrieNode root = new TrieNode();
    for (String word : A) {
      insert(root, word);
    }

    // Now finding the prefixes.
    for (String word : A) {
      TrieNode currNode = root;

      // This pointer will point to the last TrieNode whose map has multiple
      // characters.
      // By doing this we can just know that the prefix will be from the root to this
      // pointer,
      // because behind this pointer everything is a prefix and it appears in multiple
      // words hence the behind part can't be chosen as the unique prefix.
      TrieNode pointer = null;

      // Loop to find the last TrieNode whose map has multiple characters.
      for (char currChar : word.toCharArray()) {
        currNode = currNode.map.get(currChar);
        if (currNode.map.size() > 1) {
          pointer = currNode;
        }
      }

      // Iterate from root to pointer and that is the shortest unique prefix.
      if (pointer != null) {
        StringBuilder sb = new StringBuilder();
        TrieNode temp = root;
        for (int i = 0; i < word.length(); i++) {
          char currChar = word.charAt(i);
          sb.append(currChar);
          temp = temp.map.get(currChar);
          if (temp == pointer) {
            sb.append(word.charAt(i + 1));
            break;
          }
        }
        prefixes.add(sb.toString());
      }
      // If no TrieNode is found whose map has multiple characters means there is no
      // word starting with the same first letter hence the first letter itself is the
      // shortest unique prefix.
      else {
        prefixes.add("" + word.charAt(0));
      }
    }

    return prefixes;
  }

  private TrieNode insert(TrieNode root, String word) {
    if (root == null) {
      root = new TrieNode();
    }
    TrieNode currNode = root;
    for (char currChar : word.toCharArray()) {
      currNode = currNode.map.computeIfAbsent(currChar, k -> new TrieNode());
    }
    currNode.isEnd = true;
    return currNode;
  }

  static class TrieNode2 {
    Map<Character, TrieNode2> map;
    boolean isEnd;
    int frequency;

    public TrieNode2() {
      this.map = new HashMap<>();
      this.isEnd = false;
      this.frequency = 0;
    }

    public TrieNode2(int frequency) {
      this.map = new HashMap<>();
      this.isEnd = false;
      this.frequency = frequency;
    }
  }

  public ArrayList<String> prefix2(ArrayList<String> A) {
    ArrayList<String> prefixes = new ArrayList<>();
    TrieNode2 root = new TrieNode2();

    // Create a Trie and insert all words along with their frequencies.
    for (String word : A) {
      insert2(root, word);
    }

    for (String word : A) {
      TrieNode2 currNode = root;
      int i = 0;
      StringBuilder sb = new StringBuilder();

      for (char currChar : word.toCharArray()) {
        sb.append(currChar);
        currNode = currNode.map.get(currChar);
        if (currNode.frequency == 1) {
          prefixes.add(sb.toString());
          break;
        }
      }

    }

    return prefixes;
  }

  private TrieNode2 insert2(TrieNode2 root, String word) {
    if (root == null) {
      root = new TrieNode2();
    }
    // No need to compute the frequencies for the root node, since its going to be
    // equal to the word count.
    TrieNode2 currNode = root;
    for (char currChar : word.toCharArray()) {
      if (!currNode.map.containsKey(currChar)) {
        currNode.map.put(currChar, new TrieNode2());
      }
      currNode = currNode.map.get(currChar);
      currNode.frequency++;
    }
    currNode.isEnd = true;
    return root;
  }

  public static void main(String[] args) {
    Main m = new Main();
    ArrayList<String> list = new ArrayList<>();
    list.add("dog");
    list.add("duck");
    list.add("dove");
    list.add("zebra");
    ArrayList<String> list2 = m.prefix2(list);
    System.out.println(list2.toString());
  }
}
