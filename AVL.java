// AVL Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)
import java.util.*;
import java.lang.Math;

public class AVL extends BST
{
  public AVL() { 
    this.root = null;
    this.size = 0;
    this.depth = 0;
  }
  
  int depth = 0;
  
  private int getHeight(Node node) {
    if (node==null) {
      return 0;
    } else {
      return node.height;
    }
  }
  public void insert(String key) {
    if (this.root == null) {
      size++;
      this.root = new Node(key);
      nodes.add(this.root);
    } else {
      this.root = avlInsert(this.root, key);
    }
  }


  public Node avlInsert(Node node, String key){
    Node newNode = new Node(key);
    size++;

    if (node == null) {
      nodes.add(newNode);
      return newNode;
    }
    int compare_factor = node.data.compareTo(key);

    /* 재귀로 접근 */
    if (compare_factor > 0) {
      node.left = avlInsert(node.left, key);
    } else if (compare_factor < 0) {
      node.right = avlInsert(node.right, key);
    } else {
      node.frequency++;
      size--;
    }

    node.height = 1 + Math.max(getHeight(node.left) , getHeight(node.right));

    int bf = BF(node);

    if (bf > 1 && (node.left.data).compareTo(key) > 0)
      return rightRotate(node);

    if (bf < -1 && (node.right.data).compareTo(key) < 0)
      return leftRotate(node);

    if (bf > 1 && (node.left.data).compareTo(key) < 0) {
      node.left = leftRotate(node.left);
      return rightRotate(node);
    }

    if (bf < -1 && (node.right.data).compareTo(key) > 0 ) {
      node.right = rightRotate(node.right);
      return leftRotate(node);
    }

    return node;
  }

  private Node leftRotate(Node current_node) {
    Node newParent = current_node.right;
    Node leftSibling = current_node.right.left;
    newParent.left = current_node;
    current_node.right = leftSibling;
    current_node.height = 1 + Math.max(getHeight(current_node.left), getHeight(current_node.right));
    newParent.height = 1 + Math.max(getHeight(newParent.left), getHeight(newParent.right));
    return newParent;
  }

  private Node rightRotate(Node current_node) {
    Node newParent = current_node.left;
    Node rightSibling = current_node.left.right;
    newParent.right = current_node;
    current_node.left = rightSibling;
    current_node.height = 1 + Math.max(getHeight(current_node.left), getHeight(current_node.right));
    newParent.height = 1 + Math.max(getHeight(newParent.left), getHeight(newParent.right));
    return newParent;
  }

  private int BF(Node current_node){
    if (current_node == null) {
      return 0;
    }
    return getHeight(current_node.left) - getHeight(current_node.right);
  }
}
