import java.util.Arrays;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
// ADDITIONAL IMPORTS ARE NOT ALLOWED

class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int n = In.readInt(); //number of vertices
    int[][] A = new int[n][n];

    for (int i = 0; i < n; i++) {
      for(int j = 0; j < n; j++){
        int read = In.readInt();
        A[i][j] = read;
      }
    }
    Out.println(getMinimumCost(n, A));
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  // You can use this custom class if you want in your implementation.
  // The Comparable part of this class allows you to use java built-in
  // sort methods. However, you need to write your own compareTo method.
  static class Tuple implements Comparable<Tuple>{
    
    int x;
    int y;
    int cost;
    
    public Tuple(int x, int y, int cost) {
      this.x = x;
      this.y = y;
      this.cost = cost;
    }
    
    // In case you want to sort a custom class you need to have this method
    @Override public int compareTo(Tuple other){
      // in case this is > than other, return a positive number,
      // otherwise a negative number
      return Integer.valueOf(this.cost).compareTo(other.cost);
    }
  }
  
  public static int getMinimumCost(int n, int[][] A) {
    // track progress
    boolean[][] done = new boolean[n][n];
    int[][] d = new int[n][n];
    
    // init
    for (int i = 0; i < n; ++i) {
      Arrays.fill(d[i], Integer.MAX_VALUE);
    }
    
    d[0][0] = 0;
    
    PriorityQueue<Tuple> q = new PriorityQueue<>();
    
    q.add(new Tuple(0, 0, 0));
    
    // normal dijkstra
    while (!q.isEmpty()) {
      Tuple t = q.poll();
      int x = t.x;
      int y = t.y;
      
      if (x == n - 1 && y == n - 1) {
        break;
      }
      
      if (!done[x][y]) {
        
        done[x][y] = true;
        
        // check possible moves
        if (legal(x - 1, y, n)) {
          add(x, y, x - 1, y, d, done, q, A);
        }
        
        if (legal(x + 1, y, n)) {
          add(x, y, x + 1, y, d, done, q, A);
        }
        
        if (legal(x, y - 1, n)) {
          add(x, y, x, y - 1, d, done, q, A);
        }
        
        if (legal(x, y + 1, n)) {
          add(x, y, x, y + 1, d, done, q, A);
        }
        
      }
      
    }
    
    return d[n - 1][n - 1];
  }
  
  // adds tuple to heap if necessary
  public static void add(int oldX, int oldY, int newX, int newY, int[][] d, boolean[][] done, PriorityQueue<Tuple> q, int[][] A) {
    if (!done[newX][newY]) {
      
      int store = d[newX][newY];
      
      d[newX][newY] = Math.min(d[newX][newY], d[oldX][oldY] + A[newX][newY]);
      
      if (store > d[newX][newY]) {
        q.add(new Tuple(newX, newY, d[newX][newY])); 
      }
    }
  }
  
  // checks if index is legal
  public static boolean legal(int x, int y, int n) {
    return x >= 0 && y >= 0 && x < n && y < n;
  }
  
}
