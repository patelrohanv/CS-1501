/**
 *  The {@code EdgeWeightedGraph} class represents an edge-weighted
 *  graph of vertices named 0 through <em>V</em> - 1, where each
 *  undirected edge is of type {@link Edge} and has a real-valued weight.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the edges incident to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which
 *  is a vertex-indexed array of @link{Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the edges incident to a given vertex, which takes
 *  time proportional to the number of such edges.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

import java.util.ArrayList;
import java.util.LinkedList;

public class EdgeWeightedGraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    //private Bag<Edge>[] adj;
    private ArrayList<LinkedList<Edge>> adj;

    /**
     * Initializes an empty edge-weighted graph with {@code V} vertices and 0 edges.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        //adj = (Bag<Edge>[]) new Bag[V];
        adj = new ArrayList<LinkedList<Edge>>(V);
        for (int v = 0; v < V; v++) {
            //adj[v] = new Bag<Edge>();
            adj.add(new LinkedList<Edge>());
        }
    }
    /**
     * Initializes a new edge-weighted graph that is a deep copy of {@code G}.
     *
     * @param  G the edge-weighted graph to copy
     */
    public EdgeWeightedGraph(EdgeWeightedGraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Edge> reverse = new Stack<Edge>();
            for (Edge e : G.adj.get(v)) {
            //for (Edge e : G.adj[v]) {
                reverse.push(e);
            }
            for (Edge e : reverse) {
                adj.get(v).add(e);
                //adj[v].add(e);
            }
        }
    }

    public ArrayList<LinkedList<Edge>> adj(){
         return adj;
    }
    /**
     * Returns the number of vertices in this edge-weighted graph.
     *
     * @return the number of vertices in this edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted graph.
     *
     * @return the number of edges in this edge-weighted graph
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
     * Adds the undirected edge {@code e} to this edge-weighted graph.
     *
     * @param  e the edge
     * @throws IndexOutOfBoundsException unless both endpoints are between {@code 0} and {@code V-1}
     */
    public void addEdge(Edge e) {
         //System.out.println("edge add");
        int v = e.either();
        int w = e.other(v);
           //System.out.println(v);
           //System.out.println(w);
        validateVertex(v);
        validateVertex(w);
     //    boolean a = adj.get(v).add(e);
     //    boolean b = adj.get(w).add(e);
        adj.get(v).add(e);
        adj.get(w).add(e);
     //    System.out.println(a);
     //    System.out.println(b);
        //adj[v].add(e);
        //adj[w].add(e);
        E++;
    }

    /**
     * Removes the undirected edge {@code e} from this edge-weighted graph.
     *
     * @param  e the edge
     * @throws IndexOutOfBoundsException unless both endpoints are between {@code 0} and {@code V-1}
     */
    public void removeEdge(Edge e) {
      // System.out.println("edge remove");
       int v = e.either();
       int w = e.other(v);
       //System.out.println(v);
       //System.out.println(w);
       validateVertex(v);
       validateVertex(w);
       boolean a = false;
       boolean b = false;;
       for(Edge f: adj.get(v)){
            if(f.either() == v && f.other(f.either()) == w){
                //System.out.println("here 1");
                a = adj.get(v).remove(f);
            }
       }
       for(Edge f: adj.get(w)){
            if(f.either() == v && f.other(f.either()) == w){
                // System.out.println("here 2");
                 b =  adj.get(w).remove(f);
            }
       }
       //boolean a = adj.get(v).remove(e);
       //boolean b = adj.get(w).remove(e);
       //System.out.println(a);
       //System.out.println(b);
       //adj[v].add(e);
       //adj[w].add(e);
       E--;
    }

    /**
     * Returns the edges incident on vertex {@code v}.
     *
     * @param  v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj.get(v);
        //return adj[v];
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the degree of vertex {@code v}
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        //return adj[v].size();
        return adj.get(v).size();
    }

    /**
     * Returns all edges in this edge-weighted graph.
     * To iterate over the edges in this edge-weighted graph, use foreach notation:
     * {@code for (Edge e : G.edges())}.
     *
     * @return all edges in this edge-weighted graph, as an iterable
     */
    public Iterable<Edge> edges() {
        LinkedList<Edge> list = new LinkedList<Edge>();
        //Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // only add one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    /**
     * Returns a string representation of the edge-weighted graph.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            //for (Edge e : adj[v]) {
            for (Edge e : adj.get(v)) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public void setCompareType(String s){
         for(LinkedList<Edge> l: adj){
              for(Edge e: l){
                   e.compareType = s;
              }
         }
    }

    public void print(){
         for(LinkedList<Edge> l: adj){
              for(Edge e: l){
                   System.out.println(Airline.cities[e.either()] + " to "+ Airline.cities[e.other(e.either())]);
                   System.out.println("\tDistance: " + e.distance());
                   System.out.println("\tPrice: " + e.price());
              }
         }
    }
}
