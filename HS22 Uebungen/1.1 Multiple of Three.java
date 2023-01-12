class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file.
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int tests = In.readInt(); // number of tests
    for (int t = 0; t < tests; t++) {
      int n = In.readInt();
      int k = In.readInt();
      int[] A = new int[n];
      int[] P = new int[n];
      for (int i = 0; i < n; i++) {
        A[i] = In.readInt();
      }
      for (int i = 0; i < n; i++) {
        P[i] = In.readInt();
      }
      Out.println(getNumberMoves(n, k, A, P));
    }
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  public static int getNumberMoves(int n, int k, int[] A, int[] P) {
    // go through whole array, no more than n-1 moves useful
    for (int c = 0; c < A.length; ++c) {
      // found
      if (A[k] % 3 == 0) {
        return c;
      }
      // try next
      k = P[k];
    }
    // not found
    return -1;
  }
}
