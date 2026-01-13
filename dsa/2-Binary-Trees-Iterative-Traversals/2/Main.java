import java.util.ArrayList;

/**
 * QUESTION: Given the inorder and postorder traversal of a tree, construct the
 * binary tree.
 * NOTE: You may assume that duplicates do not exist in the tree.
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

  public static TreeNode helper(ArrayList<Integer> inorder, ArrayList<Integer> postorder, int inStart, int inEnd,
      int postStart, int postEnd) {
    if (inStart > inEnd || postStart > postEnd) {
      return null;
    }

    int rootVal = postorder.get(postEnd);
    TreeNode rootNode = new TreeNode(rootVal);

    int index = -1;
    for (int i = inStart; i <= inEnd; i++) {
      if (inorder.get(i) == rootVal) {
        index = i;
        break;
      }
    }

    rootNode.left = helper(inorder, postorder, inStart, index - 1, postStart, postStart + index - inStart - 1);
    rootNode.right = helper(inorder, postorder, index + 1, inEnd, postStart + index - inStart, postEnd - 1);

    return rootNode;
  }

  public static TreeNode buildTree(ArrayList<Integer> A, ArrayList<Integer> B) {
    return helper(A, B, 0, A.size() - 1, 0, B.size() - 1);
  }

  public static void main(String[] args) {
    ArrayList<Integer> postorder = new ArrayList<>();
    postorder.add(2);
    postorder.add(3);
    postorder.add(1);

    ArrayList<Integer> inorder = new ArrayList<>();
    inorder.add(2);
    inorder.add(1);
    inorder.add(3);

    TreeNode root = buildTree(inorder, postorder);
    System.out.println("Root value: " + root.val);
    System.out.println("Left child value: " + root.left.val);
    System.out.println("Right child value: " + root.right.val);

  }
}
