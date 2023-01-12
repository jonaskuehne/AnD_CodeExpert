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
        
        int T = In.readInt();
        for (int test = 0; test < T; test += 1) {
          //
          // Read the size of the array and create a new one
          //
          int N = In.readInt();
          int[][] grid = new int[N][];      
          for (int i = 0; i < N; i += 1) {
            grid[i] = new int[N];
            for (int j = 0; j < N; j += 1) {
              grid[i][j] = In.readInt();
            }
          }
          Out.println(solveGrid(grid));
        }    
          
      
        // Uncomment the following line if you want to read from a file
        // In.close();
    }
    
    //
    // Provide the solution of the grid problem in this function. 
    // Feel free to provide additional fields and methods if 
    // necessary.
    //
    public static int solveGrid (int [][] grid) {
      
      // dp table
      int[][] T = new int[grid.length][grid[0].length];
      
      // init
      for (int i = 0; i < grid[0].length; ++i) {
        T[0][i] = grid[0][i];
      }
      
      // fill
      for (int i = 1; i < grid.length; ++i) {
        for (int j = 0; j < grid[i].length; ++j) {
          // direct upper
          int min = T[i - 1][j];
          
          // can take left
          if (j != 0) {
            min = Math.min(min, T[i - 1][j - 1]);
          }
          
          // can take right
          if (j != grid[i].length - 1) {
            min = Math.min(min, T[i - 1][j + 1]);
          }
          
          // take min + cost
          T[i][j] = min + grid[i][j];
          
        }
      }
      
      // get min of last row
      int ans = T[grid.length - 1][0];
      
      for (int i = 1; i < grid[grid.length - 1].length; ++i) {
        ans = Math.min(ans, T[grid.length - 1][i]);
      }
      
      return ans;
    }
      
  // vergess de fesch kolleg  
  public static int min3(int a, int b, int c) {
      return Math.min(a,  Math.min(b, c));
  }
}
