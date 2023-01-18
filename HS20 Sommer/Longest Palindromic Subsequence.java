import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;

class Main {
    public static void main(String[] args) {
        
        // Uncomment the following two lines if you want to read from a file
        // In.open("public/example.in");
        // Out.compareTo("public/example.out");

        int T = In.readInt();               // Read Number of Tasks
      
        
        
        for(int i=0;i<T;i++)
        {
          int n = In.readInt();             // Read Length of A
          
     
          String SA=In.readString();        // Read A as a string
          
          char[] A=new char[n];
          
      

          for(int j=0;j<n;j++){
            A[j]=SA.charAt(j);
          }
          

          Out.println(Palindrome(A, n));
          
        }
        
      
        // Uncomment the following line if you want to read from a file
        // In.close();
    }
    
    public static int Palindrome(char[] A, int n) {
      // pull reverse card
      char[] B = new char[A.length];
      
      for (int i = 0; i < n; ++i) {
        B[i] = A[n - i - 1];
      }
      
      // DP table
      int[][] T = new int[n + 1][n + 1];
      
      // run longest common subsequence
      for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= n; ++j) {
          int d = 0;
          // if same increase
          if (A[i - 1] == B[j - 1]) {
            d = 1;
          }
          
          // take max
          T[i][j] = Math.max(T[i - 1][j - 1] + d, Math.max(T[i][j - 1], T[i - 1][j]));
        }
      }
      
      // return bottom right
      return T[n][n];
    }

   
}

