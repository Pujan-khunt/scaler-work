import java.util.ArrayList;
import java.util.List;

/**
 * QUESTION: You are given a Binary Tree A with N nodes. Write a function that
 * returns the size of the largest subtree, which is also a Binary Search Tree
 * (BST). If the complete Binary Tree is BST, then return the size of the whole
 * tree.
 *
 * NOTE: The largest subtree is the subtree with the most number of nodes.
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

  static class Data {
    boolean isBST;
    int size;

    public Data(boolean isBST, int size) {
      this.isBST = isBST;
      this.size = size;
    }
  }

  // To verify if a given tree is a BST, we can use the property of BST,
  // which says that the inorder traversal of a BST will be in sorted order.
  public int solve(TreeNode A) {
    Data rootData = isBST(A);
    if (rootData.isBST) {
      return rootData.size;
    }
    return helper(A.left, A.right);
  }

  private int helper(TreeNode A, TreeNode B) {
    Data data_a = isBST(A);
    Data data_b = isBST(B);

    if (data_a.isBST && data_b.isBST) {
      return Math.max(data_a.size, data_b.size);
    }
    if (data_a.isBST) {
      return data_a.size;
    }
    if (data_b.isBST) {
      return data_b.size;
    }

    return Math.max(helper(A.left, A.right), helper(B.left, B.right));
  }

  private Data isBST(TreeNode root) {
    List<Integer> inorder = getInorderTraversal(root);
    int size = inorder.size();

    for (int i = 1; i < inorder.size(); i++) {
      if (inorder.get(i - 1) > inorder.get(i)) {
        return new Data(false, -1);
      }
    }
    return new Data(true, size);
  }

  private List<Integer> getInorderTraversal(TreeNode root) {
    List<Integer> inorder = new ArrayList<>();
    TreeNode curr = root;

    while (curr != null) {
      if (curr.left == null) {
        // (Processing)
        inorder.add(curr.val);

        curr = curr.right;
      } else {
        // Get the rightmost element of the LST of curr.
        TreeNode temp = curr.left;
        while (temp.right != curr && temp.right != null) {
          temp = temp.right;
        }

        if (temp.right == null) {
          temp.right = curr;
          curr = curr.left;
        }
        if (temp.right == curr) {
          // (Processing)
          inorder.add(curr.val);

          temp.right = null;
          curr = curr.right;
        }
      }

    }

    return inorder;
  }

  // The second approach is also a bruteforce.
  // Find the maximum value in the LST and the minimum value in the RST.
  // if the following order maintains, then the tree is a valid BST:
  // (max of LST) <= value of Root <= (minimum value of RST)
  public int solve2(TreeNode A) {
    return 0;
  }

  public static class TreeData {
    boolean isBST;
    int size, min, max;

    public TreeData(boolean isBST, int size, int min, int max) {
      this.isBST = isBST;
      this.size = size;
      this.min = min;
      this.max = max;
    }
  }

  // In this approach we will build the solution from the bottom up.
  // We will ask each subtree to return the following:
  // 1. boolean representing if its a valid BST.
  // 2. size of the subtree
  // 3. minimum value of the subtree
  // 4. maximum value of the subtree
  // Use postorder traversal and fetch these data values from both subtree
  // and then use both data values to send the data value for the current larger
  // subtree above to the parent.
  // This will recursively determine at the end.
  public int solve3(TreeNode root) {
    TreeData rootData = helper2(root);
    return rootData.size;
  }

  public TreeData helper2(TreeNode root) {
    // Base case: Null nodes are BSTs by definition
    // Use Infinity to ensure they never fail the comparison check
    if (root == null) {
      return new TreeData(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    TreeData left = helper2(root.left);
    TreeData right = helper2(root.right);

    // A node is a BST ONLY if:
    // 1. Both children are BSTs
    // 2. Its value is > max of left subtree
    // 3. Its value is < min of right subtree
    if (left.isBST && right.isBST && root.val > left.max && root.val < right.min) {
      int currentSize = 1 + left.size + right.size;
      // Update min/max. Handle leaf case by using root.val if child is null
      int currentMin = Math.min(root.val, left.min);
      int currentMax = Math.max(root.val, right.max);

      return new TreeData(true, currentSize, currentMin, currentMax);
    }

    // If it's NOT a BST, return the max size found in subtrees
    // min/max don't matter anymore because isBST is false
    return new TreeData(false, Math.max(left.size, right.size), 0, 0);
  }

  public TreeData helper(TreeNode root) {
    // The maximum and minimum integer values are chosen so that the logic works
    // even on nodes with negative numbers. This will ensure that root.val will
    // always be greater than max of its left child and lesser than min of its right
    // child.
    if (root == null) {
      return new TreeData(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    TreeData left = helper(root.left);
    TreeData right = helper(root.right);

    // Return the current node if both children are null.
    if (left.size == 0 && right.size == 0) {
      return new TreeData(true, 1, root.val, root.val);
    }

    if (left.size == 0) {
      if (root.val <= right.min && right.isBST) {
        return new TreeData(true, 1 + right.size, root.val, right.max);
      } else {
        return new TreeData(false, right.size, -1, -1);
      }
    }

    if (right.size == 0) {
      if (left.max <= root.val && left.isBST) {
        return new TreeData(true, 1 + left.size, left.min, root.val);
      } else {
        return new TreeData(false, left.size, -1, -1);
      }
    }

    if (left.isBST && right.isBST && left.max <= root.val && root.val <= right.min) {
      return new TreeData(true, 1 + left.size + right.size, left.min, right.max);
    } else {
      return new TreeData(false, Math.max(left.size, right.size), -1, -1);
    }
  }

  public static void main(String[] args) {
  }
}
