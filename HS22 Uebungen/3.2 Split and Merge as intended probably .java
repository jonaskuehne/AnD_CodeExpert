// ADDITIONAL IMPORTS ARE NOT ALLOWED

class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file
    // In.open("public/custom.in");
    // Out.compareTo("public/custom.out");

    int n = In.readInt();
    int[] A = new int[n];
    
    // Read A
    for (int i = 0; i < n; i++) {
      A[i] = In.readInt(); 
    }
    
    Out.println(splitMerge(n, A));
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  public static int splitMerge(int n, int[] A) {
    // table
    int[][] table = new int[A.length][A.length];

    // set base cases
    for (int i = 0; i < A.length; ++i) {
      // diagonal elements itself
      table[i][i] = A[i];
    }

    // fill table in diagonal order
    // begin of diagonal
    for (int startCol = 1; startCol < A.length; ++startCol) {
      // diagonal itself
      for (int diaCol = 0; diaCol < A.length - startCol; ++ diaCol) {
        // actual indices
        int row = diaCol;
        int col = startCol + diaCol;

        // I don't trust you...
        int max = Integer.MIN_VALUE;

        // get new table entry
        for (int k = row; k < col; ++k) {
          max = Math.max(max, computeOp(table[row][k], table[k + 1][col]));
        }

        // set
        table[row][col] = max;
      }
    }

    // result
    return table[0][table[0].length - 1];
  }
  
  // does actual work
  public static int computeOp(int a, int b) {
    if (a > b) {
      return a + b / 2;
    } else {
      return b + a / 2;
    }
  }
}
