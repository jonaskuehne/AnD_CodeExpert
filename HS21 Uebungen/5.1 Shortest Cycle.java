import java.io.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.Math;


class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int n = In.readInt();     // number of vertices
    int m = In.readInt();     // number of edges
    
    // The following two arrays stores the information of edges
    int[][] edgeArray = new int[m][3];
    
    // Read edges
    for (int i = 0; i < m; i++) {
      edgeArray[i][0] = In.readInt();  // one endpoint
      edgeArray[i][1] = In.readInt();  // the other endpoint
      edgeArray[i][2] = In.readInt();  // weight
    }
    
    
    Graph G = new Graph(n, m, edgeArray);
    Out.println(G.shortestCycle());
  
    // Uncomment the following line if you want to read from a file
    // In.close();
  }
}

class Graph {
  private int n;              // number of vertices
  private int m;              // number of edges
  private int[] degrees;      // degrees[i]: the degree of vertex i
  private int[][] edges;      // edges[i][j]: the endpoint of the j-th edge of vertex i
  private int[][] weights;    // weights[i][j]: the weight of the j-th edge of vertex i
  
  Graph(int n, int m, int[][] edgeArray)
  {
    this.n = n;
    this.m = m;
    degrees = new int[n];
    
    for (int i = 0; i < n; i++) {
      degrees[i]=0;
    }
   
    for (int i = 0; i < m; i++) {
      degrees[edgeArray[i][0]]++;
      degrees[edgeArray[i][1]]++;
    }
    
    edges = new int[n][];
    weights = new int[n][];
      
    for (int i = 0; i < n; i++) {
      if (degrees[i] != 0) {
        edges[i] = new int[degrees[i]];
        weights[i] = new int[degrees[i]];
        degrees[i] = 0;
      } else {
        edges[i] = null;
        weights[i] = null;
      }
    }
    
    for (int i=0; i<m; i++) {
      edges[edgeArray[i][0]][degrees[edgeArray[i][0]]] = edgeArray[i][1];
      edges[edgeArray[i][1]][degrees[edgeArray[i][1]]] = edgeArray[i][0];
      weights[edgeArray[i][0]][degrees[edgeArray[i][0]]] = edgeArray[i][2];
      weights[edgeArray[i][1]][degrees[edgeArray[i][1]]] = edgeArray[i][2];
      degrees[edgeArray[i][0]]++;
      degrees[edgeArray[i][1]]++;
    } 
  }
  
  public int shortestCycle() {
    boolean seen[] = new boolean[n];
    seen[0] = true;
    
    int[] d = new int[n];
    Arrays.fill(d, Integer.MAX_VALUE);
    d[0] = 0;
    
    int[] rep = new int[n];
    Arrays.fill(rep, -1);
    rep[0] = 0;
    
    PriorityQueue<Pair> heap = new PriorityQueue<>();
    
    for (int i = 0; i < degrees[0]; ++i) {
      heap.add(new Pair(edges[0][i], weights[0][i]));
      rep[edges[0][i]] = edges[0][i];
      d[edges[0][i]] = weights[0][i];
    }
    
    int minCycle = Integer.MAX_VALUE;
    
    // normal dijkstra
    while (!heap.isEmpty()) {
      Pair p = heap.poll();
      
      if (!seen[p.index]) {
        seen[p.index] = true;
        
        for (int nextInd = 0; nextInd < degrees[p.index]; ++nextInd) {
          int next = edges[p.index][nextInd];
          
          if (!seen[next]) {
            int potD = d[p.index] + weights[p.index][nextInd];
            
            if (potD < d[next]) {
              d[next] = potD;
              heap.add(new Pair(next, d[next]));
              rep[next] = rep[p.index];
            }
          // new stuff, essentially checks if other "source" from start, then start must be in cycle
          } else {
            if (rep[next] != rep[p.index] && next != 0) {
              minCycle = Math.min(minCycle, d[next] + d[p.index] + weights[p.index][nextInd]);
            }
          }
        }
        
      }
    }
    
    return minCycle;
  }
}

class Pair implements Comparable<Pair> {
  public int index;
  public int value;

  public Pair(int index, int value) {
      this.index = index;
      this.value = value;
  }

  @Override
  public int compareTo(Pair other) {
    if (this.value < other.value){
      return -1;
    } else if (this.value == other.value){
      return 0;
    } else {
      return 1;
    }
  }
}
