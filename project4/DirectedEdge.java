/******************************************************************************
 *  Compilation:  javac DirectedEdge.java
 *  Execution:    java DirectedEdge
 *  Dependencies: StdOut.java
 *
 *  Immutable weighted directed edge.
 *
 ******************************************************************************/
/**
 *  The {@code DirectedEdge} class represents a weighted edge in an
 *  {@link EdgeWeightedDigraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the directed edge and
 *  the weight.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

public class DirectedEdge {
    private final int v;
    private final int w;
    private final double price; //prince
    private final int distance; //distance
    public String compareType;

    /**
     * Initializes a directed edge from vertex {@code v} to vertex {@code w} with
     * the given {@code weight}.
     * @param v the tail vertex
     * @param w the head vertex
     * @param weight the weight of the directed edge
     * @throws IndexOutOfBoundsException if either {@code v} or {@code w}
     *    is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public DirectedEdge(int v, int w, double price, int distance) {
        if (v < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
        if (w < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
        if (Double.isNaN(distance)) throw new IllegalArgumentException("Weight is distance");
        this.v = v;
        this.w = w;
        this.price = price;
        this.distance = distance;
    }

    public DirectedEdge(int v, int w) {
       if (v < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
       if (w < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
       price = -1.0;
       distance = -1;
       this.v = v;
       this.w = w;
    }

    /**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return v;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public int to() {
        return w;
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
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    //@Override
    public int compareTo(Edge that) {
        if(compareType.equals("distance")){
             return Integer.compare(this.distance, that.distance());
        }
        else{
             return Double.compare(this.price, that.price());
        }
    }
    /**
     * Returns a string representation of the directed edge.
     * @return a string representation of the directed edge
     */
    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", price);
    }

    public boolean equals(DirectedEdge de){
         if(de.from() == v && de.to() == w){
              return true;
         }
         else{
              return false;
         }
    }
}
