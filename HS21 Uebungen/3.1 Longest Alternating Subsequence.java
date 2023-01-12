class Main {
    public static void main(String[] args) {
        // Uncomment this line if you want to read from a file
        // In.open("public/example.in");
        // Out.compareTo("public/example.out");

        int n = In.readInt(); //Read the size of Array;
        int []A = new int[n];
        
        // Read A
        for(int i = 0; i < n; i++) {
          A[i] = In.readInt(); 
        }
        
        Out.println(longest_alternating_subsequence(n, A));
        
        // Uncomment this line if you want to read from a file
        // In.close();
    }
    
    // O(n) flex
    public static int longest_alternating_subsequence(int n, int[] A) {
      int longestSeq = 2;
      int last = A[1];
      boolean lastUp = A[0] < A[1];
      
      for (int i = 2; i < n; ++i) {
        // need to go down this time
        if (lastUp) {
          // we do!
          if (A[i] < last) {
            ++longestSeq;
            last = A[i];
            // up next time
            lastUp = false;
          // no, but can we do better?
          } else {
            // prob. higher that next one smaller if last larger
            last = Math.max(last, A[i]);
          }
        // need to go up this time
        } else {
          // we do!
          if (last < A[i]) {
            ++longestSeq;
            last = A[i];
            // down next time
            lastUp = true;
          // no, but can we do better?
          } else {
            // prob. higher that next one larger if last smaller
            last = Math.min(last, A[i]);
          }
        }
      }
      
      return longestSeq;
    }
  
}
