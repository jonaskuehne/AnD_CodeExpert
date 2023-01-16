import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;

class Main {
    public static void main(String[] args) {
        
        // Uncomment the following two lines if you want to read from a file
        // In.open("public/1-small.in");
        // Out.compareTo("public/1-small.out");

        int T = In.readInt();              // Read Number of Tasks
        for(int i=0;i<T;i++)
        {
          int n = In.readInt();           // Read Length of A
          int m = In.readInt();           // Read Length of B
          
          
          String SA=In.readString();       // Read A as a string
          String SB=In.readString();       // Read B as a string
          String SC=In.readString();       // Read C as a string
          
          char[] A=new char[n+1];
          char[] B=new char[m+1];
          char[] C=new char[n+m+1];
          
          for(int j=1;j<=n;j++){
            A[j]=SA.charAt(j-1);
          }
          
          for(int j=1;j<=m;j++){
            B[j]=SB.charAt(j-1);
          }
          
          for(int j=1;j<=n+m;j++){
            C[j]=SC.charAt(j-1);
          }
          
          Out.println(Shuffle(n,m,A,B,C));
          
        }
        
      
        // Uncomment the following line if you want to read from a file
        // In.close();
    }
    
    public static boolean Shuffle(int n, int m, char[] A, char[] B, char[] C) {
      //The input arrays A, B and C are indexed from 1 instead of 0 in the code.
      
      // table
      boolean[][] T = new boolean[n+1][m+1];
      
      // init, can build empty string with empty strings
      T[0][0] = true;
      
      // row, how long A matches C
      for (int i = 1; i <= n; ++i) {
        T[i][0] = T[i - 1][0] && A[i] == C[i];
      }
      
      // col, how long B matches C 
      for (int j = 1; j <= m; ++j) {
        T[0][j] = T[0][j - 1] && B[j] == C[j];
      }
      
      // fill
      // recursion:
      // built c(i + j - 1) with a(1...i -1) and b(1...j) and now A[i] matches C[i + j]
      // or
      // built c(i + j - 1) with a(1...i) and b(1...j - 1) and now B[j] matches C[i + j]
      for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= m; ++j) {
          T[i][j] = (T[i - 1][j] && A[i] == C[i + j]) || (T[i][j - 1] && B[j] == C[i + j]);
        }
      }
      
      // result, built c(1...n + m) with a(1...n) and b(1...m)
      return T[n][m];
    }

   
}
