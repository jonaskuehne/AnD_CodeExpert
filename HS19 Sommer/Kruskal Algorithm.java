import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;
import java.util.Arrays;
import java.util.Comparator;

class Main {
    public static void main(String[] args) {
        // Uncomment the following two lines if you want to read from a file
        // In.open("public/tiny.in");
        // Out.compareTo("public/tiny.out");

        int T = In.readInt();
        for (int test = 0; test < T; test += 1) {
          //
          // Read the number of vertices and edges
          //
          int V = In.readInt();
          int E = In.readInt();
          //
          // Create the graph representation
          // 
          Graph G = new Graph(V, E);
          //
          // Read all edges in the graph
          //
          for (int i = 0; i < E; i += 1) {        
            //
            // Read the vertices of the edge, as well as its
            // weight and add the edge in graph G.
            //
            int u = In.readInt();
            int v = In.readInt();
            int w = In.readInt();        
            G.edges[i] = new Edge(u, v, w);
          }
          Out.println(G.kruskal());
        }    
        
    
        // Uncomment the following line if you want to read from a file
        // In.close();
    }

   
}


//
// Graph is represented with number of vertices (V)
// number of edges (E) and the set of all edges,
// represented as an array of Edge instances.
//
class Graph {
    public int V;       // number of vertices in the graph
    public int E;      // number of edges  in the graph
    public Edge[] edges;  // each edge in the graph  
    //
    // Graph initialization constructor 
    //
    public Graph(int V, int E) {
      this.V     = V;
      this.E     = E;
      this.edges   = new Edge[E];
    }
    
    
    //
    // Calculate the minimum spanning tree (MST) using the Kruskal's algorithm
    // and return the cost of the graph
    //
    public long kruskal() {
      //
      // The cost of the MST will fit inside a long type.
      //
      long cost = 0;
      
      //
      // Complete the implementation of Kruskal's algorithm. Once you
      // compute the MST, compute the cost of the graph i.e. compute
      // the sum of the weights of all edges in the MST.
      //
      // Use the provided UnionFind data-structure available below.
      //
      
  
      //
      // Sort the edges
      //
      Arrays.sort(edges, new Comparator<Edge>() {
          public int compare(Edge o1, Edge o2) {
              return o1.w - o2.w;
          }
      });
      
      // create new object
      UnionFind u = new UnionFind(V);
      
      // for each edge
      for (Edge e : edges) {
        // if different representants
        if (u.find(e.u) != u.find(e.v)) {
          // add cost
          cost += e.w;
          // union
          u.union(e.u, e.v);
        }
        
      }
      
      // whole cost
      return cost;
   
    }
  
  
}


// Representation of an edge in the graph: 
// u and v represent the vertices of the edge
// and w represents its weight
//
class Edge {    
    public int u;   // vertex u of the edge 
    public int v;   // vertex v of the edge
    public int w;   // the weight of the edge    
    //
    // Edge initialization constructor 
    //
    public Edge (int u, int v, int w) {
      this.u = u;
      this.v = v;      
      this.w = w;
    }
}



// Please modify the function such that both `union` and `find` operate in
// O(log(n)) time. Feel free to add fields, and modify the existing methods.
// Initialize the newly created fields in the constructor or the `create`
// method if necessary.
//
class UnionFind {
    int[] labels;
    // store members
    LinkedList<Integer>[] members;
    //
    // The constructor, just calls `create`
    //
    public UnionFind (int N) {
      create(N);
    }    
    //
    // Initialize the union-data structure, by creating a 
    // set for each element from [0, 1, ..., N - 1]. 
    //
    void create (int N) {
      labels = new int[N];
      // mimimi unsafe
      members = new LinkedList[N];
      for (int i = 0; i < N; i += 1) { 
        labels[i] = i;
        // new list
        members[i] = new LinkedList<>();
        // add
        members[i].add(i);
      }
    }    
    //
    // Determine which set a particular element belongs to. 
    // Return the label or the 'id' of the set for a given x
    //
    int find (int x) {
      return labels[x]; 
    }
    //
    // Connect or join two sets. In other words, change 
    // the family by replacing two sets, the one containing 
    // x and the one containing y, by a single set that is 
    // the union of these two sets. 
    //
    void union (int x, int y) {            
      int n  = labels.length;
      int sx = find(x); 
      int sy = find(y);
      
      // get smaller list
      int from;
      int to;
      if (members[sx].size() < members[sy].size()) {
        from = sx;
        to = sy;
      } else {
        from = sy;
        to = sx;
      }
      
      // change label of each member of old list and add to new list
      for (int member : members[from]) {
        labels[member] = to;
        members[to].add(member);
      }
      
      // memory flex
      members[from] = null;
    }
}
