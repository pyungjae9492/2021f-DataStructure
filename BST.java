// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)
import java.util.*;

public class BST { // Binary Search Tree implementation

  public class Node {
    public int frequency, access_count, height;
    public String data;
    public Node left, right;
    public Node (String data) {
      this.data = data;
      this.left = this.right = null;
      this.frequency = 1;
      this.access_count = 0;
      this.height = 0;
    }
  }

  public Node root;
  public int size;
  public ArrayList<Node> nodes = new ArrayList<>();
  protected boolean NOBSTified = false;
  protected boolean OBSTified = false;

  public BST() {
    this.root = null;
    this.size = 0;
  }

  public int size() {
    return size;
  }

  public void insert(String key) {
    size++;
    if (this.root == null) {
      Node t = new Node(key);
      this.root = t;
      nodes.add(this.root);
    } else {
      Node current_node = this.root;
      while (true) {
        int compare_factor = current_node.data.compareTo(key);
        if (compare_factor > 0) {
          if (current_node.left == null) {
            Node t = new Node(key);
            current_node.left  = t;
            nodes.add(current_node.left);
            break;
          } else {
            current_node = current_node.left;
          }
        } else if (compare_factor == 0) {
          current_node.frequency++;
          size--;
          break;
        } else if (compare_factor < 0) {
          if (current_node.right == null) {
            Node t = new Node(key);
            current_node.right = t;
            nodes.add(current_node.right);
            break;
          } else {
            current_node = current_node.right;
          }
        }
      }
    }
  }
  
  public boolean find(String key) {
    if (this.root != null) {
      Node current_node = this.root;
      while (true) {
        current_node.access_count++;
        int compare_factor = current_node.data.compareTo(key);
        if (compare_factor > 0) {
          if (current_node.left == null) {
            return false;
          } else {
            current_node = current_node.left;
            continue;
          }
        } else if (compare_factor == 0) {
          return true;
        } else if (compare_factor < 0) {
          if (current_node.right == null) {
            return false;
          } else {
            current_node = current_node.right;
            continue;
          }
        }
      }
    } else {
      return false;
    }
  }
  
  
  public int sumFreq() {
    int sum_frequency = 0;
    for (Node node : nodes) {
      sum_frequency += node.frequency;
    }
    return sum_frequency;
  }
  
  public int sumProbes() {
    int sum_probe = 0;
    for (Node node : nodes) {
      sum_probe += node.access_count;
    }
    return sum_probe;
  }

  public int sumWeightedPath() {
    return getWeightedPath(this.root, 1);
  }

  private int getWeightedPath(Node node, int i) {
    int sum = 0;
    int level = i;
    if (node == null) {
      return sum;
    }
    if (node.left != null) {
      level++;
      sum += getWeightedPath(node.left, level);
      level--;
    }
    if (node != null) {
      sum += node.frequency * level;
    }
    if (node.right != null) {
      level++;
      sum += getWeightedPath(node.right, level);
      level--;
    }
    return sum;
  }


  public void resetCounters() {
    for (Node node : nodes) {
      node.frequency = 0;
      node.access_count = 0;
    }
  }

  public void nobst() {
    this.NOBSTified = true;
    Stack<Node> stack = new Stack<>();
    ArrayList<Node> inorderNode = new ArrayList<>();
    int[] inorderFreq = new int[size];
    int sumfreq = sumFreq();
    int index = 0;
    Node current_node = this.root;
    while (true) {
      while (current_node != null) {
        stack.push(current_node);
        current_node = current_node.left;
      }
      if (!stack.empty()) {
        current_node = stack.pop();
        inorderNode.add(current_node);
        inorderFreq[index] = current_node.frequency;
        index++;
        current_node = current_node.right;
      } else {
        stack.clear();
        break;
      }
    }
    // nodes = new ArrayList<>();
    // this.root = null;
    // buildNobst(inorderNode, sumfreq);
  }	// Set NOBSTified to true.

  // private Node buildNobst(Node[] inorderNode, int[] inorderFreq, int sumfreq, int start, int end) {
  //   int sum = 0;
  //   if (start == end) {
  //     return null;
  //   }
  //   for (int i = start; i < size; i++) {
  //     sum += inorderFreq[i];
  //     int dif = ((sumfreq-sum) - (sum - inorderFreq[i]));
  //     System.out.println(dif);
  //     if (dif <= 0) {
  //       if (this.root == null) {
  //         this.root = inorderNode[i];
  //         nodes.add(this.root);
  //         // this.root.left = buildNobst(inorderNode, inorderFreq, sum-inorderFreq[i], start, i-1);
  //         // this.root.right = buildNobst(inorderNode, inorderFreq, sumfreq-sum, i+1, end);
  //         return null;
  //       } else {
  //         Node node = inorderNode[i];
  //         nodes.add(node);
  //         // node.left = buildNobst(inorderNode, inorderFreq, sum-inorderFreq[i], start, i-1);
  //         // node.right = buildNobst(inorderNode, inorderFreq, sumfreq-sum, i+1, end);
  //         return node;
  //       }


