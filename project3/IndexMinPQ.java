/******************************************************************************
 *  Compilation:  javac IndexMinPQ.java
 *  Execution:    java IndexMinPQ
 *  Dependencies: StdOut.java
 *
 *  Minimum-oriented indexed PQ implementation using a binary heap.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The {@code IndexMinPQ} class represents an indexed priority queue of generic Apts.
 *  It supports the usual <em>insert</em> and <em>delete-the-minimum</em>
 *  operations, along with <em>delete</em> and <em>change-the-Apt</em>
 *  methods. In order to let the client refer to Apts on the priority queue,
 *  an integer between {@code 0} and {@code maxN - 1}
 *  is associated with each Apt—the client uses this integer to specify
 *  which Apt to delete or change.
 *  It also supports methods for peeking at the minimum Apt,
 *  testing if the priority queue is empty, and iterating through
 *  the Apts.
 *  <p>
 *  This implementation uses a binary heap along with an array to associate
 *  Apts with integers in the given range.
 *  The <em>insert</em>, <em>delete-the-minimum</em>, <em>delete</em>,
 *  <em>change-Apt</em>, <em>decrease-Apt</em>, and <em>increase-Apt</em>
 *  operations take logarithmic time.
 *  The <em>is-empty</em>, <em>size</em>, <em>min-index</em>, <em>min-Apt</em>,
 *  and <em>Apt-of</em> operations take constant time.
 *  Construction takes time proportional to the specified capacity.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Apt> the generic type of Apt on this priority queue
 */
 @SuppressWarnings("unchecked")
public class IndexMinPQ implements Iterable<Integer> {
    private int maxN;        // maximum number of elements on PQ
    private int n;           // number of elements on PQ
    private int[] pq;        // binary heap using 1-based indexing
    private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Apt[] Apts;      // Apts[i] = priority of i

