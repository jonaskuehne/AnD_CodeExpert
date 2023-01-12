// ADDITIONAL IMPORTS ARE NOT ALLOWED

class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file.
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int n = In.readInt();
    int S = In.readInt();
    int[] A = new int[n];
    for (int i = 0; i < n; i++) {
      A[i] = In.readInt();
    }
    Out.println(getB(n, A, S));
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  public static int getB(int n, int[] A, int S) {
    // get min and max value of array
    int[] minMax = getMinMax(A);
    
    // start values
    int l = minMax[0];
    int r = minMax[1];
    int b = 0;
    int resB = 0;
    
    // binary search really
    while (l <= r) {
      
      // +1 by trial and error
      b = (l + r + 1) / 2;
      
      if (S <= getBoundSum(A, b)) {
        r = b - 1;
        // store
        resB = b;
      } else {
        l = b + 1;
      }
      
    }
    
    return b + 1;
  }
  
  // max subarray sum w/ bound
  public static int getBoundSum(int[] A, int bound) {
    int max = 0;
    int sum = 0;
    
    for (int i = 0; i < A.length; ++i) {
      if (A[i] < bound) {
        if (sum < 0) {
          sum = A[i];
        } else {
          sum += A[i];
        }
        // better than last?
        max = Math.max(max, sum);
      }
    }
    return max;
  }
  
  // returns min, max
  public static int[] getMinMax(int[] A) {
    int min = A[0];
    int max = A[0];
    
    for (int i = 1; i < A.length; ++i) {
      min = Math.min(min, A[i]);
      max = Math.max(max, A[i]);
    }
    
    return new int[]{min, max};
  }
}
