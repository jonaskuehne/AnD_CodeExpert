import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
// ADDITIONAL IMPORTS ARE NOT ALLOWED

class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file
    // In.open("public/small.in");
    // Out.compareTo("public/small.out");

    int n = In.readInt(); // number of vertices
    int m = In.readInt(); // number of edges
    int[] vertexCost = new int[n]; // cost of vertices
    int[] edge1 = new int[m]; // first nodes of the edges
    int[] edge2 = new int[m]; // second nodes of the edges
    int[] edgeCost = new int[m]; // costs of the edges
    
    for (int i = 0; i < n; i++) {
      vertexCost[i] = In.readInt();
    }
    for (int i = 0; i < m; i++) {
      edge1[i] = In.readInt();
      edge2[i] = In.readInt();
      edgeCost[i] = In.readInt();
    }

    Out.println(getCost(n, m, edge1, edge2, vertexCost, edgeCost));
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  // class to represent edges
  static class Edge {
    // class vars
    int from;
    int to;
    int cost;
    // ctors
    public Edge(int from, int to, int cost) {
      // set stuff
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }
  
  // does stuff
  public static int getCost(int n, int m, int[] edge1, int[] edge2, int[] vertexCost, int[] edgeCost) {
    // get graph representation
    ArrayList<Edge> graph = getGraph(n, m, edge1, edge2, edgeCost, vertexCost);
    
    // representants, n + additional vertex ("src")
    int[] rep = new int[n + 1];
    // list for members
    ArrayList<ArrayList<Integer>> members = new ArrayList<>();
    // initialize
    for (int i = 0; i < n + 1; ++i) {
      // represents itself
      rep[i] = i;
      // init list
      members.add(new ArrayList<>());
      // add
      members.get(i).add(i);
    }
    
    // store result
    int res = 0;
    
    // go through edges, are sorted
    for (Edge e : graph) {
      // other zhk?
      // yes
      if (rep[e.from] != rep[e.to]) {
        // add to result
        res += e.cost;
        // which one smaller?
        if (members.get(rep[e.from]).size() < members.get(rep[e.to]).size()) {
          // unite
          unite(rep, e.from, e.to, members);
        } else {
          // unite
          unite(rep, e.to, e.from, members);
        }
      }
      // else do nothing 
    }
    
    return res;
  }
  
  // unite
  public static void unite(int[] rep, int from, int to, 
      ArrayList<ArrayList<Integer>> members) {
    // store old rep
    int oldRep = rep[from];
    // go through members
    for (int mem : members.get(oldRep)) {
      // now also member
      members.get(rep[to]).add(mem);
      // change rep
      rep[mem] = rep[to];
    }
    
    // because memoryflex
    members.set(oldRep, null);
  }
    
  // creates adj. list
  public static ArrayList<Edge> getGraph(int n, int m, int[] edge1, int[] edge2, 
      int[] edgeCost, int[] vertexCost) {
    
    // res object
    ArrayList<Edge> graph = new ArrayList<>();
    // fill
    for (int i = 0; i < m; ++i) {
      // use very nice edge class
      graph.add(new Edge(edge1[i], edge2[i], edgeCost[i]));
    }
    
    // add vertex costs as additional edges
    for (int i = 0; i < n; ++i) {
      // edge to "new" vertex n
      graph.add(new Edge(i, n, vertexCost[i]));
    }
    
    // sort
    graph.sort(new Comparator<Edge>() {
      public int compare(Edge e1, Edge e2) {
        return Integer.valueOf(e1.cost).compareTo(e2.cost);
      }
    });
    
    return graph;
  }
}