    /**
     * Initializes an empty indexed priority queue with indices between {@code 0}
     * and {@code maxN - 1}.
     * @param  maxN the Apts on this priority queue are index from {@code 0}
     *         {@code maxN - 1}
     * @throws IllegalArgumentException if {@code maxN < 0}
     */
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        Apts = new Apt[maxN + 1];    // make this of length maxN??
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   // make this of length maxN??
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return {@code true} if this priority queue is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Is {@code i} an index on this priority queue?
     *
     * @param  i an index
     * @return {@code true} if {@code i} is an index on this priority queue;
     *         {@code false} otherwise
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     */
    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }

    /**
     * Returns the number of Apts on this priority queue.
     *
     * @return the number of Apts on this priority queue
     */
    public int size() {
        return n;
    }

    /**
     * Associates Apt with index {@code i}.
     *
     * @param  i an index
     * @param  Apt the Apt to associate with index {@code i}
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @throws IllegalArgumentException if there already is an item associated
     *         with index {@code i}
     */
    public void insert(int i, Apt Apt) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        qp[i] = n;
        pq[n] = i;
        Apts[i] = Apt;
        swim(n);
    }

    /**
     * Returns an index associated with a minimum Apt.
     *
     * @return an index associated with a minimum Apt
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int minIndex() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    /**
     * Returns a minimum Apt.
     *
     * @return a minimum Apt
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Apt minApt() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return Apts[pq[1]];
    }

    /**
     * Removes a minimum Apt and returns its associated index.
     * @return an index associated with a minimum Apt
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int delMin() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, n--);
        sink(1);
        assert min == pq[n+1];
        qp[min] = -1;        // delete
        Apts[min] = null;    // to help with garbage collection
        pq[n+1] = -1;        // not needed
        return min;
    }

    /**
     * Returns the Apt associated with index {@code i}.
     *
     * @param  i the index of the Apt to return
     * @return the Apt associated with index {@code i}
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @throws NoSuchElementException no Apt is associated with index {@code i}
     */
    public Apt AptOf(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return Apts[i];
    }

    /**
     * Change the Apt associated with index {@code i} to the specified value.
     *
     * @param  i the index of the Apt to change
     * @param  Apt change the Apt associated with index {@code i} to this Apt
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @throws NoSuchElementException no Apt is associated with index {@code i}
     */
    public void changeApt(int i, Apt Apt) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        Apts[i] = Apt;
        swim(qp[i]);
        sink(qp[i]);
    }

    /**
     * Change the Apt associated with index {@code i} to the specified value.
     *
     * @param  i the index of the Apt to change
     * @param  Apt change the Apt associated with index {@code i} to this Apt
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @deprecated Replaced by {@code changeApt(int, Apt)}.
     */
    @Deprecated
    public void change(int i, Apt Apt) {
        changeApt(i, Apt);
    }

    /**
     * Decrease the Apt associated with index {@code i} to the specified value.
     *
     * @param  i the index of the Apt to decrease
     * @param  Apt decrease the Apt associated with index {@code i} to this Apt
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @throws IllegalArgumentException if {@code Apt >= AptOf(i)}
     * @throws NoSuchElementException no Apt is associated with index {@code i}
     */
    public void decreaseApt(int i, Apt Apt) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (Apts[i].compareTo(Apt) <= 0)
            throw new IllegalArgumentException("Calling decreaseApt() with given argument would not strictly decrease the Apt");
        Apts[i] = Apt;
        swim(qp[i]);
    }

    /**
     * Increase the Apt associated with index {@code i} to the specified value.
     *
     * @param  i the index of the Apt to increase
     * @param  Apt increase the Apt associated with index {@code i} to this Apt
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @throws IllegalArgumentException if {@code Apt <= AptOf(i)}
     * @throws NoSuchElementException no Apt is associated with index {@code i}
     */
    public void increaseApt(int i, Apt Apt) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (Apts[i].compareTo(Apt) >= 0)
            throw new IllegalArgumentException("Calling increaseApt() with given argument would not strictly increase the Apt");
        Apts[i] = Apt;
        sink(qp[i]);
    }

    /**
     * Remove the Apt associated with index {@code i}.
     *
     * @param  i the index of the Apt to remove
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @throws NoSuchElementException no Apt is associated with index {@code i}
     */
    public void delete(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exch(index, n--);
        swim(index);
        sink(index);
        Apts[i] = null;
        qp[i] = -1;
    }
    //gets
    public Apt minCity(String city){
         Apt b = null;
         boolean flag = false;
         for(Apt a: Apts){
              if(a.getCity().equals(city) && flag == false){
                   b = a;
                   flag = true;
              }
              if(a.getCity().equals(city) && a.getRent() < b.getRent() && flag == true){
                   b = a;
              }
         }
         if(b != null){
              return b;
         }
         else{
              return null;
         }
    }
    //gets ID for apt in array
    public int getAptID(String street_address, int apt_number, String zip_code){
        for(Apt a: Apts){
             if(a.getStreet_address().equals(street_address) && a.getApt_number() == apt_number && a.getZip_code().equals(zip_code)){
                  return a.id;
             }
        }
        return -1;
    }
    public void print(){
         for(Apt a: Apts){
              if(a != null)
                    System.out.println(a);
         }
    }


   /***************************************************************************
    * General helper functions.
    ***************************************************************************/
    private boolean greater(int i, int j) {
        return Apts[pq[i]].compareTo(Apts[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }


   /***************************************************************************
    * Heap helper functions.
    ***************************************************************************/
    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }


   /***************************************************************************
    * Iterators.
    ***************************************************************************/

    /**
     * Returns an iterator that iterates over the Apts on the
     * priority queue in ascending order.
     * The iterator doesn't implement {@code remove()} since it's optional.
     *
     * @return an iterator that iterates over the Apts in ascending order
     */
    public Iterator<Integer> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private IndexMinPQ copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no Apts move
        public HeapIterator() {
            copy = new IndexMinPQ(pq.length - 1);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i], Apts[pq[i]]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
}
