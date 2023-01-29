import java.lang.Math;


class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file.
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int tests = In.readInt(); // number of tests
    for (int t = 0; t < tests; t++) {
      int n = In.readInt(); // length of A
      int[] A = new int[n]; // A
      for (int i = 0; i < n; i++) {
        A[i] = In.readInt();
      }
      Out.println(getMaximumSum(n, A));
    }
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  public static int getMaximumSum(int n, int[] A) {
    
    // store all possible pairs
    int[] pairs = new int[n - 1];
    for (int i = 0; i < pairs.length; ++i) {
      pairs[i] = A[i] + A[i + 1];
    }
    
    // DP-table
    int[] T = new int[n - 1];
    
    // init, can only take one of first three pairs
    T[0] = pairs[0];
    T[1] = Math.max(T[0], pairs[1]);
    T[2] = Math.max(T[1], pairs[2]);
    
    // recursion: max of last, second last (both don't take),
    // third last + pairs (take)
    // but also just pairs if third last negative
    for (int i = 3; i < T.length; ++i) {
      // don't take
      T[i] = Math.max(T[i - 1], T[i - 2]);
      // take
      T[i] = Math.max(T[i], T[i - 3] + pairs[i]);
      // better with only this pair
      T[i] = Math.max(T[i], pairs[i]);
      
    }
    
    // 0 if all negative
    return Math.max(0, T[T.length - 1]);
  }
}
