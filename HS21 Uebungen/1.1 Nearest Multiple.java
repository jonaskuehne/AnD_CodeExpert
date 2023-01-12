class Main {
    public static void main(String[] args) {
      
        // Uncomment this line if you want to read from a file
        // In.open("public/example.in");
        // Out.compareTo("public/example.out");

        int n = In.readInt(); //Read the size of Array;
        int []A=new int[n+1];
        
        // Read A
        for(int i=1;i<=n;i++){
          A[i]=In.readInt(); 
        }
        
        int m = In.readInt(); // Read the number of queries
        
        // Queries
        for(int i=0;i<m;i++){
          int p = In.readInt(); // Read the position
          int k = In.readInt(); // Read the factor;
          Out.println(Nearest_Multiple(A,n,p,k));
        }
          
        
                
        // Uncomment this line if you want to read from a file
        // In.close();
   
    }
    
    public static int Nearest_Multiple(int[] A, int n, int p, int k) {
      
      // pointers
      int lP = p;
      int rP = p;
      
      // go left / right until found or one at end
      while (lP >= 1 && rP <= n) {
        if (A[lP] % k == 0) {
          return lP; 
        }
        
        if (A[rP] % k == 0) {
          return rP;
        }
        
        --lP;
        ++rP;
          
      }
      
      // go left until found
      while (lP >= 1) {
        if (A[lP] % k == 0) {
          return lP; 
        }
        --lP;
      }
      
      // go right until found
      while (rP <= n) {
        if (A[rP] % k == 0) {
          return rP;
        }
        ++rP;
      }
      
      // make compiler happy :)
      return 0;
    }

   
}
