import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;


class Main {
  public static void main(String[] args) {
    // Uncomment this line if you want to read from a file
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    int n = In.readInt(); // number of nodes
    int m = In.readInt(); // number of edges
    
    ArrayList<Integer>[] edges = new ArrayList[n];
    for (int i = 0; i < n; i++) {
      edges[i] = new ArrayList<Integer>();
    }
    
    // edges
    for(int i = 0; i < m; i++) {
      int node1 = In.readInt();
      int node2 = In.readInt();
      edges[node1].add(node2);
      edges[node2].add(node1);
    }
    
    Out.println(getMinDistance(n, edges));
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  public static int getMinDistance(int n, ArrayList<Integer>[] edges) {
    // just take the ones with odd degree -> must be even number of those (HS-lemma)
    boolean[] noisy = new boolean[n];
    for (int i = 0; i < n; ++i) {
      if (edges[i].size() % 2 != 0) {
        noisy[i] = true;
      }
    }
    
    // new graph, add new node
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    
    for (int i = 0; i <= n; ++i) {
      graph.add(new ArrayList<>());
    }
    
    // replace all edges from noisy vertices to edges from new vertex
    for (int from = 0; from < edges.length; ++from) {
      for (int to : edges[from]) {
        
        // both nodes marked -> don't matter
        if (noisy[to] && noisy[from]) {
          // nothing hehe
        // "to" noisy -> edge from new to from
        } else if (noisy[to]) {
          graph.get(n).add(from);
        // "from" noisy -> edge from new to to
        } else if (noisy[from]) {
          graph.get(n).add(to);
        // keep those, add both ways since undirected
        } else {
          graph.get(from).add(to);
          graph.get(to).add(from);
        }
      }
    }

    // bfs    
    int[] d = bfs(graph, n);
    
    // get max distance
    int max = d[0];
    for (int i : d) {
      max = Math.max(max, i);
    }
    
    return max;
  }
  
  // slightly modified as edges potentially duplicates
  public static int[] bfs(ArrayList<ArrayList<Integer>> graph, int start) {
    boolean[] seen = new boolean[graph.size()];
    
    int[] d = new int[graph.size()];
    
    LinkedList<Integer> queue = new LinkedList<>();
    
    queue.add(start);
    // mod: mark already here
    seen[start] = true;
    
    while (!queue.isEmpty()) {
      int curr = queue.poll();
      
      for (int next : graph.get(curr)) {
        if (!seen[next]) {
          // mod: mark here to avoid duplicates
          seen[next] = true;
          queue.add(next);
          d[next] = d[curr] + 1;
        }
      }
      
    }
    return d;
  }
  
}
