import java.util.ArrayList;

/**
 * QUESTION: Given a binary tree, return the inorder traversal of its nodes
 * values.
 *
 * NOTE: Using recursion and stack are not allowed.
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

  // NOTE: Morris Inorder Traversal is an algorithm for traversing a tree by
  // utilizing
  // the free right pointers of the leaf nodes in O(N) time and O(1) space.
  public ArrayList<Integer> solve(TreeNode A) {
    ArrayList<Integer> inorder = new ArrayList<>();
    TreeNode curr = A;

    // Iterate until you cover all nodes.
    // At the end curr will point to the right of the rightmost leaf node i.e. null.
    while (curr != null) {
      if (curr.left == null) {
        // If there doesn't exist any LST, then we need to proceed to the RST.
        // NOTE: But before proceeding to the RST, we need to process the root node
        // first.
        // Hence we first include the processsing step here and then proceed on moving
        // right.
        inorder.add(curr.val); // Processing step.
        curr = curr.right; // Moving right step.
      } else {
        // Find the predecessor of curr.
        // i.e. the rightmost node in the LST of curr.
        // We do this because of the above check of (curr.left != null) we have
        // established that the LST exists and in the inorder traversal you first need
        // to process the leftmost node first and you can't proceed before processing
        // the leftmost node, but if we just directly move towards the left node, we
        // lose the pointer/access to this current node that we need to come back to,
        // for that reason, we find the predecessor of curr and link it back to the curr
        // node and hence we have a path to visit it back to finish the rest of the
        // tree.
        TreeNode predecessor = curr.left;

        // Loop until the rightmost node's right pointer is null i.e. you reach the
        // rightmost leaf node.
        // Or until you reach the node which has its right pointer linked to the curr
        // node. (This is a link that is created later by us to move to the curr node).
        while (predecessor.right != null && predecessor.right != curr) {
          predecessor = predecessor.right;
        }

        // If there is no link established, it means that we are visiting the LST for
        // the first time.
        // This means we need to create the link for future use.
        // After creating the link we can safely move the curr pointer to the left and
        // can be assured that the link we just created will help us in future to travel
        // back to the node we left.
        if (predecessor.right == null) {
          predecessor.right = curr;
          curr = curr.left;
        }

        // If the link has already been created it means that we have already covered
        // the entire LST and we have looped back again, so now we can do the following:
        // 1. Remove the link to restore the tree.
        // 2. Process the curr node, since the LST has already been processed, (inorder
        // = LST - Root - RST)
        // 3. Move towards the RST, since the Root and LST have been entirely processed.
        if (predecessor.right == curr) {
          predecessor.right = null; // Remove the link step.
          inorder.add(curr.val); // Processing step.
          curr = curr.right; // Move to RST step.
        }
      }
    }

    return inorder;
  }

  public static void main(String[] args) {

  }
}
