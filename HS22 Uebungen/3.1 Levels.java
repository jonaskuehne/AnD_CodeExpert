// ADDITIONAL IMPORTS ARE NOT ALLOWED

class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int n = In.readInt();
    int[] A = new int[n];
    int[] B = new int[n];
    for (int i = 0; i < n; i++) {
      A[i] = In.readInt();
    }
    for (int i = 0; i < n; i++) {
      B[i] = In.readInt();
    }
    Out.println(getLevels(n, A, B));
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  public static int getLevels(int n, int[] A, int[] B) {
    
    // table
    int[] t = new int[n + 1];
    
    // init
    for (int i = 1; i < t.length; ++i) {
      t[i] = Integer.MIN_VALUE;
    }
    
    t[0] = Integer.MAX_VALUE;
    
    // go through elements
    for (int i = 0; i < n; ++i) {
      // get best position
      int key = binarySearch(t, A[i]);
      
      // better than current?
      t[key] = Math.max(t[key], B[i]);
    }
    
    // get number of solutions
    int count = 0;
    
    for (int i = 1; i < t.length; ++i) {
      if (t[i] > Integer.MIN_VALUE) {
        ++count;
      } else {
        break;
      }
    }

    return count;
  }
  
  // binary search
  public static int binSearch(int[] val, int key) {
    int r = val.length - 1;
    int l = 0;
    
    while (l <= r) {
      int m = (l + r) / 2;
      
      if (key == val[m]) {
        return m;
      }
      
      if (key < val[m]) {
        l = m + 1;
      } else {
        r = m - 1;
      }
      
    }
    // + 1 to get next larger
    return Math.min((l + r) / 2 + 1, val.length - 1);
    
  }
}
