import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;


class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file
    // In.open("public/small.in");
    // Out.compareTo("public/small.out");

    int m = In.readInt(); // number of operations
    char[] operationArray = new char[m]; // operators (insertion, deletion, height query)
    int[] operandArray = new int[m];     // keys
    
    for (int i = 0; i < m; i++) {
      operationArray[i] = In.readChar();
      operandArray[i] = In.readInt();
    }
    
    BinaryTree T = new BinaryTree();
    
    for (int i = 0; i < m; i++) {
      switch (operationArray[i]) {
        case 'I': // insertion
          T.insert(operandArray[i]);
          break;
          
        case 'D': // deletion
          T.delete(operandArray[i]);
          break;

        case 'H': // height query 
          Out.println(T.heightOfKey(operandArray[i]));
          break;
      }
    }
    
    // Uncomment the following line if you want to read from a file
    // In.close();
  }
}

class TreeNode {
    public int key;
    public TreeNode parent;
    public TreeNode left;
    public TreeNode right;
    public int height;
  
    TreeNode(int key) {
      this.key = key;
      this.parent = null;
      this.left = null;
      this.right = null;
      this.height = 1;
    }
}

class BinaryTree {
  TreeNode root;
  
  BinaryTree() {
    this.root = null;
  }
  
  public void insert(int key) {
    if (root == null) {
      root = new TreeNode(key);
    } else {
      insert(root, key);
    }
  }
  
  public void insert(TreeNode node, int key) {
    if (key < node.key) {
      if (node.left != null) {
        insert(node.left, key);
      } else {
        node.left = new TreeNode(key);
        node.left.parent = node;
      }
    } else {
      if (node.right != null) {
        insert(node.right, key);
      } else {
        node.right = new TreeNode(key);
        node.right.parent = node;
      }
    }
    
    if (node.left == null) {
      node.height = node.right.height + 1;
    } else if (node.right == null) {
      node.height = node.left.height + 1;
    } else {
      node.height = Math.max(node.left.height, node.right.height) + 1;
    }
    
  }
  
  public void delete(int key) {
    TreeNode node = search(key, true); // find the node to be deleted
    
    if (node == null) {
      return;
    }
      
    if (node.left == null) {
      if (node.right == null) { // no child
        if (node == root) {
          root = null;
        } else {
          if (node.parent.left == node) {
            node.parent.left = null;
          } else {
            node.parent.right = null;
          }
        }
      } else { // only right child
        if (node == root) {
          root = node.right;
          root.parent = null;
        } else {
          if (node.parent.left == node){
            node.parent.left = node.right;
            node.right.parent = node.parent;
          } else {
            node.parent.right = node.right;
            node.right.parent = node.parent;
          }
        }
      }
    } else {
      if (node.right == null) { // only left child
        if (node == root) {
          root = node.left;
          root.parent = null;
        } else {
          if (node.parent.left == node){
            node.parent.left = node.left;
            node.left.parent = node.parent;
          } else {
            node.parent.right = node.left;
            node.left.parent = node.parent;
          }
        }
      } else { // two children
        TreeNode swapNode = node.left;
        
        while (swapNode.right != null) {
          swapNode = swapNode.right;
        }
        
        // very nice indeed
        search(swapNode.key, true);
          
        node.key = swapNode.key;
        
        if (swapNode.left != null) { // swapNode has left Child
          if (swapNode.parent.left == swapNode) {
            swapNode.parent.left = swapNode.left;
            swapNode.left.parent = swapNode.parent;
          } else {
            swapNode.parent.right = swapNode.left;
            swapNode.left.parent = swapNode.parent;
          }
        } else { // swapNode has no child
          if (swapNode.parent.left == swapNode) {
            swapNode.parent.left = null;
          } else {
            swapNode.parent.right = null;
          }
        }
      }
    }
  }
  
  public TreeNode search(int key, boolean doDelete) {
    if (root == null) {
      return null;
    } else {
      return search(root, key, doDelete);
    }
  }
  
  public TreeNode search(TreeNode node, int key, boolean doDelete) {
    TreeNode retNode;
    if (node == null) {
      retNode =  null;
    } else if (key == node.key) {
      retNode = node;
    } else {
      if (key < node.key) {
        retNode = search(node.left, key, doDelete);
      } else {
        retNode = search(node.right, key, doDelete);
      }
    }
    
    // new
    if (retNode != null && doDelete && !(node.left == null && node.right == null)) {
      
      if (node.left == null) {
        if (node.right == retNode) {
          --node.height;
        } else {
          node.height = node.right.height + 1;
        }
      } else if (node.right == null) {
        if (node.left == retNode) {
          --node.height;
        } else {
          node.height = node.left.height + 1;
        }
      } else {
        if (node.left == retNode) {
          node.height = Math.max(node.height - 1, node.right.height + 1);
        } else if (node.right == retNode) {
          node.height = Math.max(node.height - 1, node.left.height + 1);
        } else {
          node.height = Math.max(node.left.height, node.right.height) + 1;
        }
      }
      
    }
    
    return retNode;
  }
  
  public int heightOfKey(int key) {
    TreeNode node = search(key, false);
    if (node == null) {
      return -1;
    } else {
      return node.height;
    }
  }
} 
