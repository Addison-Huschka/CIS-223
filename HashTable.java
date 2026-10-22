/**
 * A hash table of nonnegative integer keys that resolves collisions with
 * open addressing and linear probing, following Chapter 11 of the textbook.
 *
 * The table is an array T[0..m-1]. Each slot holds a key, NIL (the slot has
 * never held a key), or DELETED (the slot held a key that was deleted).
 *
 * As given, this table works only when no two keys collide: every key goes
 * in its first-choice slot h'(k) or nowhere. Your job is to add linear
 * probing. Look for the four TODO comments, in hash, insert, search, and
 * delete. Everything else is finished, and you shouldn't need to change it.
 *
 * This is the only file you submit.
 */
public class HashTable {

    /** A slot that has never held a key. Also returned by search when a key isn't found. */
    public static final int NIL = -1;

    /** A slot whose key was deleted. */
    public static final int DELETED = -2;

    private final int[] table;   // T[0..m-1]
    private int size;            // n, the number of keys in the table

    /**
     * Builds an empty hash table with m slots, all NIL.
     *
     * @param m the number of slots; at least 1
     */
    public HashTable(int m) {
        if (m < 1) {
            throw new IllegalArgumentException("m must be at least 1, but was " + m);
        }
        table = new int[m];
        for (int q = 0; q < m; q++) {
            table[q] = NIL;
        }
        size = 0;
    }

    /**
     * The auxiliary hash function h'(k) = k mod m (the division method).
     * This is the key's first-choice slot.
     */
    public int auxHash(int k) {
        return k % table.length;
    }

    /**
     * The probe sequence for linear probing: h(k, i) = (h'(k) + i) mod m.
     * Probe number i = 0 is the key's first choice, i = 1 its second choice,
     * and so on. After slot m - 1 the sequence wraps around to slot 0.
     *
     * @param k a key
     * @param i the probe number, 0 <= i < m
     * @return the slot to look at on probe number i
     */
    public int hash(int k, int i) {
        return (auxHash(k) + i) % table.length;
    }

    /**
     * HASH-INSERT: stores key k in the first slot of its probe sequence that
     * is NIL or DELETED.
     *
     * Assumes k is not already in the table.
     *
     * @param k the key to insert; nonnegative
     * @return the slot where k was stored
     * @throws IllegalStateException with the message "hash table overflow"
     *         if every slot already holds a key
     */
    public int insert(int k) {
        checkKey(k);
        
        for (int i = 0; i < table.length; i++) {
            int q = hash(k, i);
            if (table[q] == NIL || table[q] == DELETED) {
                table[q] = k;
                size++;
                return q;
            }
        }
        if (size == table.length) {
            throw new IllegalStateException("hash table overflow");
        }
        return NIL;
    }

    /**
     * HASH-SEARCH: follows k's probe sequence until it finds k, reaches a NIL
     * slot, or has probed all m slots. DELETED slots don't stop the search.
     *
     * @param k the key to look for; nonnegative
     * @return the slot that holds k, or NIL if k is not in the table
     */
    public int search(int k) {
        checkKey(k);
        for (int i = 0; i < table.length; i++) {
            int q = hash(k, i);
            if (table[q] == k) {
                return q;
            }
            if (table[q] == NIL) {
                return NIL;
            }
            if (i == table.length - 1) {
                return NIL;
            }
        }
        return NIL;
    }

    /**
     * Deletes key k by marking its slot DELETED (not NIL, which would cut
     * other keys' probe sequences short).
     *
     * @param k the key to delete; nonnegative
     * @return the slot that held k, or NIL if k was not in the table
     */
    public int delete(int k) {
        int q = search(k);
        if (q != NIL) {
            table[q] = DELETED;
            size--;
        }
        return q;
    }

    /** Returns n, the number of keys in the table. */
    public int size() {
        return size;
    }

    /** Returns m, the number of slots. */
    public int capacity() {
        return table.length;
    }

    /**
     * Returns what slot q holds: a key, NIL, or DELETED. For testing and
     * debugging.
     */
    public int slot(int q) {
        return table[q];
    }

    /** Returns the table one slot at a time, such as [0: 13, 1: NIL, 2: DELETED]. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (int q = 0; q < table.length; q++) {
            if (q > 0) {
                s.append(", ");
            }
            s.append(q).append(": ");
            if (table[q] == NIL) {
                s.append("NIL");
            } else if (table[q] == DELETED) {
                s.append("DELETED");
            } else {
                s.append(table[q]);
            }
        }
        return s.append("]").toString();
    }

    private static void checkKey(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("keys must be nonnegative, but was " + k);
        }
    }
}
