class Main {
    public static void main(String[] args) {
        // Uncomment this line if you want to read from a file
        In.open("public/small.in");
        Out.compareTo("public/small.out");

        int n = In.readInt(); // read the size of A
        int []A = new int[n];
        
        // Read A
        for(int i = 0; i < n; i++) {
          A[i] = In.readInt(); 
        }
        
        Out.println(triangles(n, A));
        
        // Uncomment this line if you want to read from a file
        In.close();
    }
    
    public static long triangles(int n, int []A)
    {
      // n: length of A
      // A: an array of positive integers
      long triangles = 0; // the number of triangles may not fit into the int type
      
      // sort array
      mergeSort(A);
      
      for (int i = 0; i < n - 2; ++i) {
        for (int j = i + 1; j < n - 1; ++j) {
          int index = binSearch(A, A[i] + A[j], j + 1, n - 1);
          
          // it ain't pretty but it's working
          while (index + 1 < n && A[i] + A[j] >= A[index + 1]) {
            ++index;
          }
          
          if (A[i] + A[j] >= A[index]) {
            triangles += index - j;
          }
          
        }
      }
      
      return triangles;
    }
  
  public static void mergeSort(int[] A) {
    int n = A.length - 1;
    
    int length = 1;
    
    while (length <= n) {
      int l = 0;
      
      while (l < n) {
        int m = Math.min(l + length - 1, n);
        int r = Math.min(l + 2 * length - 1, n);
        
        merge(A, l, m, r);
        
        l += 2 * length;
      }
      
      length *= 2;
    }
    
  }
  
  public static void merge(int[] A, int l, int m, int r) {
    int lSize = m - l + 1;
    int rSize = r - m;
    
    int[] left = new int[lSize];
    int[] right = new int[rSize];
    
    
    for (int i = 0; i < lSize; ++i) {
      left[i] = A[l + i];
    }
    
    for (int i = 0; i < rSize; ++i) {
      right[i] = A[m + 1 + i];
    }
    
    int lP = 0;
    int rP = 0;
    int iP = l;
    
    while (lP < lSize && rP < rSize) {
      if (left[lP] < right[rP]) {
        A[iP] = left[lP];
        ++lP;
        ++iP;
      } else {
        A[iP] = right[rP];
        ++rP;
        ++iP;
      }
    }
    
    while (lP < lSize) {
      A[iP] = left[lP];
      ++lP;
      ++iP;
    }
    
    while (rP < rSize) {
      A[iP] = right[rP];
      ++rP;
      ++iP;
    }
    
  }
  
  public static int binSearch(int[] A, int key, int l, int r) {
    while (l <= r) {
      int m = (l + r) / 2;
      
      if (A[m] == key) {
        return m;
      }
      
      if (key < A[m]) {
        r = m - 1;
      } else {
        l = m + 1;
      }
      
    }
    
    return (l + r) / 2;
  }
  
}
