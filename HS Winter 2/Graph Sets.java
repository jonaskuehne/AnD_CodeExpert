import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file.
    // Also make sure that the lines are commented when testing with the 
    // "Test" button or when submitting. Otherwise your code may exceed the
    // time liimt.
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int tests = In.readInt(); // number of tests
    for (int t = 0; t < tests; t++) {
      int n = In.readInt(); // number of nodes
      int m = In.readInt(); // number of edges
      int q = In.readInt(); // number of queries
      
      int[][] edgeArray = new int[m][2]; // array of edges
      for (int i = 0; i < m; i++) {
        edgeArray[i][0] = In.readInt();
        edgeArray[i][1] = In.readInt();
      }
      
      Graph G = new Graph(n, m, edgeArray); // graph
      G.initialize();
      
      // queries
      for (int i = 0; i < q; i++) {
        int type = In.readInt();
        if (type == 1) {
          // hasCycle
          Out.println(G.hasCycle());
        } else if (type == 2) {
          // hasCycleWithoutNodeZero
          Out.println(G.hasCycleWithoutNodeZero());
        } else if (type == 3) {
          // isSameSet
          int x = In.readInt();
          int y = In.readInt();
          Out.println(G.isSameSet(x, y));
        } else if (type == 4) {
          // getShortestPath
          int x = In.readInt();
          int y = In.readInt();
          Out.println(G.getShortestPath(x, y));
        }
      }
    }
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
}

class Graph {
  private int n;                // number of nodes
  private int m;                // number of edges
  private int[] degree;         // degrees[i]: the degree of vertex i
  private int[][] edges;        // edges[i][j]: the endpoint of the j-th edge of vertex i
  private boolean[] visited;    // visited[i]: whether node i has been visited
  private int[] rep;
  private boolean computedRep;
  private int[] d;
  private boolean computedD;
  
  Graph(int n, int m, int[][] edgeArray){
    this.n = n;
    this.m = m;
    degree = new int[n];
    edges = new int[n][];
    visited = new boolean[n];
    
    for (int i = 0; i < m; i++) {
      degree[edgeArray[i][0]]++;
      degree[edgeArray[i][1]]++;
    }
    for (int i = 0; i < n; i++) {
      edges[i] = new int[degree[i]];
      degree[i] = 0;
    }
    for (int i = 0; i < m; i++) {
      edges[edgeArray[i][0]][degree[edgeArray[i][0]]++] = edgeArray[i][1];
      edges[edgeArray[i][1]][degree[edgeArray[i][1]]++] = edgeArray[i][0];
    } 
  }
  
  // init stuff, just markers if already computed
  // method seems only to get called at beginning of type of query
  public void initialize() {
    computedRep = false;
    computedD = false;
  }
  
  public int hasCycle() {
    // new array
    visited = new boolean[n];
    return cycleHelper(true, 0);
  }
  
  // essentially bfs, reuse for next task
  private int cycleHelper(boolean withZero, int start) {
    // keep track of last node as n1 <-> n2 is no cycle
    int[] last = new int[n];
    Queue<Integer> q = new LinkedList<>();
    q.add(start);
    // since start at 0
    last[start] = -1;
    
    while (!q.isEmpty()) {
      int node = q.poll();
      visited[node] = true;
      
      for (int next : edges[node]) {
        
        if (!visited[next]) {
          last[next] = node;
          q.add(next);
        // now check if other last node
        } else if (last[node] != next) {
          
          // used for task 1
          if (withZero) {
            return 1;
          // used for task 2
          } else if (next != 0 && node != 0){
            return 1;
          }
          
        }
        
      }
      
    }
    // no cycle
    return 0;
  }
  
  public int hasCycleWithoutNodeZero() {
    visited = new boolean[n];
    visited[0] = true;
    // run bfs for all nodes adj. to 0
    for (int node : edges[0]) {
      if (!visited[node]) {
        // mark false for method
        if (cycleHelper(false, node) == 1) {
          return 1;
        } 
      }
    }
    
    // no cycle
    return 0;
  }
  
  // compute representants for each set
  public int isSameSet(int x, int y) {
    
    // not computed yet
    if (!computedRep) {
      // new array
      rep = new int[n];
      // mark as computed
      computedRep = true;
      
      // call tweaked dfs
      for (int node : edges[0]) {
        if (!visited[node]) {
          rep[node] = node;
          repDFS(node, node);
        }
      }
      
    }
    
    // same group?
    if (rep[x] == rep[y]) {
      return 1;
    } else {
      return 0;
    }
    
  }
  
  private void repDFS(int x, int source) {
    visited[x] = true;
    for (int i = 0; i < degree[x]; i++) {
      if (!visited[edges[x][i]] && edges[x][i] != 0) {
        // only difference to normal dfs, store representant of group
        rep[edges[x][i]] = source;
        repDFS(edges[x][i], source);
      }
    }
  }
  
  // only paths between nodes in different groups -> path must go through 0
  public int getShortestPath(int x, int y) {
    
    // not computed yet, normal bfs
    if(!computedD) {
      // new arrays
      d = new int[n];
      visited = new boolean[n];
      visited[0] = true;
      // mark as computed
      computedD = true;
      
      // queue
      Queue<Integer> q = new LinkedList<>();
      q.add(0);
      
      // bfs
      while(!q.isEmpty()) {
        int node = q.poll();
        // all adj
        for (int next : edges[node]) {
          if (!visited[next]) {
            // mark here since undirected
            visited[next] = true;
            d[next] = d[node] + 1;
            q.add(next);
          }
        }
      }
    }
    
    // now just sum of distance
    return d[x] + d[y];
  }
}
