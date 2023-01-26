import java.io.*;
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
    // In.open("public/small.in");
    // Out.compareTo("public/small.out");

    int tests = In.readInt(); // number of tests
    for (int t = 0; t < tests; t++) {
      int n = In.readInt(); // length of A
      int[] A = new int[n]; // A
      for (int i = 0; i < n; i++) {
        A[i] = In.readInt();
      }
      Out.println(getLongestSubsequence(n, A));
    }
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  public static int getLongestSubsequence(int n, int[] A) {
    // look-up array
    int[] lookUp = new int[1048575 + 1];
    
    // get max of each element
    // just try all powers of 2, see which entry is the largest
    for (int i = 0; i < n; ++i) {
      // + 1 as we increase the sequence at the max entry
      lookUp[A[i]] = getMax(lookUp, A[i]) + 1;
    }
    
    // get max value in look-up
    int max = lookUp[0];
    
    for (int i = 1; i < lookUp.length; ++i) {
      max = Math.max(max, lookUp[i]);
    }
    
    return max;
  }
  
  public static int getMax(int[] table, int val) {
    
    int max = 0;
    // try all powers of 2 (hence log h), store max entry
    for (int i = 1; i < table.length; i *= 2) {
      // smaller one in bound
      if (val - i >= 0) {
        max = Math.max(max, table[val - i]);
      }
      
      // larger one in bound
      if (val + i < table.length) {
        max = Math.max(max, table[val + i]);
      }
      
    }
    
    return max;
    
  }
  
}
