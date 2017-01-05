/******************************************************************************
 *  Compilation:  javac Edge.java
 *  Execution:    java Edge
 *  Dependencies: StdOut.java
 *
 *  Immutable weighted edge.
 *
 ******************************************************************************/

/**
 *  The {@code Edge} class represents a weighted edge in an
 *  {@link EdgeWeightedGraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the edge and
 *  the weight. The natural order for this data type is by
 *  ascending order of weight.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Edge implements Comparable<Edge> {

    private final int v; //city one
    private final int w; //city two
    private final double price; //prince
    private final int distance; //distance
    public String compareType;

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  weight the weight of this edge
     * @throws IndexOutOfBoundsException if either {@code v} or {@code w}
     *         is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w, double price, int distance) {
        if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (Double.isNaN(price)) throw new IllegalArgumentException("price is NaN");
        this.v = v;
        this.w = w;
        this.price = price;
        this.distance = distance;
    }

    public Edge(int v, int w) {
       if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
       if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
       price = -1.0;
       distance = -1;
       this.v = v;
       this.w = w;
    }

    /**
     * Returns the price of this edge.
     *
     * @return the price of this edge
     */
    public double price() {
        return price;
    }

    /**
     * Returns the distance of this edge.
     *
     * @return the distance of this edge
     */
    public int distance() {
       return distance;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *         endpoints of this edge
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    /**
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    @Override
    public int compareTo(Edge that) {
        if(compareType.equals("distance")){
             return Integer.compare(this.distance, that.distance());
        }
        else{
             return Double.compare(this.price, that.price());
        }
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format("%d-%d %.5f", v, w, price);
    }

    public boolean equals(Edge e){
         if(e.either() == v && e.other(e.either()) == w){
              return true;
         }
         else{
              return false;
         }
    }
}
