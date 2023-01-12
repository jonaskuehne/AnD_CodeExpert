import java.lang.Math;


class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file.
    // In.open("public/large.in");
    // Out.compareTo("public/large.out");

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
    int[] sumArray = new int[n - 1];
    
    for (int i = 0; i < n - 1; ++i) {
      sumArray[i] = A[i] + A[i + 1];
    }
    
    // table
    int[] memo = new int[n - 1];
    
    // base cases
    memo[0] = sumArray[0];
    memo[1] = Math.max(sumArray[0], sumArray[1]);
    memo[2] = Math.max(memo[1], sumArray[2]);
    
    for (int i = 3; i < memo.length; ++i) {
      // take max
      memo[i] = Math.max(memo[i - 1], memo[i - 2]);
      memo[i] = Math.max(memo[i], memo[i - 3] + sumArray[i]);
      memo[i] = Math.max(memo[i], sumArray[i]);
    }
    
    return Math.max(0, memo[memo.length - 1]);
  }
}
