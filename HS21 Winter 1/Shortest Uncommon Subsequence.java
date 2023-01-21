import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;

class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file.
    // Also make sure that the lines are commented when testing with the 
    // "Test" button or when submitting. Otherwise your code may exceed the
    // time liimt.
    // In.open("public/1-small.in");
    // Out.compareTo("public/1-small.out");

    int T = In.readInt();           // number of tasks
    for(int i = 0; i < T; i++)
    {
      int n = In.readInt();         // length of A
      int m = In.readInt();         // length of B
      
      String SA = In.readString();  // read A as a string
      String SB = In.readString();  // read B as a string
      
      char[] A = new char[n];
      char[] B = new char[m];
      for (int j = 0; j < n; j++) {
        A[j] = SA.charAt(j);
      }
      for (int j = 0; j < m; j++) {
        B[j] = SB.charAt(j);
      }
      
      Out.println(ShortestUncommonSubsequence(n, m, A, B));
    }
  
    // Uncomment the following line if you want to read from a file
    In.close();
  }
    
  public static int ShortestUncommonSubsequence(int n, int m, char[] A, char B[]) {
    // DP-table
    int[][] T = new int[n+1][m+1];
    
    // init
    for (int i = 0; i <= m; ++i) {
      // divide to prevent overflow when adding 1
      T[0][i] = Integer.MAX_VALUE / 2;
    }
    
    for (int i = 1; i <= n; ++i) {
      T[i][0] = 1;
    }
    
    // fill
    // recursion: min (above (don't need this char), largest pos. where last char of
    // current A is in current B (use char, kinda same as largest increasing 
    // subsequence: smallest place where we can "add" this char))
    for (int i = 1; i <= n; ++i) {
      for (int j = 1; j <= m; ++j) {
        // get index
        int index = greatestPosInB(A[i - 1], j, B);
        // store
        int possMin;
        // did not find char
        if (index == -1) {
          // then this char is enough as current B does not contain this
          possMin = 1;
        } else {
          // reasoning described above
          possMin = T[i - 1][index] + 1;
        }
        
        // take minimum
        T[i][j] = Math.min(T[i - 1][j], possMin);
      }
        
    }
    
    // bottom right solution
    return T[n][m];
  }
  
  // finds greatest position of char in bounded B
  public static int greatestPosInB(char c, int bound, char[] B) {
    // start at possible greatest position
    for (int i = bound - 1; i >= 0; --i) {
      // found
      if (B[i] == c) {
        return i;
      }
    }
    
    // not found, marker
    return -1;
  }
  
}

