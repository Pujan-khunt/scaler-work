import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * QUESTION: Binary Tree from Inorder and Preorder
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

  // TC: O(N^2)
  // The worst case is a left skewed tree, where the preorder and the inorder
  // traversals are nothing but
  // complete top to down and bottom to up traversals respectively. This would
  // mean to find a single index for the root node
  // in the inorder array would take about O(n) time, by extending that logic,
  // doing it for each value in the preorder array,
  // it would mean that the time complexity is O(N * N) which is O(N^2)
  public static TreeNode helper(ArrayList<Integer> preorder, ArrayList<Integer> inorder, int preStart, int preEnd,
      int inStart,
      int inEnd) {
    if (preStart > preEnd || inStart > inEnd) {
      return null;
    }
    int root = preorder.get(preStart);

    // Create the root tree node
    TreeNode rootNode = new TreeNode(root);

    // Find index of element root in the inorder list.
    int index = -1;
    for (int i = inStart; i <= inEnd; i++) {
      if (inorder.get(i) == root) {
        index = i;
        break;
      }
    }

    rootNode.left = helper(preorder, inorder, preStart + 1, preStart + index - inStart, inStart, index - 1);
    rootNode.right = helper(preorder, inorder, preStart + index + 1 - inStart, preEnd, index + 1, inEnd);

    return rootNode;
  }

  // TC: O(N)
  // The TC is minimized by precomputing a hashmap of key = value of inorder
  // array, value = index of inorder array
  // This guarantees O(1) retrieval of the index in inorder array.
  public static TreeNode helper2(ArrayList<Integer> preorder, ArrayList<Integer> inorder, int preStart, int preEnd,
      int inStart,
      int inEnd, Map<Integer, Integer> inorderIndexMap) {
    if (preStart > preEnd || inStart > inEnd) {
      return null;
    }
    int root = preorder.get(preStart);

    // Create the root tree node
    TreeNode rootNode = new TreeNode(root);

    // Find index of element root in the inorder list.
    // int index = -1;
    // for (int i = inStart; i <= inEnd; i++) {
    // if (inorder.get(i) == root) {
    // index = i;
    // break;
    // }
    // }
    int index = inorderIndexMap.get(root);

    rootNode.left = helper2(
        preorder,
        inorder,
        preStart + 1,
        preStart + index - inStart,
        inStart,
        index - 1,
        inorderIndexMap
    );
    rootNode.right = helper2(
        preorder,
        inorder,
        preStart + index + 1 - inStart,
        preEnd,
        index + 1,
        inEnd,
        inorderIndexMap
    );

    return rootNode;
  }

  /*
   * A: List of integers representing the preorder traversal of the binary tree
   * B: List of integers representing the inorder traversal of the binary tree
   */
  public static TreeNode buildTree(ArrayList<Integer> A, ArrayList<Integer> B) {
    Map<Integer, Integer> indexMap = new HashMap<>();
    for (int i = 0; i < B.size(); i++) {
      indexMap.put(B.get(i), i);
    }
    return helper2(A, B, 0, A.size() - 1, 0, B.size() - 1, indexMap);
  }

  public static void main(String[] args) {
    ArrayList<Integer> preorder = new ArrayList<>();
    preorder.add(1);
    preorder.add(6);
    preorder.add(2);
    preorder.add(3);

    ArrayList<Integer> inorder = new ArrayList<>();
    inorder.add(6);
    inorder.add(1);
    inorder.add(3);
    inorder.add(2);

    buildTree(preorder, inorder);

    ArrayList<Integer> preorder2 = new ArrayList<>();
    preorder2.add(4);

    ArrayList<Integer> inorder2 = new ArrayList<>();
    inorder2.add(4);

    buildTree(preorder2, inorder2);
  }
}
