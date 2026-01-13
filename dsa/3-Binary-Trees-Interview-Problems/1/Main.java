/**
 * QUESTION: Given a binary tree where each node has an extra next pointer,
 * populate each node's next pointer to its next right node. If there is no next
 * right node, the next pointer should be set to null.
 *
 * NOTE: You may only use constant space.
 *
 * NOTE: 1. that using recursion has memory overhead and does not qualify for
 * constant space.
 * NOTE: 2. The tree need not be a perfect binary tree.
 */
public class Main {
  static class TreeLinkNode {
    int val;
    TreeLinkNode left, right, next;

    public TreeLinkNode(int x) {
      val = x;
      left = right = next = null;
    }
  }

  public void connect(TreeLinkNode root) {
  }

  public static void main(String[] args) {

  }
}
