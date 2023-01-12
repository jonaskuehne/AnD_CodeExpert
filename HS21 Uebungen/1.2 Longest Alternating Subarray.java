class Main {
    public static void main(String[] args) {
        // Uncomment this line if you want to read from a file
        // In.open("public/example.in");
        // Out.compareTo("public/example.out");

        int n = In.readInt(); //Read the size of Array;
        int []A = new int[n + 1];
        
        // Read A
        for(int i = 1; i <= n; i++) {
          A[i] = In.readInt(); 
        }
        
        Out.println(longest_alternating_subarray(n, A));
        
        // Uncomment this line if you want to read from a file
        // In.close();
    }
    
    public static int longest_alternating_subarray(int n, int[] A) {
      // init
      int max = 2;
      int current = 2;
      boolean lastUp;
      
      // check how sequence started
      if (A[1] < A[2]) {
        lastUp = true;
      } else {
        lastUp = false;
      }
      
      for (int i = 3; i <= n; ++i) {
        // continue case 1
        if (lastUp && A[i - 1] > A[i]) {
          lastUp = false;
          ++current;
        // continue case 2
        } else if (!lastUp && A[i - 1] < A[i]) {
          lastUp = true;
          ++current;
        // start new sequence
        } else {
          if (A[i - 1] < A[i]) {
            lastUp = true;
          } else {
            lastUp = false;
          }
          current = 2;
        }
        
        max = Math.max(max, current);
        
      }
      
      
      return max;
    }
  
}
