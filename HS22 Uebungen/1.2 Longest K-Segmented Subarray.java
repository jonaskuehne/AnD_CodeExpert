class Main {
    public static void main(String[] args) {
        // Uncomment this line if you want to read from a file
        // In.open("public/example.in");
        // Out.compareTo("public/example.out");

        int n = In.readInt(); //Read the size of Array;
        int []A = new int[n + 1];
        
        int K = In.readInt(); // Read size of segments
        
        // Read A
        for(int i = 1; i <= n; i++) {
          A[i] = In.readInt(); 
        }
        
        Out.println(longestKSegmentedSubarray(n, A, K));
        
        // Uncomment this line if you want to read from a file
        // In.close();
    }
    
    public static int longestKSegmentedSubarray(int n, int[] A, int k) {
      // get lengths of sequences
      int[] seq = new int[n];
      // go through array
      int arrInd = 0;
      // store last element
      int last = A[1];
      // keep count
      int seqCount = 1;
      // go through array
      for (int i = 2; i <= n; ++i) {
        // new seq
        if (last != A[i]) {
          // write
          seq[arrInd] = seqCount;
          ++arrInd;
          // reset count
          seqCount = 1;
          // reset last element
          last = A[i];
        // seq continues
        } else {
          ++seqCount;
        }
      }
      // take last one
      seq[arrInd] = seqCount;
      
      int maxLength = 0;
      int currLength = 0;
      // go through sequence lengths
      for (int i = 0; i < seq.length; ++i) {
        // last one
        if (seq[i] == 0) {
          break;
        }
        // can still take
        if (seq[i] % k == 0) {
          currLength += seq[i];
        // run finished
        } else {
          // take whats left
          currLength += seq[i] - (seq[i] % k);
          // better than last?
          maxLength = Math.max(currLength, maxLength);
          // continue with whats left
          currLength = seq[i] - (seq[i] % k);
        }
      }
      
      return maxLength;
    }
  
}
