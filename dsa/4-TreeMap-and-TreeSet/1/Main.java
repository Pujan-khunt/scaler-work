/**
 * QUESTION: Given a complete binary tree, count the number of nodes.
 *
 * NOTE: All levels of a complete binary tree are full except possibly the last
 * level where all nodes must be left aligned.
 */
public class Main {
  static class TreeNode {
    int val;
    TreeNode left, right;

    public TreeNode(int x) {
      val = x;
      left = right = null;
    }
  }

  // Use dfs (postorder) to recursively count number of nodes.
  // TC: O(N) => All nodes need to be visited exactly once.
  // SC: O(N) => Maximum size of recursion stack is N in the case of a skewed
  // tree.
  public int countNodesInCompleteBT(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int leftCnt = countNodesInCompleteBT(root.left);
    int rightCnt = countNodesInCompleteBT(root.right);

    return leftCnt + rightCnt + 1;
  }

  private int getRSTDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int depth = 1;
    while (root.right != null) {
      root = root.right;
      depth++;
    }
    return depth;
  }

  private int getLSTDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int depth = 1;
    while (root.left != null) {
      root = root.left;
      depth++;
    }
    return depth;
  }

  // This approach utilizes the property of complete binary trees.
  // The property is that atleast one side either LST or RST must be completely
  // full
  // and the other will be partiall full. We can recursively run on both subtrees
  // but can be assured
  // that exactly one of them will take O(1) time to finish since it will be
  // completely full, the left and right depth of it will be the same and hence it
  // could be directly calculated using mathematics.
  // TC: O(Log N * Log N)
  // SC: O(Log N)
  public int countNodesInCompletBT2(TreeNode root) {
    if (root == null) {
      return 0;
    }

    // TC: O(Log N)
    // SC: O(Log N)
    int leftDepth = getLSTDepth(root);
    int rightDepth = getRSTDepth(root);

    if (leftDepth == rightDepth) {
      return (1 << leftDepth) - 1;
    }
    return 1 + countNodesInCompletBT2(root.left) + countNodesInCompletBT2(root.right);
  }

  public static void main(String[] args) {

  }
}
