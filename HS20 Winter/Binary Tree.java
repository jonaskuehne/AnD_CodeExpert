import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;


class Main {
    public static void main(String[] args) {
        // Uncomment the following two lines if you want to read from a file
        // In.open("public/custom.in");
        // Out.compareTo("public/custom.out");

        int m = In.readInt();                 // Number of Operations
        char[] Operation_Array=new char[m];   // Operators (Insertion, Deletion, Query)
        int[] Operand_Array=new int[m];       // Keys
        
        for(int i=0;i<m;i++){
          Operation_Array[i]=In.readChar();
          switch(Operation_Array[i]){
            case 'I':  // Insertion
            case 'D':  // Deletion
            case 'H':  // Depth Query
            case 'C':  // Children Query
            case 'K':  // Key of Rank Query
              Operand_Array[i]=In.readInt();
              break;
          }
        }
        
        BinaryTree T=new BinaryTree();
        
        for(int i=0;i<m;i++){
        
          switch(Operation_Array[i]){
            case 'I':  // Insertion
              T.insert(Operand_Array[i]);
              break;
              
            case 'D':  // Deletion
              T.delete(Operand_Array[i]);
              break;
              
            case 'M':  // Minimum Query 
              Out.println(T.min());
              break;
              
            case 'H':  // Depth Query 
              Out.println(T.depth(Operand_Array[i]));
              break;
              
            case 'C':  // Children Query 
              Out.println(T.children_of_node(Operand_Array[i]));
              break;
              
            case 'K':  // Key of Rank Query
              Out.println(T.key_of_rank(Operand_Array[i]));
              
          }
        }
        
        
      
        // Uncomment the following line if you want to read from a file
        //In.close();
    }
    

   
}

class TreeNode{
    public int key;
    public TreeNode parent;
    public TreeNode left;
    public TreeNode right;
    
    // added that one, represents size of subtree
    public int numChildren;
  
    TreeNode(int key){
        this.key=key;
        this.parent=null;
        this.left=null;
        this.right=null;
        
        // init with 1
        numChildren = 1;
    }
}

class BinaryTree{
  
    TreeNode root;
    
    BinaryTree(){
      this.root=null;
    }
    

    // returns min key
    public int min(){
      // base case tree empty
      if (root == null) {
        return -1;
      }
      
      // recursive case
      return recMin(root);
    }
    
    // searches min recursively
    public int recMin(TreeNode node) {
      // can't go any further to the left -> result
      if (node.left == null) {
        return node.key;
      // else go left
      } else {
        return recMin(node.left);
      }
    }
    
    // gets depth
    public int depth(int key){
        return depthSearch(key);
    }
    
    // copy paste from their own implementation, added depth argument
    public int depthSearch(int key){
      // empty tree
      if(root==null){
        return -1;
      }
      else{
        return depthSearch(root,key, 0);
      }
    }
    
    // again copy paste from below
    public int depthSearch(TreeNode node,int key, int depth){
      if(node==null){
        return -1;
      }
      else if(key==node.key){
        // changed this part to return the depth
        return depth;
      }
      else{
        // next level -> depth + 1 for both branches
        if(key<node.key){
          return depthSearch(node.left,key, depth + 1);
        }
        else{
          return depthSearch(node.right,key, depth + 1);
        }
      }
    }
    
    // checks how many children node has (0, 1 or 2)
    public int children_of_node(int key){
      // get node
      TreeNode keyNode = search(key);
      
      // not found
      if (keyNode == null) {
        return -1;
      }
      
      // count non-null children
      int count = 0;
      
      // has left
      if (keyNode.left != null) {
        ++count;
      }
      
      // has right
      if (keyNode.right != null) {
        ++count;
      }
      
      return count;
    }

    // gets (rank)th smallest key
    // needed adjustments in tree implementation (numChildren)
    public int key_of_rank(int rank){
      
      // base case, either empty of less nodes than rank
      if (root == null || root.numChildren < rank) {
        return -1;
      }
      
      return recKeyRank(root, rank);
    }
    