  //     }
  //   }
  //   return null;
  // }

  // private void buildNobst(ArrayList<Node> inorderNode, int sumFreq) {
  //   if (inorderNode.size() > 0 && sumFreq > 0) {
  //     int k = 0;
  //     int s_lsum = 0;
  //     int s_rsum = 0;
  //     int leftSum = 0;
  //     int rightSum = sumFreq;
  //     int mindiff = 2000000000;
  //     for (int i = 0; i < inorderNode.size(); i++) {
  //       leftSum += i == 0 ? 0 : inorderNode.get(i - 1).freq;
  //       rightSum -= inorderNode.get(i).freq;
  //       if (Math.abs(rightSum - leftSum) < mindiff) {
  //         mindiff = rightSum - leftSum;
  //         k = i;
  //         s_lsum = leftSum;
  //         s_rsum = rightSum;
  //       }
  //     }
  //     Node curr = inorderNode.get(k);
  //     insertWithFreq(curr.key, curr.freq);
  //     buildNobst(inorderNode.subList(0, k), s_lsum);
  //     buildNobst(inorderNode.subList(k + 1 >= inorderNode.size() ? inorderNode.size() - 1 : k + 1, inorderNode.size()), s_rsum);
  //   }
  // }

  public void obst() {
    this.OBSTified = true;
    Stack<Node> stack = new Stack<>();
    Node[] inorderNode = new Node[size];
    int[] inorderFreq = new int[size];
    int index = 0;
    Node current_node = this.root;
    while (true) {
      while (current_node != null) {
        stack.push(current_node);
        current_node = current_node.left;
      }
      if (!stack.empty()) {
        current_node = stack.pop();
        inorderNode[index] = current_node;
        inorderFreq[index] = current_node.frequency;
        index++;
        current_node = current_node.right;
      } else {
        stack.clear();
        break;
      }
    }
    int [][] rootMatrix = getMatrix(inorderFreq);
    
    nodes = new ArrayList<>();
    this.root = null;
    obstBuild(1, size, rootMatrix, inorderNode);
  }	// Set OBSTified to true.

  private Node obstBuild(int i, int j, int[][] rootMatrix, Node[] inorderNode) {
    int k = rootMatrix[i][j];
    if (k == 0) {
      return null;
    }
    if (this.root == null) {
      this.root = inorderNode[k-1];
      nodes.add(this.root);
      this.root.left = obstBuild(i, k-1, rootMatrix, inorderNode);
      this.root.right = obstBuild(k+1, j, rootMatrix, inorderNode);
      return null;
    } else {
      Node node = inorderNode[k-1];
      nodes.add(node);
      node.left = obstBuild(i, k-1, rootMatrix, inorderNode);
      node.right = obstBuild(k+1, j, rootMatrix, inorderNode);
      return node;
    }
  }

  private int[][] getMatrix(int[] freqList) {
    int n = size;
    int[][] cost = new int [n+2][n+1];
    int[][] root = new int [n+2][n+1];
    for(int i = 1; i <= n; i++){
      cost[i][i] = freqList[i-1];
      root[i][i] = i;
    }
    for(int j = 1; j<n; j++) {
      for(int m = 1; m <= n-j; m++) {
        int s = m + j;
        int low = root[m][s-1];
        int high = root[m+1][s];
        int minCost = 0;
        int optRoot = 0;

        for(int k=low; k<=high; k++) {
          int tmp = cost[m][k-1] + cost[k+1][s];
          if (minCost == 0) {
            minCost = tmp;
            optRoot = k;
            continue;
          }
          else if (minCost > tmp) {
            minCost = tmp;
            optRoot = k;
          }
        }
        cost[m][s] = freqSum(freqList, m, s) + minCost;
        root[m][s] = optRoot;
      }
    }
    return root;
  }

  private int freqSum(int[] freqList, int m, int s) {
    int sum = 0;
    for (int i = m-1; i <= s-1; i++) {
      sum += freqList[i];
    }
    return sum;
  }

  public void print() {
    Stack<Node> stack = new Stack<>();
    Node current_node = this.root;
    while (true) {
      while (current_node != null) {
        stack.push(current_node);
        current_node = current_node.left;
      }
      if (!stack.empty()) {
        current_node = stack.pop();
        System.out.println("["+current_node.data + ":" + current_node.frequency + ":" + current_node.access_count+"]");
        current_node = current_node.right;
      } else {
        stack.clear();
        break;
      }
    }
  }

}
