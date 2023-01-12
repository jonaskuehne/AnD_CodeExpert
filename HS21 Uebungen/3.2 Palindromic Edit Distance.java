class Main {
    public static void main(String[] args) {
      
        // Uncomment this line if you want to read from a file
        // In.open("public/custom.in");
        // Out.compareTo("public/custom.out");

        int m = In.readInt(); //Read the number of sequences
        
        
        for(int i=0;i<m;i++){
          
          int n = In.readInt(); // Read the number of charachters of the i-th sequence 
          String SA=In.readString();        // Read A as a string
          
          char[] A=new char[n];
          for(int j=0;j<n;j++){
            A[j]=SA.charAt(j);
          }

          Out.println(Palindromic_Edit_Distance(A,n));
        }
        
       
                
        // Uncomment this line if you want to read from a file
        // In.close();
   
    }
    
    public static int Palindromic_Edit_Distance(char[] A, int n) {
      // table
      int[][] T = new int[n + 1][n + 1];
      // reverse string for palindrome
      char[] revA = new char[n];
      // create
      for (int i = 0; i < n; ++i) {
        revA[i] = A[n - 1 - i];
      }
      
      // init
      // since same length okay
      for (int i = 0; i <= n; ++i) {
        T[0][i] = i;
        T[i][0] = i;
      }
      
      // fill, normal alg.
      for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= n; ++j) {
          // check if same
          int same;
          if (revA[i - 1] == A[j - 1]) {
            same = 0;
          } else {
            same = 1;
          }
          
          // get min, all ops same weight
          T[i][j] = Math.min(T[i - 1][j - 1] + same, Math.min(T[i - 1][j] + 1, T[i][j - 1] + 1));
          
        }
      }
      
      // get min
      int min = Integer.MAX_VALUE;
      
      /* read diagonal bottom left to top right and elements right above / below
      makes sense since T[i][j] is MED a1...ai to b1...bj
      since we have reverse A -> a1...ai to an...aj
      we want the whole word, hence i = j
      also below / above if word has odd number of letters
      */
      for (int i = 0; i <= n; ++i) {
        min = Math.min(min, T[n - i][i]);
        if (i != n) {
          min = Math.min(min, T[n - i - 1][i]);
          min = Math.min(min, T[n - i][i + 1]);
        }
      }
      
      
      return min;
    
    }

   
}
