class Main {
    public static void main(String[] args) {
      
        // Uncomment this line if you want to read from a file
        // In.open("public/example.in");
        // Out.compareTo("public/example.out");

        int n = In.readInt(); //Read the size of Array;
        int []A=new int[n];
        
        // Read A
        for(int i=0;i<n;i++){
          A[i]=In.readInt(); 
        }
        
        int m = In.readInt(); // Read the number of queries
        
        // Queries
        for(int i=0;i<m;i++){
          int k_l = In.readInt(); // Read the left integer
          int k_r = In.readInt(); // Read the right integer;
          Out.println(Range_Counting(A,n, k_l, k_r));
        }
          
        
                
        // Uncomment this line if you want to read from a file
        // In.close();
   
    }
    
    public static int Range_Counting(int A[], int n, int k_l, int k_r) {
      
        int lI = binSearch(A, k_l);
        
        // correcture if index not found
        if (A[lI] < k_l) {
          ++lI;
        }
        
        int rI = binSearch(A, k_r);
    
        return rI - lI + 1;
    }
    
    // not found: returns index of next smaller element
    public static int binSearch(int[] A, int key) {
      int l = 0;
      int r = A.length - 1;
      
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
