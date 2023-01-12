import java.util.ArrayList;
import java.util.LinkedList;
// ADDITIONAL IMPORTS ARE NOT ALLOWED

class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int n = In.readInt(); //number of vertices
    int edges = In.readInt();
    int x = In.readInt();
    int y = In.readInt();
    ArrayList<ArrayList<Integer>> G = new ArrayList<ArrayList<Integer>>(n);

    for (int i = 0; i < n; i++) {
      G.add(new ArrayList<Integer>());
    }
    
    for (int i = 0; i < edges; i++) {
      int src = In.readInt();
      int trg = In.readInt();
      G.get(src).add(trg);
    }

    Out.println(countInBetween(n, G, x, y));
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  public static int countInBetween(int n, ArrayList<ArrayList<Integer>> G, int x, int y) {
    
    // get reversed graph
    ArrayList<ArrayList<Integer>> revG = reverse(n, G);
    
    // get reachable vertices
    boolean[] xS = bfs(G, x);
    // here with reversed graph
    boolean[] yS = bfs(revG, y);
    
    // count those reachable from both
    int count = 0;
    
    for (int i = 0; i < n; ++i) {
      if (xS[i] && yS[i]) {
        ++count;
      }
    }
    
    return count;
  }
  
  public static boolean[] bfs(ArrayList<ArrayList<Integer>> G, int s) {
    // track visited vertices
    boolean[] visited = new boolean[G.size()];
    
    // queue
    LinkedList<Integer> queue = new LinkedList<>();
    queue.add(s);
    
    // search
    while(!queue.isEmpty()) {
      
      int v = queue.poll();
      visited[v] = true;
      
      // add those not already visited
      for (int u : G.get(v)) {
        if (!visited[u]) {
          queue.add(u);
        }
      }
      
    }
    
    return visited;
    
  }
  
  // reverse graph
  public static ArrayList<ArrayList<Integer>> reverse(int n, ArrayList<ArrayList<Integer>> G) {
    ArrayList<ArrayList<Integer>> newG = new ArrayList<>();
    
    // add entries to new graph
    for (int i = 0; i < n; ++i) {
      newG.add(new ArrayList<>());
    }
    
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < G.get(i).size(); ++j) {
        newG.get(G.get(i).get(j)).add(i);
      }
    }
    
    return newG;
    
  }
}