    // does work
    public int recKeyRank(TreeNode node, int rank) {
      
      // size of left branch + 1
      int leftTreeSize;
      
      // right branch empty -> whole subtree
      if (node.right == null) {
        leftTreeSize = node.numChildren;
      // else subtract
      } else {
        leftTreeSize = node.numChildren - node.right.numChildren;
      }
      
      // found
      if (rank == leftTreeSize) {
        return node.key;
      }
      
      // go left -> rank same
      if (rank < leftTreeSize) {
        return recKeyRank(node.left, rank);
      // go right -> adjust rank
      // (kth smallest element in tree not kth smallest element in right branch)
      } else {
        return recKeyRank(node.right, rank - leftTreeSize);
      }
      
    }
    
    
    public void insert(int key){
      
      if(root==null){
        root=new TreeNode(key);
      }
      else{
        insert(root,key);
      }
    }
    
    
    public void insert(TreeNode node,int key){
      
      // added that one
      ++node.numChildren;
      
      if(key<node.key){
        if(node.left!=null){
          insert(node.left,key);
        }
        else{
          node.left=new TreeNode(key);
          node.left.parent=node;
        }
      }
      else{
        if(node.right!=null){
          insert(node.right,key);
        }
        else{
          node.right=new TreeNode(key);
          node.right.parent=node;
        }
      }
    }
    
    
    public void delete(int key){
      
      // find the node to be deleted
      TreeNode node=search(key);
      
      if(node==null){
        return;
      }
      
      // kinda search for node again and decrease numChildren along the way
      // guaranteed to find since else return above
      deleteSearch(root, key);
      
      if(node.left==null){
        if(node.right==null){
          // no child
          
          if(node==root){
            root=null;
          }
          else{
            if(node.parent.left==node){
              node.parent.left=null;
            }
            else{
              node.parent.right=null;
            }
          }
        }
        else{
          // only right child
          
          if(node==root){
            root=node.right;
            root.parent=null;
          }
          else{
            if(node.parent.left==node){
              node.parent.left=node.right;
              node.right.parent=node.parent;
            }
            else{
              node.parent.right=node.right;
              node.right.parent=node.parent;
            }
          }
        }
        
      }
      else{
        if(node.right==null){
          //only left child
          
          if(node==root){
            root=node.left;
            root.parent=null;
          }
          else{
            if(node.parent.left==node)
            {
              node.parent.left=node.left;
              node.left.parent=node.parent;
            }
            else
            {
              node.parent.right=node.left;
              node.left.parent=node.parent;
            }
          }  
        }
        else{      
          // two children
          TreeNode swap_node=node.left;
          
          // added that one, decrease on way down to successor
          --swap_node.numChildren;
          
          while(swap_node.right!=null){
            swap_node=swap_node.right;
            
            // added that one
            --swap_node.numChildren;
          }
            
          node.key=swap_node.key;
          
          if(swap_node.left!=null){
            // swap_node has left Child
            
            if(swap_node.parent.left==swap_node){
              swap_node.parent.left=swap_node.left;
              swap_node.left.parent=swap_node.parent;
            }
            else{
              swap_node.parent.right=swap_node.left;
              swap_node.left.parent=swap_node.parent;
            }
          }
          else{
            // swap_node has no child
            
            if(swap_node.parent.left==swap_node){
              swap_node.parent.left=null;
            }
            else{
              swap_node.parent.right=null;
            }
          }
        }
      }
            
    }
    
    // again some copy-paste action going on here
    public TreeNode deleteSearch(TreeNode node,int key){
      if(node==null){
        return null;
      }
      
      // added that one
      --node.numChildren;
      
      if(key==node.key){
        return node;
      }

      if(key<node.key){
        return deleteSearch(node.left,key);
      } else{
        return deleteSearch(node.right,key);
      }

    }
    
    public TreeNode search(int key){
      
      if(root==null){
        return null;
      }
      else{
        return search(root,key);
      }
    }
    
    public TreeNode search(TreeNode node,int key){
      if(node==null){
        return null;
      }
      else if(key==node.key){
        return node;
      }
      else{
        if(key<node.key){
          return search(node.left,key);
        }
        else{
          return search(node.right,key);
        }
      }
    }
} 
