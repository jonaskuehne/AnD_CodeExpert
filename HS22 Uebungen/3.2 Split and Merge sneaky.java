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
    
    int max = A[0];
    
    for (int i = 1; i < n; ++i) {
      max = Math.max(max, A[i]);
    }
    
    boolean tookMax = false;
    
    int sum = 0;
    
    for (int i = 0; i < n; ++i) {
      if (tookMax || A[i] != max) {
        sum += A[i] / 2;
      } else {
        sum += A[i];
        tookMax = true;
      }
    }
    
    return sum;
  }
}
