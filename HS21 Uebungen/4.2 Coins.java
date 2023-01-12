import java.util.Arrays;
import java.util.ArrayList;


class Main {
  private static int n;
  private static int[] E1;
  private static int[] E2;

  public static void main(String[] args) {
    // Uncomment this line if you want to read from a file
    // In.open("public/example.in");
    // Out.compareTo("public/example.out");

    n = In.readInt(); // number of nodes
    E1 = new int[n - 1];
    E2 = new int[n - 1];
    
    // edges
    for(int i = 0; i < n - 1; i++) {
      // edge between E1[i] and E2[i], such that E1[i] is the parent of E2[i]
      E1[i] = In.readInt();
      E2[i] = In.readInt();
    }
    
    Out.println(getMinCoins());
    
    // Uncomment this line if you want to read from a file
    // In.close();
  }
  
  public static int getMinCoins() {
    // get graph representation
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    
    for (int i = 0; i < n; ++i) {
      graph.add(new ArrayList<>());
    }
    
    for (int i = 0; i < E1.length; ++i) {
      graph.get(E1[i]).add(E2[i]);
    }
    
    int[] num = new int[n];
    
    kindaDFS(graph, 0, num);
    
    return num[0];
  }
  
  public static void kindaDFS(ArrayList<ArrayList<Integer>> graph, int node, int[] num) {
    int size = graph.get(node).size();
    
    if (size == 0) {
      num[node] = 1;
      return;
    }
    
    int[] needs = new int[size];
    int i = 0;
    for (int next : graph.get(node)) {
      kindaDFS(graph, next, num);
      
      needs[i] = num[next];
      ++i;
    }
    
    Arrays.sort(needs);
    
    int init = Math.max(size, needs[needs.length - 1]);
    
    int initC = init;
    
    for (int k = needs.length - 1; k >= 0; --k) {
      if (initC < needs[k]) {
        ++initC;
        ++init;
      }
      --initC;
    }
    
    num[node] = init;
  }
  
}
