import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;

class Main {
    public static void main(String[] args) {
        // Uncomment the following two lines if you want to read from a file
        // In.open("public/test1.in");
        // Out.compareTo("public/test2.out");

      
        MaxHeap heap = new MaxHeap();
        //
        // Read the number of test cases, and start executing
        //
        int T = In.readInt();
        for (int test = 0; test < T; test += 1) {
          //
          // Read the command from the input
          //
          int command = In.readInt();
          if (command == 1) {
            //
            // If command is '1' then, we must read an array from the input
            // then, build a heap out of the array, using the 'buildHeap'
            // method, and finally, output the heap on the screen.        
            //
            heap.readHeap();
            heap.buildHeap();
            heap.writeHeap();
            
          } else if (command == 2) {
            //
            // if the command is set to '2', then we read the heap values,
            // which already have partial order that satisfies the heap
            // property. Then we read we insert M elements, reading the 
            // M number first.
            //
            heap.readHeap();        
            int M = In.readInt();
            for (int i = 0; i < M; i += 1) {          
              heap.insert(In.readInt());
            }        
            heap.writeHeap();
            
          } else if (command == 3) {
            //
            // if the command is set to '3', then we read the heap values,
            // which already have partial order that satisfies the heap
            // property. Then we delete the maximum elements M times, 
            // after reading the M number first.
            //
            heap.readHeap();        
            int M = In.readInt();;
            for (int i = 0; i < M; i += 1) {
              heap.deleteMax();
            }
            heap.writeHeap();
            
          }
        }
        
        // Uncomment the following line if you want to read from a file
        // In.close();
        
    }

   
}

class MaxHeap {
    //
    // We assume that the heap will not exceed MAX_HEAPSIZE length
    //
    final static int MAX_HEAPSIZE = 100000;    
    //
    // This field describes the number of elements that the heap holds
    //
    private int N = 0;
    //
    // The values of the heap are stored in this array
    //
    private int values[] = new int [MAX_HEAPSIZE];    
    //
    // Default empty constructor
    //
    public MaxHeap () { 
      // do nothing ...
    }    
    //
    // Helper function that provides comparison.
    //
    private boolean cmp(int a, int b) {
      return a < b;
    }
    //
    // Helper function that swaps the i-th & the j-th element of the heap
    //
    private void swap (int i, int j) {
      int tmp = values[i];
      values[i] = values[j];
      values[j] = tmp;
    }
    //
    // The heap will read the values. In this function, 
    // we assume that the values have partial order that satisfies the heap
    // condition.
    //
    public void readHeap () {      
      N = In.readInt();
      for (int i = 0; i < N; i += 1) {
        values[i] = In.readInt();
      }
    }
    //
    // Helper function that is used in printing the state of the heap.
    //
    public void writeHeap () {
      if (N > 0) {
        Out.print(values[0]);
        for (int i = 1; i < N; i += 1) {
          Out.print(" " + values[i]);
        }  
      }    
      Out.println();
    }    
  
    // ====================================================================================================================
    // Complete the methods below. Feel free to add additional methods / fields if needed.
    // ====================================================================================================================
  
    //
    // We assume that values are already stored in the values[] array, but they
    // do not hold the heap condition and have arbitrary order. We need to 
    // restore the heap condition using the method below.
    //

    public void buildHeap () {
      // get starting point
      int start = N / 2;
      
      // restore top down
      for (int i = start; i >= 0; --i) {
        restoreHeapDown(i);
      }
      
    }    
    
    //
    // Inserts a value in the heap, and places it on the right positions such
    // that the heap condition holds.
    //
    public void insert(int value) {
      // add at end
      values[N] = value;
      // increase size
      ++N;
      // restore bottom up
      restoreHeapUp(N - 1);
    }
    
    // restore heap bottom up
    public void restoreHeapUp(int e) {
      // basecase
      if (e == 0) {
        return;
      }
      
      // get parent
      int p = getParent(e);
      
      // larger than parent?
      if (values[p] < values[e]) {
        // swap
        swap(e, p);
        // call on next one
        restoreHeapUp(p);
      }
      // else nothing to do
      
    }
    
    //
    // Pops the first value from the heap, restoring the heap condition
    //
    public void deleteMax () {      
      // swap with last one
      swap(0, N - 1);
      // decrease size
      --N;
      // restore top down
      restoreHeapDown(0);
    }
    
    // restores heap top down
    public void restoreHeapDown(int e) {
      // <firstbornson>
      int fsb = getChilds(e);
      // case has no children -> base case
      if (fsb >= N) {
        return;
      }
      
      // case only left child
      if (fsb == N - 1) {
        // all good
        if (values[e] >= values[fsb]) {
        // swap and 
        } else {
          swap(e, fsb);
        }
        // done since there cannot be a successor, the dynasty is at its end!
        return;
      }
      
      // case two children
      if (values[e] >= values[fsb] && values[e] >= values[fsb + 1]) {
        // all good
        return;
      }
      
      // get index of larger element
      int index;
      if (values[fsb] > values[fsb + 1]) {
        index = fsb;
      } else {
        index = fsb + 1;
      }
      
      // swap
      swap(e, index);
      // recursive call
      restoreHeapDown(index);
    }
    
    // gives index of first (left) child
    public int getChilds(int num) {
      // get level of key
      int level = getLevel(num);
      
      // first elements
      int firstOfThisLevel = (int)Math.pow(2, level) - 1;
      int firstOfNextLevel = (int)Math.pow(2, level + 1) - 1;
      
      // where to start
      int space = num - firstOfThisLevel;
      
      return firstOfNextLevel + 2 * space;
    }
    
    // gives index of parent
    public int getParent(int num) {
      // base case
      if (num == 0) {
        return 0;
      }
      
      // get level of key
      int level = getLevel(num);
      
      // first elements
      int firstOfThisLevel = (int)Math.pow(2, level) - 1;
      int firstOfLastLevel = (int)Math.pow(2, level - 1) - 1;
      
      // where to start
      int space = num - firstOfThisLevel;
      
      return firstOfLastLevel + space / 2;
      
    }
    
    // returns level of key
    public int getLevel(int num) {
      // init
      int lvl = 0;
      int pot = 1;
      
      // use that heap are always dense
      while (num >= pot - 1) {
        ++lvl;
        pot *= 2;
      }
      
      return lvl - 1;
    }
    
    
    
    // ====================================================================================================================
    // End of implementation
    // ====================================================================================================================
}

