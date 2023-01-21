import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;


class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file.
    // Also make sure that the lines are commented when testing with the 
    // "Test" button or when submitting. Otherwise your code may exceed the
    // time liimt.
    // In.open("public/custom.in");
    // Out.compareTo("public/custom.out");
        
    char operator = In.readChar();  // type of operation
    int N;                          // number of graphs 
    int n;                          // number of vertices
    int m;                          // number of edges
    int q;                          // number of querie
    int[][] edge_array;             // array of edges
    Graph G;                        // Graph
        
    switch (operator) {
      case 'P':
        // IsPath
        N = In.readInt();           // number of graphs 
        for (int l = 0; l < N; l++) {
          n = In.readInt();         // number of vertices
          m = In.readInt();         // number of edges
        
          edge_array = new int[m][2];
          for (int i = 0; i < m; i++) {
            edge_array[i][0] = In.readInt();
            edge_array[i][1] = In.readInt();
          }
          
          G = new Graph(n, m, edge_array);
          Out.println(G.IsPath());
        }
        break;
      case 'T':
        // EdgeOfTriangle
        n = In.readInt();           // number of vertices
        m = In.readInt();           // number of edges
        
        edge_array = new int[m][2];
        for(int i = 0; i < m; i++) {
          edge_array[i][0] = In.readInt();
          edge_array[i][1] = In.readInt();
        }
        
        G = new Graph(n, m, edge_array);
        q = In.readInt();           // number of querie
        for (int i = 0; i < q; i++) {
          int u = In.readInt();
          int v = In.readInt();
          Out.println(G.EdgeOfTriangle(u, v));
        }
        break;
      case 'C':
        // NumberOfComponents
        N = In.readInt();           // number of graphs 
        for(int l = 0; l < N; l++) {
          n = In.readInt();         // number of vertices
          m = In.readInt();         // number of edges
          
          edge_array = new int[m][2];
          for (int i = 0; i < m; i++) {
            edge_array[i][0] = In.readInt();
            edge_array[i][1] = In.readInt();
          }
          
          G = new Graph(n, m, edge_array);
          Out.println(G.NumberOfComponents());
        }
        break;
      case 'L':
        // LargestPerimeter
        n = In.readInt();           // number of vertices
        m = In.readInt();           // number of edges
        
        edge_array = new int[m][2];
        for (int i = 0; i < m; i++) {
          edge_array[i][0] = In.readInt();
          edge_array[i][1] = In.readInt();
        }
        
        G = new Graph(n, m, edge_array);
        q = In.readInt();           // number of querie
        for(int i = 0; i < q; i++) {
          int v = In.readInt();
          Out.println(G.LargestPerimeter(v));
        }
        break;
    }
    
    // Uncomment the following line if you want to read from a file
    // In.close();
  }
}

class Graph{
  private int n;              // number of vertices
  private int m;              // number of edges
  private int[] degrees;      // degrees[i]: the degree of vertex i
  private int[][] edges;      // edges[i][j]: the endpoint of the j-th edge of vertex i
  private boolean[] visited;  // visited[i]: whether vertex i has been visited
  
  Graph(int n, int m, int[][] edge_array) {
    this.n = n;
    this.m = m;
    degrees = new int[n];
    edges = new int[n][];
    visited = new boolean[n];
    
    for (int i = 0; i < n; i++) {
      degrees[i] = 0;
    }
    for (int i = 0; i < m; i++) {
      degrees[edge_array[i][0]]++;
      degrees[edge_array[i][1]]++;
    }
    for(int i = 0; i < n; i++) {
      if (degrees[i] != 0) {
        edges[i] = new int[degrees[i]];
        degrees[i] = 0;
      } else {
        edges[i] = null;
      }
    }
    for (int i = 0; i < m; i++) {
      edges[edge_array[i][0]][degrees[edge_array[i][0]]++] = edge_array[i][1];
      edges[edge_array[i][1]][degrees[edge_array[i][1]]++] = edge_array[i][0];
    } 
  }
  
  // use bfs twice, used this since I already needed it for "LargestPerimeter"
  public boolean IsPath() {
    // first time, don't care about list
    int[] d = BFS(0, new LinkedList<>());
    
    // find vertex furthest away from some random vertex
    // -> if path-graph must be "undirected source"
    int maxInd = 0;
    int maxD = 0;
    for (int i = 0; i < n; ++i) {
      if (d[i] > maxD) {
        maxInd = i;
        maxD = d[i];
      }
    }
    
    // run bfs again, now with potential source, store vertices in list
    LinkedList<Integer> nodeList = new LinkedList<>();
    d = BFS(maxInd, nodeList);
    
    // now check if they are ordered in inc. distance
    // yes -> path-graph
    
    // get first one
    int last = 0;
    nodeList.poll();
    
    for (int node : nodeList) {
      // no path-graph
      if (d[node] != last + 1) {
        return false;
      }
      
      // update
      last = d[node];
    }
    
    // all good
    return true;
  }
  
  // slightly tweaked bfs to also store nodes in list
  public int[] BFS(int start, LinkedList<Integer> nodeList) {
    int[] d = new int[n];
    boolean[] visited = new boolean[n];
    // mark here since undirected graph
    visited[start] = true;
    
    LinkedList<Integer> queue = new LinkedList<>();
    queue.add(start);
    
    while (!queue.isEmpty()) {
      int u = queue.poll();
      
      // only new part
      nodeList.add(u);
      
      for (int v : edges[u]) {
        if (!visited[v]) {
          
          // mark here since undirected
          visited[v] = true;
          
          d[v] = d[u] + 1;
          queue.add(v);
        }
      }
    }
    // distance array
    return d;
  }
  
  // use some boolean arrays
  public boolean EdgeOfTriangle(int u, int v) {
  
    boolean[] uEdge = new boolean[n];
    boolean[] vEdge = new boolean[n];
    
    // get directly reachable vertices from u
    for (int e : edges[u]) {
      uEdge[e] = true;
    }
    
    // get directly reachable vertices from v
    for (int e : edges[v]) {
      vEdge[e] = true;
    }
    
    // make sure not u and v
    uEdge[u] = false;
    uEdge[v] = false;
    vEdge[u] = false;
    vEdge[v] = false;
    
    // check if some vertex directly reachable from both
    // then we have a triangle
    for (int i = 0; i < n; ++i) {
      if (uEdge[i] && vEdge[i]) {
        return true;
      }
    }

    return false;
  }
  
  // just use their dfs implementation
  public int NumberOfComponents() {
    
    // reset visited since multiple tests
    visited = new boolean[n];
    
    // count runs of dfs
    int count = 0;
    
    for (int i = 0; i < n; ++i) {
      if (!visited[i]) {
        DFS(i);
        ++count;
      }
    }
    
    // result
    return count;
  }
  
  // use bfs to get distances
  public int LargestPerimeter(int v) {
    
    int[] d = BFS(v, new LinkedList<>());
    
    // store number of vertices with same distance from v
    int[] numD = new int[n];
    
    // count
    for (int dist : d) {
      ++numD[dist];
    }
    
    // get max
    int max = 0;
    for (int dist : numD) {
      max = Math.max(max, dist);
    }
    
    return max;
  }
  
  private void DFS(int v) {
    visited[v]=true;
    for (int i = 0; i < degrees[v]; i++) {
      if(visited[edges[v][i]] == false) {
        DFS(edges[v][i]);
      }
    }
  }
}
