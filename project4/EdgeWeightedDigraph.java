/**
 *  The {@code EdgeWeightedDigraph} class represents a edge-weighted
 *  digraph of vertices named 0 through <em>V</em> - 1, where each
 *  directed edge is of type {@link DirectedEdge} and has a real-valued weight.
 *  It supports the following two primary operations: add a directed edge
 *  to the digraph and iterate over all of edges incident from a given vertex.
 *  It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which
 *  is a vertex-indexed array of @link{Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the edges incident from a given vertex, which takes
 *  time proportional to the number of such edges.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;                // number of vertices in this digraph
    private int E;                      // number of edges in this digraph
    //private Bag<DirectedEdge>[] adj;    // adj[v] = adjacency list for vertex v
    private ArrayList<LinkedList<DirectedEdge>> adj;
    private int[] indegree;             // indegree[v] = indegree of vertex v

    /**
     * Initializes an empty edge-weighted digraph with {@code V} vertices and 0 edges.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        //adj = (Bag<DirectedEdge>[]) new Bag[V];
        adj = new ArrayList<LinkedList<DirectedEdge>>(V);
        for (int v = 0; v < V; v++){
           //adj[v] = new Bag<DirectedEdge>();
           adj.add(new LinkedList<DirectedEdge>());
        }
    }

    /**
     * Initializes a new edge-weighted digraph that is a deep copy of {@code G}.
     *
     * @param  G the edge-weighted digraph to copy
     */
    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++)
            this.indegree[v] = G.indegree(v);
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<DirectedEdge> reverse = new Stack<DirectedEdge>();
            //for (DirectedEdge e : G.adj[v]) {
            for (DirectedEdge e : G.adj.get(v)) {
                reverse.push(e);
            }
            for (DirectedEdge e : reverse) {
                //adj[v].add(e);
                adj.get(v).add(e);
            }
        }
    }

    public ArrayList<LinkedList<DirectedEdge>> adj(){
         return adj;
    }
    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
        return E;
    }

    // throw an IndexOutOfBoundsException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the directed edge {@code e} to this edge-weighted digraph.
     *
     * @param  e the edge
     * @throws IndexOutOfBoundsException unless endpoints of edge are between {@code 0}
     *         and {@code V-1}
     */
    public void addEdge(DirectedEdge e) {
         //System.out.println("directed edge add");
        int v = e.from();
        int w = e.to();
       // System.out.println(v);
       // System.out.println(w);
        validateVertex(v);
        validateVertex(w);
        //adj[v].add(e);
        adj.get(v).add(e);
     //    boolean a = adj.get(v).add(e);
     //    System.out.println(a);
        indegree[w]++;
        E++;
    }

    /**
     * Removes the directed edge {@code e} from this edge-weighted graph.
     *
     * @param  e the edge
     * @throws IndexOutOfBoundsException unless both endpoints are between {@code 0} and {@code V-1}
     */
    public void removeEdge(DirectedEdge e) {
         //System.out.println("directed edge remove");
        int v = e.from();
        int w = e.to();
        //System.out.println(v);
        //System.out.println(w);
        validateVertex(v);
        validateVertex(w);
        //adj.get(v).remove(e);
        boolean a = false;
        for(DirectedEdge f: adj.get(v)){
             if(f.from() == v && f.to() == w){
                  //System.out.println("here 3");
                  a = adj.get(v).remove(f);
             }
        }
        //boolean a = adj.get(v).remove(e);
        //System.out.println(a);
        indegree[w]--;
        E--;
    }

    /**
     * Returns the directed edges incident from vertex {@code v}.
     *
     * @param  v the vertex
     * @return the directed edges incident from vertex {@code v} as an Iterable
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     */
    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        //return adj[v];
        return adj.get(v);
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     */
    public int outdegree(int v) {
        validateVertex(v);
        //return adj[v].size();
        return adj.get(v).size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * Returns all directed edges in this edge-weighted digraph.
     * To iterate over the edges in this edge-weighted digraph, use foreach notation:
     * {@code for (DirectedEdge e : G.edges())}.
     *
     * @return all edges in this edge-weighted digraph, as an iterable
     */
    public Iterable<DirectedEdge> edges() {
        LinkedList<DirectedEdge> list = new LinkedList<DirectedEdge>();
        //Bag<DirectedEdge> list = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            //for (DirectedEdge e : adj[v]) {
            for (DirectedEdge e : adj.get(v)) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public void setCompareType(String s){
         for(LinkedList<DirectedEdge> l: adj){
              for(DirectedEdge e: l){
                   e.compareType = s;
              }
         }
    }
    public void maxPrice(double max){
     for(LinkedList<DirectedEdge> l: adj){
         for(DirectedEdge e: l){
               if(e.price() <= max){
                     System.out.println(Airline.cities[e.from()] + " to "+ Airline.cities[e.to()]);
                     System.out.println("\tDistance: " + e.distance());
                     System.out.println("\tPrice: " + e.price());
                }
           }
      }
     }
}
