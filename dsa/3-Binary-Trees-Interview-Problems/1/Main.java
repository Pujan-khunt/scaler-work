import java.util.LinkedList;
import java.util.Queue;

import com.sun.source.tree.Tree;

import Main.TreeNode;

/**
 * QUESTION: Given a binary tree where each node has an extra next pointer,
 * populate each node's next pointer to its next right node. If there is no next
 * right node, the next pointer should be set to null.
 *
 * NOTE: You may only use constant space.
 *
 * NOTE: 1. that using recursion has memory overhead and does not qualify for
 * constant space. NOTE: 2. The tree need not be a perfect binary tree.
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

  // This method uses level order traversal and stores
  // all the nodes in the same level and then manually iterates
  // over them and sets the right pointer to the next right node level by level.
  // TC: O(N) => need to process each node in the tree once.
  // SC: O(N) => Maximum size of the queue can be N/2.
  public void connect(TreeLinkNode root) {
    if (root == null) {
      return;
    }

    Queue<TreeLinkNode> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      int size = queue.size();
      TreeLinkNode[] temp = new TreeLinkNode[size];

      for (int i = 0; i < size; i++) {
        TreeLinkNode node = queue.poll();

        // Storing nodes for later processing.
        temp[i] = node;

        if (node.left != null) {
          queue.add(node.left);
        }
        if (node.right != null) {
          queue.add(node.right);
        }
      }

      // Processing each node by setting the right pointers appropriately.
      for (int i = 0; i < size; i++) {
        temp[i].next = i != size - 1 ? temp[i + 1] : null;
      }
    }
  }

  // This method also uses BFS (level order traversal) but with curr and prev
  // pointers to modify the `next` pointers for each node and not storing them.
  // TC: O(N) => need to process each node in the tree once.
  // SC: O(N) => Maximum size of the queue can be N/2.
  public void connect2(TreeLinkNode root) {
    if (root == null) {
      return;
    }

    Queue<TreeLinkNode> queue = new LinkedList<>();
    queue.add(root);
    TreeLinkNode prev = null;

    while (!queue.isEmpty()) {
      int size = queue.size();

      for (int i = 0; i < size; i++) {
        TreeLinkNode curr = queue.poll();

        if (prev != null) {
          prev.next = curr;
        }

        if (curr.left != null) {
          queue.add(curr.left);
        }
        if (curr.right != null) {
          queue.add(curr.right);
        }
        prev = curr;
      }

      // Reset prev to prevent unnecessary right pointers on it from the next
      // level.
      prev = null;
    }

  }

  // Use the `next` columns from the current level to set the `next` pointers
  // for
  // the level below
  // TC: O(N) => Each node is visited exactly once.
  // SC: O(1) => No extra space is being used.
  public void connect3(TreeLinkNode root) {
    if (root == null) {
      return;
    }

    TreeLinkNode verticalPointer = root;

    // To iterate from the first to the last level.
    while (verticalPointer != null) {
      TreeLinkNode horizontalPointer = verticalPointer;

      // This loop will ensure that all the `next` pointers in the level below
      // are set
      // correctly.
      while (horizontalPointer != null) {
        if (horizontalPointer.left != null) {
          if (horizontalPointer.right != null) {
            // If a parent has both children. The `next` pointer of the left
            // child is the
            // right child.
            horizontalPointer.left.next = horizontalPointer.right;
          } else {
            // Left child but no right, then find the next right node.
            horizontalPointer.left.next = getNextRightNode(horizontalPointer);
          }
        }
        // No left child but right child.
        if (horizontalPointer.right != null) {
          horizontalPointer.right.next = getNextRightNode(horizontalPointer);
        }
        horizontalPointer = horizontalPointer.next;
      }

      // Need to move to the level below. If it doesn't exist then move to first
      // node
      // in the next level, using the same getNextRightNode function.
      if (verticalPointer.left != null) {
        verticalPointer = verticalPointer.left;
      } else if (verticalPointer.right != null) {
        verticalPointer = verticalPointer.right;
      } else {
        // @formatter:off
        /**
         * This case will occur when the tree is like this.
         * Here (P) is the verticalPointer.
         *        1
         *       / \
         *   (P)3  4
         *        / \
         *       5  6
         */
        // @formatter:on
        verticalPointer = getNextRightNode(verticalPointer);
      }
    }

  }

  private TreeLinkNode getNextRightNode(TreeLinkNode root) {
    root = root.next;
    while (root != null) {
      if (root.left != null) {
        return root.left;
      }
      if (root.right != null) {
        return root.right;
      }
      root = root.next;
    }
    return null;
  }

  public static void main(String[] args) {

  }
}
