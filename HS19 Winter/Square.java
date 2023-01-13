import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;

class Main {
    public static void main(String[] args) {
        // Uncomment the following two lines if you want to read from a file
        // In.open("public/test2.in");
        // Out.compareTo("public/test2.out");
        
        // Read the number of test cases
        int T = In.readInt();
        //
        // Now solve each case
        //    
        for (int test = 0; test < T; test += 1) {
          //
          // Read the number of rows of the matrix
          //
          int M = In.readInt();
          //
          // Read the number of columns of the matrix
          //
          int N = In.readInt();
          //
          // Allocate the matrix
          //
          int B[][] = new int[M][N];      
          //
          // Read the matrix
          //
          for (int i = 0; i < M; i += 1) {
            for (int j = 0; j < N; j += 1) {                          
              B[i][j] = In.readInt();                     
            }
          }        
          
          Out.println(Square(M, N, B));
        }
        
      
        // Uncomment the following line if you want to read from a file
        // In.close();
    }
    
    private static int Square(int M, int N, int[][] B){
      
      if (M == 0 || N == 0) {
        return 0;
      }
      
      // dp table
      int[][] T = new int[M][N];
      
      int maxLength = 0;
      
      // init
      // also get maxLength 1 if array only one-dimensional
      for (int i = 0; i < M; ++i) {
        T[i][0] = B[i][0];
        maxLength = Math.max(T[i][0], maxLength);
      }
      
      for (int j = 0; j < N; ++j) {
        T[0][j] = B[0][j];
        maxLength = Math.max(T[0][j], maxLength);
      }
      
      // meaning of table: largest edge with bottom right edge here
      for (int i = 1; i < M; ++i) {
        for (int j = 1; j < N; ++j) {
          // if 1 at spot
          if (B[i][j] == 1) {
            // get min of adj elements, +1
            T[i][j] = Math.min(T[i - 1][j - 1], Math.min(T[i - 1][j], T[i][j - 1])) + 1;
            maxLength = Math.max(maxLength, T[i][j]);
          }
        }
      }
      
      // since square
      return maxLength * maxLength;
    }

   
}
