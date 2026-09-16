/** A toy implementation of the heap Abstract Data Type.
 * We make the following simplifications:
 *  - we only support min-heaps, not max-heaps
 *  - we only store keys, not key-value pairs
 *  - we can only hold up to 1024 keys
 *  - the keys must be integers
 */
public class Heap {
    private static final int MAX_SIZE = 1024;
    private int[] keys = new int[MAX_SIZE]; // Array to store the keys
    private int n = 0; // Number of keys

    /** Returns the number of elements in heap.
     * @return The number of elements in the heap.
     */
    public int size() {
        // ADD CODE TO MAKE SIZE() WORK
    }

    /** Returns true if the heap is empty, otherwise false.
     * @return True if the heap is empty, otherwise false.
     */
    public boolean isEmpty() {
        // ADD CODE TO MAKE ISEMPTY() WORK
    }

    /** Returns the minimum key in the heap
     * @return The value of the minimum key in the heap. 
     * @throws NoSuchElementException if the heap is empty.
     */
    public int min() {
        // Throw an exception if the heap is empty
        if (n == 0) {
            throw new java.util.NoSuchElementException();
        }
        // ADD CODE TO MAKE MIN() WORK
    }

    /** Adds a key to the heap.
     * @param key The key to be added.
     * @throws IllegalStateException if the heap is already full.
     */
    public void add(int key) {
        // Throw an exception if heap is already full
        if (n >= MAX_SIZE) {
            throw new java.lang.IllegalStateException();
        }
        // ADD CODE TO MAKE ADD() WORK
    }

    /** Removes and returns the minimum key from the heap.
     *  @return The key with the minimum value.
     *  @throws NoSuchElementException if the heap is already empty.
     */
    public int removeMin() {
        // Throw an exception if heap is already empty
        if (n <= 0) {
            throw new java.util.NoSuchElementException();
        }
        // ADD CODE TO MAKE REMOVEMIN() WORK
    }
}