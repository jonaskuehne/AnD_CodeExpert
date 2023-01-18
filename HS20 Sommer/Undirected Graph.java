import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;
import java.lang.String;



class Main {
    public static void main(String[] args) {
      
        // Uncomment the following two lines if you want to read from a file
        // In.open("public/custom.in");
        // Out.compareTo("public/custom.out");
        
        char operator=In.readChar();  // Type of Operations
        
        int T=In.readInt();   // number of tasks
        
        switch(operator)
        {
          
          case 'P':
            // Two-Induced Path
        
            for(int l=0;l<T;l++){
              int n=In.readInt();     // number of vertices
              int m=In.readInt();     // number of edges
              int k=In.readInt();     // number of queries  
        
            
              int[][] edge_array=new int[m][2];
              
          
             
              for(int i=0;i<m;i++){
                edge_array[i][0]=In.readInt();
                edge_array[i][1]=In.readInt();
              }
              
              
              int[][] query_array=new int[k][3];
              
              for(int i=0;i<k;i++){
                query_array[i][0]=In.readInt();
                query_array[i][1]=In.readInt();
                query_array[i][2]=In.readInt();
              }
              
              
              
              Graph G= new Graph(n, m, edge_array);
              
              for(int i=0;i<k;i++){
                Out.println(G.Two_Induced_Path(query_array[i][0],query_array[i][1],query_array[i][2]));
              }
            }
            
            
            
            break;
          
          case 'E':
            // Euler Tour
        
            for(int l=0;l<T;l++){
              int n=In.readInt();     // number of vertices
              int m=In.readInt();     // number of edges
        
              int[][] edge_array=new int[m][2];
              
          
              for(int i=0;i<m;i++){
                edge_array[i][0]=In.readInt();
                edge_array[i][1]=In.readInt();
              }
              Graph G= new Graph(n, m, edge_array);
              Out.println(G.Exists_Euler_Cycle());
            }
            
            
            break;
            

            
            
            case 'C':
            // Two-Colorable
            
        
            for(int l=0;l<T;l++){
              int n=In.readInt();     // number of vertices
              int m=In.readInt();     // number of edges
        
              
              int[][] edge_array=new int[m][2];
              
              for(int i=0;i<m;i++){
                edge_array[i][0]=In.readInt();
                edge_array[i][1]=In.readInt();
              }
              Graph G= new Graph(n, m, edge_array);
              Out.println(G.Two_Colorable());
            }
            
            
            break;
            
            
            case 'D':
            // Maximum Distance
        
            for(int l=0;l<T;l++){
              int n=In.readInt();     // number of vertices
              int m=In.readInt();     // number of edges
              int k=In.readInt();     // number of queries  
        
              int[][] edge_array=new int[m][2];
              
              for(int i=0;i<m;i++){
                edge_array[i][0]=In.readInt();
                edge_array[i][1]=In.readInt();
              }
              
              
              int[] query_array=new int[k];
              
              for(int i=0;i<k;i++){
                query_array[i]=In.readInt();
              }
              

              
              Graph G= new Graph(n, m, edge_array);
              
              for(int i=0;i<k;i++){

                Out.println(G.Max_Distance(query_array[i]));
              }
            }
            
            
            
            break;
        }
        

        // Uncomment the following line if you want to read from a file
        //In.close();
    }

   
}

class Graph{
  
  private int n;              // number of vertices
  private int m;              // number of edges
  private int[] degree;      // degrees[i]: the degree of vertex i
  private int[][] edges;      // edges[i][j]: the endpoint of the j-th edge of vertex i
  private boolean[] visited;  // visited[i]: if vertex i has been visited. 
  
  Graph(int n, int m, int[][] edge_array)
  {
    this.n=n;
    this.m=m;
    degree=new int[n];
    
    for(int i=0;i<n;i++){
      degree[i]=0;
    }
      
    for(int i=0;i<m;i++){
      degree[edge_array[i][0]]++;
      degree[edge_array[i][1]]++;
    }
    
    edges=new int[n][];
      
    for(int i=0;i<n;i++){
      if(degree[i]!=0){
        edges[i]=new int[degree[i]];
        degree[i]=0;
      }
      else{
        edges[i]=null;
      }
    }
    
    for(int i=0;i<m;i++){
      edges[edge_array[i][0]][degree[edge_array[i][0]]++]=edge_array[i][1];
      edges[edge_array[i][1]][degree[edge_array[i][1]]++]=edge_array[i][0];
    } 
    
    visited=new boolean[n];
  }
  
  // check if all degrees even since G connected
  public boolean Exists_Euler_Cycle()
  {
    
    for (int i = 0; i < n; ++i) {
      if (degree[i] % 2 != 0) {
        return false;
      }
    }
    
    return true;
  }
  
  // just check edges
  public boolean Two_Induced_Path(int u,int v, int w)
  {
    
    if (u > n || v > n || w > n) {
      return false;
    }
    
    int count = 0;
    
    if (containsEdge(v, u)) {
      ++count;
    }
    
    if (containsEdge(u, w)) {
      ++count;
    }
    
    if (containsEdge(v, w)) {
      ++count;
    }
    
    return count == 2;
  }
  
  // checks if edge between vertices is in graph
  public boolean containsEdge(int u, int v) {
    for (int i = 0; i < degree[u]; ++i) {
      if (edges[u][i] == v) {
        return true;
      }
    }
    
    return false;
  }
  
  // run tweaked bfs
  public boolean Two_Colorable()
  {
    
    // arrays to store stuff
    boolean[] color = new boolean[n];
    boolean[] visited = new boolean[n];
    
    // queue
    LinkedList<Integer> queue = new LinkedList<>();
    // init
    queue.add(0);
    
    // bfs
    while (!queue.isEmpty()) {
      // get element
      int v = queue.poll();
      // mark
      visited[v] = true;
      
      // for next edges
      for (int next : edges[v]) {
        // not visited yet -> set color
        if (!visited[next]) {
          queue.add(next);
          color[next] = !color[v];
          
        // visited -> control color
        } else {
          if (color[v] == color[next]) {
            return false;
          }
        }
        
      }
      
    }
    
    // all correct
    return true;
  }
  
  // really just bfs
  public int Max_Distance(int v)
  {
    
    // store stuff
    boolean[] visited = new boolean[n];
    int[] d = new int[n];
    int max = 0;
    
    // queue
    LinkedList<Integer> queue = new LinkedList<>();
    // init
    queue.add(v);
    visited[v] = true;
    
    // bfs
    while (!queue.isEmpty()) {
      // get element
      int curr = queue.poll();
      
      // for next edges
      for (int next : edges[curr]) {
        // use d instead of visited here (array-graph-dies-das)
        if (!visited[next]) {
          queue.add(next);
          // get longest path
          d[next] = d[curr] + 1;
          // store max
          max = Math.max(max, d[next]);
          // mark
          visited[next] = true;
        }
        
      }
      
    }

    return max;
  }
  
  
  // this is bfs land my lad
  private void DFS_Initialization()
  {
    //Initialization
    for(int i=0;i<n;i++){
      visited[i]=false;
    }
  }
  
  private void DFS(int v)
  {
    // DFS from v
    
    for(int i=0;i< degree[v];i++){
        if(visited[edges[v][i]]==false){
          visited[edges[v][i]]=true;
          DFS(edges[v][i]);
        }
    }
  }

}
