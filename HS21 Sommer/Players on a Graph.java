import java.lang.Math;


class Main {
  public static void main(String[] args) {
    // Uncomment the following two lines if you want to read from a file.
    // In.open("public/0-example.in");
    // Out.compareTo("public/0-example.out");

    int tests = In.readInt(); // number of tests
    for (int t = 0; t < tests; t++) {
      int n = In.readInt(); // number of nodes
      int q = In.readInt(); // number of queries
      
      int[] edgeFrom = new int[n]; // array of edges
      for (int i = 0; i < n; i++) {
        edgeFrom[i] = In.readInt(); // there is an edge from i to edgeFrom[i]
      }
      
      Graph G = new Graph(n, edgeFrom); // graph
      G.initialize();
      
      // queries
      for (int i = 0; i < q; i++) {
        int type = In.readInt();
        if (type == 1) {
          // cycleLength
          Out.println(G.cycleLength());
        } else if (type == 2) {
          // distanceToCycle
          int x = In.readInt();
          Out.println(G.distanceToCycle(x));
        } else if (type == 3) {
          // firstNodeInCycle
          int x = In.readInt();
          Out.println(G.firstNodeInCycle(x));
        } else if (type == 4) {
          // distanceInCycle
          int x = In.readInt();
          int y = In.readInt();
          Out.println(G.distanceInCycle(x, y));
        }
      }
    }

    // Uncomment this line if you want to read from a file
    // In.close();
  }
}

class Graph {
  // nodes in cycle (in order)
  private int[] inCycle;
  // position of nodes in cycle (0 if not in cycle)
  private int[] posInCycle;
  // node is in cycle
  private boolean[] isInCycle;
  // length of cycle
  private int cycleLength;
  // distance of every node to cycle
  private int[] distToCycle;
  // nearest node in cycle from every node
  private int[] firstCycleNode;
  // keep track of done calculations (tasknumbers)
  private boolean t1Done;
  private boolean t23Done;
  
  private int n;             // number of nodes
  private int[] edgeFrom;    // edgeFrom[i]: there is an edge from i to edgeFrom[i]
  private int[] inDegree;    // inDegree[i]: the in-degree of i
  private int[][] edgesTo;   // edgesTo[i][j]: there is an edge to i from edgesTo[i][j]
                             // note: edgesTo[i] is an array of length inDegree[i]

  Graph(int n, int[] edgeFrom){
    this.n = n;
    this.edgeFrom = edgeFrom;
    
    // The following code computes inDegree and edgesTo.
    inDegree = new int[n];
    edgesTo = new int[n][];
    for (int i = 0; i < n; i++) {
      inDegree[edgeFrom[i]]++;
    }
    for (int i = 0; i < n; i++) {
      edgesTo[i] = new int[inDegree[i]];
      inDegree[i] = 0;
    }
    for (int i = 0; i < n; i++) {
      edgesTo[edgeFrom[i]][inDegree[edgeFrom[i]]++] = i;
    }
  }

  public void initialize() {
    // reset
    t1Done = false;
    t23Done = false;
  }
  
  // task 1
  public void computeInCycle() {
    cycleLength = 1;
    // track visited nodes
    boolean[] seen = new boolean[n];
    
    int node = 0;
    
    // guaranteed to encounter cycle from every node
    while (true) {
      // already seen -> cycle
      if (seen[node]) {
        // get size of cycle
        int next = edgeFrom[node];
        while (next != node) {
          ++cycleLength;
          next = edgeFrom[next];
        }
        
        // fill arrays now that we know the size of the cycle
        // description of arrays above
        inCycle = new int[cycleLength];
        isInCycle = new boolean[n];
        posInCycle = new int[n];
        // fill
        for (int i = 0; i < cycleLength; ++i) {
          posInCycle[node] = i;
          inCycle[i] = node;
          isInCycle[node] = true;
          node = edgeFrom[node];
        }
        
        // done
        break;
      }
      
      // update
      seen[node] = true;
      node = edgeFrom[node];
    }
    // done
    t1Done = true;
  }
  
  // task 2 / 3
  public void computeDistToAndFirst() {
    
    // need stuff from task 1
    if (!t1Done) {
      computeInCycle();
    }
    
    // description above
    distToCycle = new int[n];
    firstCycleNode = new int[n];
    
    // init to track already computed positions
    for (int i = 0; i < n; ++i) {
      firstCycleNode[i] = -1;
    }
    
    // nodes in cycle
    for (int i = 0; i < cycleLength; ++i) {
      firstCycleNode[inCycle[i]] = inCycle[i];
    }
    
    // use memoization
    for (int i = 0; i < n; ++i) {
      memoDist(i);
    }
    
    // done
    t23Done = true;
  }
  
  public void memoDist(int start) {
    // is in cycle -> nothing to do here
    if (isInCycle[start]) {
      return;
    }
    
    // init, no Integer import...
    int minDist = 2147483647 / 2;
    int minMember = 0;
    
    // get next node -> only one
    int e = edgeFrom[start];
    
    // not already computed
    if (firstCycleNode[e] == -1) {
      // compute
      memoDist(e);
    }
    
    // distance one larger than next
    distToCycle[start] = distToCycle[e] + 1;
    // same first-node-in-cycle
    firstCycleNode[start] = firstCycleNode[e];
    
  }
  
  // task 1
  public int cycleLength() {
    // not already computed
    if (!t1Done) {
      computeInCycle();
    }
    
    return cycleLength;
  }
  
  // task 2
  public int distanceToCycle(int x) {
    // not already computed
    if (!t23Done) {
      computeDistToAndFirst();
    }
    
    return distToCycle[x];
  }
  
  // task 3
  public int firstNodeInCycle(int x) {
    // not already computed
    if (!t23Done) {
      computeDistToAndFirst();
    }
    
    return firstCycleNode[x];
  }
  
  // task 4
  public int distanceInCycle(int x, int y) {
    // need stuff from task 2 / 3
    if (!t23Done) {
      computeDistToAndFirst();
    }
    
    // get node with smaller (n1) and node with larger (n2) distance from cycle
    int n1;
    int n2;
    if (distToCycle[x] < distToCycle[y]) {
      n1 = x;
      n2 = y;
    } else {
      n1 = y;
      n2 = x;
    }
    
    // node with smaller distance
    int firstNode = firstCycleNode[n1];
    // need to chill in cycle until n2 arrives
    int addDist = distToCycle[n2] - distToCycle[n1];
    // will end up here
    int index = (posInCycle[firstNode] + addDist) % cycleLength;
    // real node
    firstNode = inCycle[index];
    
    // node with larger distance
    int secondNode = firstCycleNode[n2];
    
    // n2 -> n1
    int d1 = Math.abs(posInCycle[firstNode] - posInCycle[secondNode]);
    // n2 <- n1
    int d2 = cycleLength - d1;
    
    // min
    return Math.min(d1, d2);
  }
}
