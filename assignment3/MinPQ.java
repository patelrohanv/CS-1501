import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Iterator;



public class MinPQ{
    private Car[] pq;                    // store items at indices 1 to N
    private int N;                       // number of items on priority queue
    int type;                           //0 for price, 1 for millage
    private Comparator comparator;
    
    public MinPQ(int initCapacity, int type) {
        pq = new Car[initCapacity + 1];
        N = 0;
        this.type = type;
    }

    /**
     * Initializes a priority queue from the array of keys.
     * <p>
     * Takes time proportional to the number of keys, using sink-based heap construction.
     *
     * @param  keys the array of keys
     */
    public MinPQ(Car[] cars) {
        N = cars.length;
        pq = new Car[cars.length + 1];
        for (int i = 0; i < N; i++)
            pq[i+1] = cars[i];
        for (int k = N/2; k >= 1; k--)
            sink(k);
        assert isMinHeap();
    }
    
    public MinPQ(int initCapacity, int type, Comparator comparator) {
        this.type = type;
        this.comparator = comparator;
        pq = new Car[initCapacity + 1];
        N = 0;
    }

    public MinPQ(int t, Comparator comparator) {
        this(1, t, comparator);
    }


    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    
    /**
     * Returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Car min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // helper function to double the size of the heap array
    private void resize(int capacity) {
        assert capacity > N;
        Car[] temp = new Car[capacity];
        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }
    
    /**
     * Adds a new key to this priority queue.
     *
     * @param  x the key to add to this priority queue
     */

    //adds to the end and swims it up
    public void insert(Car x) {
        // double size of array if necessary
        if (N == pq.length - 1) resize(2 * pq.length);

        // add x, and percolate it up to maintain heap invariant
        pq[++N] = x;
        swim(N);
        assert isMinHeap();
    }
    
    //check if the arrays contains a car based on VIN
    public boolean contains(String vin){
        for(int i =1; i < pq.length; i++){
            if(pq[i].getVIN().equals(vin)){
                return true;
            }
        }
        return false;
    }
    public Car delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        exch(1, N);
        Car min = pq[N--];
        sink(1);
        pq[N+1] = null;         // avoid loitering and help with garbage collection
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length  / 2);
        assert isMinHeap();
        return min;
    }
    
    //updates an index, if the new value is greater then it 
    //will be sunk towards the bottom of the heap and if
    //it is less it woill be swam towards the top of the heap
    public void update(String vin){
        int index = -1;
        for(int i = 1; i <= N; i++){
            if(pq[i].getVIN().equals(vin)){
                index = i;
            }
        }
        if(index == -1){
            System.out.println("could not find");
            return;
        }
        
        System.out.println("Would you like to update:\n"
                + "1. Price\n"
                + "2. Milage\n"
                + "3. Color");
        Scanner s = new Scanner(System.in);
        int change = s.nextInt();
        if(change == 1){
            System.out.println("Please enter new price");
            int oldValue = pq[index].getPrice();
            int newValue = s.nextInt();
            pq[index].setPrice(newValue);
            if(newValue > oldValue){
                sink(index);
            }
            else{
                swim(index);
            }

            return;
        }
        else if(change == 2){
            System.out.println("Please enter new milage");
            int oldValue = pq[index].getMillage();
            int newValue = s.nextInt();
            pq[index].setMillage(newValue);
            if(newValue > oldValue){
                sink(index);
            }
            else{
                swim(index);
            }
            return;
        }
        //no changing position if just changing the color
        else if (change == 3){
            System.out.println("Please enter new color");
            String newValue = s.next();
            pq[index].setColor(newValue);
            return;
        }
    }

    //remove and set index to null
    //swap index with last value
    //decrement N
    //sink the new value
    public void remove(String vin){
        int index = -1;
        for(int i = 1; i <= N; i++){
            if(pq[i].getVIN().equals(vin)){
                index = i;
            }
        }
        if(index == -1){
            System.out.println("could not find");
            return;
        }
        exch(index, N);
        pq[N] = null;
        N--;
        sink(index);
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length  / 2);
        assert isMinHeap();

        return;
    }
   /***************************************************************************
    * Helper functions to restore the heap invariant.
    ***************************************************************************/

    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

   /***************************************************************************
    * Helper functions for compares and swaps.
    ***************************************************************************/
    private boolean greater(int i, int j) {
        if (type == 0) { //comparing price
            return (pq[i].comparePrice(pq[j])) > 0;
        }
        else { //comparing millage
            return (pq[i].compareMillage(pq[j])) > 0;
        }
    }

    private void exch(int i, int j) {
        Car swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..N] a min heap?
    private boolean isMinHeap() {
        return isMinHeap(1);
    }

    // is subtree of pq[1..N] rooted at k a min heap?
    private boolean isMinHeap(int k) {
        if (k > N) return true;
        int left = 2*k, right = 2*k + 1;
        if (left  <= N && greater(k, left))  return false;
        if (right <= N && greater(k, right)) return false;
        return isMinHeap(left) && isMinHeap(right);
    }
    
    public void findLoPriceMake (String make, String model){
        Car c = new Car("null", "null", "null", -1, -1, "null");
        int j = -1;
        for(int i = 1; i < pq.length; i++){
            if(pq[i].getMake().equals(make) && pq[i].getModel().equals(model)){
                c = pq[i];
                j = i;
            }
        }
        
        if(j == -1){
            System.out.println("not found");
            return;
        }
  
        for (;j< pq.length; j++){
            if(pq[j].getMake().equals(make) && pq[j].getModel().equals(model) && pq[j].getPrice() < c.getPrice()){
                c = pq[j];
            }
        }   
        c.print();
    }
    
    public void findLoMileeMake (String make, String model){
        Car c = new Car("null", "null", "null", -1, -1, "null");
        int j = -1;
        for(int i = 1; i < pq.length; i++){
            if(pq[i].getMake().equals(make) && pq[i].getModel().equals(model)){
                c = pq[i];
                j = i;
            }
        }
        
        if(j == -1){
            System.out.println("not found");
            return;
        }
  
        for (;j< pq.length; j++){
            if(pq[j].getMake().equals(make) && pq[j].getModel().equals(model) && pq[j].getMillage() < c.getMillage()){
                c = pq[j];
            }
        }
        c.print();
    }
    /**
     * Returns an iterator that iterates over the keys on this priority queue
     * in ascending order.
     * <p>
     * The iterator doesn't implement <tt>remove()</tt> since it's optional.
     *
     * @return an iterator that iterates over the keys in ascending order
     */
    public Iterator iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator {
        // create a new pq
        private MinPQ copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) copy = new MinPQ(size(),type);
            else                    copy = new MinPQ(size(), type, comparator);
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Car next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
    
    public void printAll(){
        for(int i = 1; i <= N; i++){
            pq[i].print();
        }
    }
}
