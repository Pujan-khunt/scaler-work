import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * QUESTION: Given a BST, 2 nodes have been swapped by mistake. Find those
 * values. A solution using O(n) space is straightforward. Could you devise a
 * constant space solution?
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

  // M.I.T (Morris Inorder Traversal)
  // In MIT, the node is "visited" right before you proceed to the right child of
  // a node. This happens exactly at 2 places:
  // 1. `curr.left == null` It means there is no left child to process and the
  // curr can be processed directly, before moving right.
  // 2. `temp.right == curr` Here the temporary link is cut to process the RST of
  // curr, meaning the `curr` node can be processed.
  public static ArrayList<Integer> recoverTree2(TreeNode root) {
    TreeNode curr = root, prev = null;
    TreeNode first = null, second = null;

    while (curr != null) {
      if (curr.left == null) {

        // Processing start
        if (prev != null && prev.val > curr.val) {
          if (first == null) {
            first = prev;
          }
          second = curr;
        }
        prev = curr;
        // Processing end

        curr = curr.right;
      } else {
        // Find the predecessor to the current node.
        // i.e. the rightmost node in the left subtree
        TreeNode temp = curr.left;
        while (temp.right != null && temp.right != curr) {
          temp = temp.right;
        }

        // Since the nodes haven't been linked, it means that the LST of curr hasn't
        // been visited yet.
        // Create the link to have a path to the current node. Its the equivalent of
        // pushing curr on the stack in the previous approach.
        if (temp.right == null) {
          temp.right = curr;
          curr = curr.left;
        }
        // If the link has already been established then it means that LST of curr has
        // been fully visited.
        // Now the link needs to be broken to restore the tree and the RST needs to
        // start getting processed.
        else if (temp.right == curr) {
          // Processing start
          if (prev != null && prev.val > curr.val) {
            if (first == null) {
              first = prev;
            }
            second = curr;
          }
          prev = curr;
          // Processing end

          temp.right = null;
          curr = curr.right;
        }
      }
    }

    ArrayList<Integer> ansList = new ArrayList<>();
    ansList.add(second.val);
    ansList.add(first.val);
    return ansList;
  }

  public static ArrayList<Integer> recoverTree(TreeNode root) {
    List<Integer> inorder = new ArrayList<>();
    Stack<TreeNode> backtrack = new Stack<>();
    TreeNode curr = root;

    while (!backtrack.isEmpty() || curr != null) {
      while (curr != null) {
        backtrack.push(curr);
        curr = curr.left;
      }

      curr = backtrack.pop();
      inorder.add(curr.val);
      curr = curr.right;
    }

    // If consecutive elements are swapped, there will be only inconsistency in the
    // sorted order.
    // Else there will be 2.
    boolean again = false;
    int x = -1, y = -1;

    for (int i = 1; i < inorder.size(); i++) {
      if (inorder.get(i - 1) > inorder.get(i)) {
        if (again) {
          y = inorder.get(i);
        } else {
          x = inorder.get(i - 1);
          y = inorder.get(i);
          again = true;
        }
      }
    }

    // Smaller first, then larger
    ArrayList<Integer> ansList = new ArrayList<>();
    ansList.add(y);
    ansList.add(x);
    return ansList;
  }

  public static void main(String[] args) {

  }
}
