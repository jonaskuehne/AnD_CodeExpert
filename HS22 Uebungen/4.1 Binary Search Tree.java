// ADDITIONAL IMPORTS ARE NOT ALLOWED

class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int m = In.readInt(); // number of operations
    char[] operationArray = new char[m]; // operators (insert, delete, query)
    int[] operandArray = new int[m];     // values
    for (int i = 0; i < m; i++) {
      operationArray[i] = In.readChar();
      operandArray[i] = In.readInt();
    }
    
    BinaryTree T = new BinaryTree();
    for (int i = 0; i < m; i++) {
      switch (operationArray[i]) {
        case 'I': // insert
          T.insert(operandArray[i]);
          break;
          
        case 'D': // delete
          T.delete(operandArray[i]);
          break;

        case 'Q': // query 
          Out.println(T.query(operandArray[i]));
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
  
  public int numChilds;

  TreeNode(int key) {
    this.key = key;
    this.parent = null;
    this.left = null;
    this.right = null;
    
    // added this
    this.numChilds = 1;
  }
}

class BinaryTree {
  TreeNode root;
  
  BinaryTree() {
    this.root = null;
  }
  
  // Inserts a node with the given key in the binary tree rooted at BinaryTree.root.
  public void insert(int key) {
    if (root == null) {
      root = new TreeNode(key);
    } else {
      insert(root, key);
    }
  }
  
  // Inserts a node with the given key in the binary tree rooted at node.
  public void insert(TreeNode node, int key) {
    
    // added this
    ++node.numChilds;
    
    if (key < node.key) { // insert in left subtree
      if (node.left != null) {
        insert(node.left, key);
      } else {
        node.left = new TreeNode(key);
        node.left.parent = node;
      }
    } else { // insert in right subtree
      if (node.right != null) {
        insert(node.right, key);
      } else {
        node.right = new TreeNode(key);
        node.right.parent = node;
      }
    }
  }
  
  // Deletes a node with the given key from the binary tree rooted at BinaryTree.root.
  public void delete(int key) {
    TreeNode node = search(key); // find the node to be deleted
    
    if (node == null) {
      return;
    }
    
    if (node.left == null) { // no left child
      if (node.right == null) { // no children
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
        
        // added this
        --swapNode.numChilds;
        
        while (swapNode.right != null) { // swapNode is the rightmost descendant of node.left
          swapNode = swapNode.right;
          
          // added this
          --swapNode.numChilds;
          
        }
          
        node.key = swapNode.key; // replace node by swapNode
        
        // clean up the tree
        if (swapNode.left != null) { // swapNode has left child
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
  
  // Returns a node with the given key in the binary tree rooted at BinaryTree.root.
  public TreeNode search(int key) {
    if (root == null) {
      return null;
    } else {
      return search(root, key);
    }
  }
  
  // Returns a node with the given key in the binary tree rooted at node.
  public TreeNode search(TreeNode node, int key) {
    
    // added this
    if (node != null) {
      --node.numChilds; 
    }
    
    if (node == null) {
      return null;
    } else if (key == node.key) {
      return node;
    } else {
      if (key < node.key) {
        return search(node.left, key);
      } else {
        return search(node.right, key);
      }
    }
  }
  
  // Returns the number of keys with value less than or equal to val in the binary tree rooted at BinaryTree.root.
  public int query(int val) {
    
    // if we can do recursion for once...
    return getNum(val, root);
  }
  
  public int getNum(int val, TreeNode node) {
    
    // base case
    if (node == null) {
      return 0;
    }
    
    // need to go lower
    if (node.key > val) {
      // has successor
      if (node.left != null) {
        // go there
        return getNum(val, node.left);
      // nothing suitable then
      } else {
        return 0;
      }
    // start here
    } else {
      // those fit for sure
      int num = node.numChilds;
      
      // has successor
      if (node.right != null) {
        // adjust number and add potential addition ones
        num = num - node.right.numChilds + getNum(val, node.right);
      }
      
      return num;
    }
    
  }
}
