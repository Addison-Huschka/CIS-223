/**
 * An in-place quicksort with median-of-three pivot selection.
 *
 * Complete the sort(), medianOfThree(), and partition() methods. Do not change their signatures.
 * Rules:
 *  - Sort in place. Do not allocate any array, list, or other collection. The only extra space
 *    you may use is a constant number of local variables per call, plus the recursion stack.
 *  - Every element exchange must go through swap(Key[], int, int) in Key.java
 *  - Every key comparison must go through compareTo(Key) in Key.java
 *  - Do not call java.util.Arrays.sort or any other library sort
 *  - Don't touch anything in Key.java
 * </ul>
 */
public final class Quicksort {

    /** Not instantiable. */
    private Quicksort() {
    }

    /**
     * Sorts the whole array into ascending order, in place.
     *
     * @param a the array to sort; must not be null, may be empty, may contain duplicate values
     */
    public static void sort(Key[] a) {
        // TODO: handle the trivial cases, then call your recursive helper on the full range.
        if (a == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        sort(a, 0, a.length - 1);
    }

    /**
     * Sorts the subarray a[lo..hi] (inclusive on both ends) into ascending order, in place.
     *
     * @param a  the array to sort
     * @param lo the first index of the range
     * @param hi the last index of the range; the range is empty when hi < lo
     */
    private static void sort(Key[] a, int lo, int hi) {
        // TODO: base case, then partition and recurse on the two sides.
        if (lo < hi) {
            int pivotIndex = partition(a, lo, hi);
            sort(a, lo, pivotIndex - 1);
            sort(a, pivotIndex + 1, hi);
        }
    }

    /**
     * Returns the index of the median of the three elements a[lo], a[mid], and a[hi], 
     * where mid == lo + (hi - lo) / 2.
     *
     * "Median" means the element that is neither the strict minimum nor the strict maximum of
     * the three. When two or more of the three are equal, more than one index is a correct answer
     * and any of them is accepted.
     *
     * This method must not modify the array, and it must use at most three comparisons.
     * Hint: just do a branching series of if-tests
     *
     * @param a  the array
     * @param lo the first index of the range; requires hi - lo >= 2 (i.e. must have at least 3 elements)
     * @param hi the last index of the range
     * @return one of lo, mid, or hi
     */
    static int medianOfThree(Key[] a, int lo, int hi) {
        // TODO: at most three comparisons, no swaps, no array modification.
        int mid = lo + (hi - lo) / 2;
        if (a[lo].compareTo(a[mid]) > 0) {
            if (a[mid].compareTo(a[hi]) > 0) {
                return mid; // lo > mid > hi
            } else if (a[lo].compareTo(a[hi]) > 0) {
                return hi; // lo > hi >= mid
            } else {
                return lo; // hi >= lo > mid
            }
        } else {
            if (a[lo].compareTo(a[hi]) > 0) {
                return lo; // mid >= lo > hi
            } else if (a[mid].compareTo(a[hi]) > 0) {
                return hi; // mid > hi >= lo
            } else {
                return mid; // lo <= mid <= hi
            }
        }
    }

    /**
     * Chooses a pivot, partitions the subarray a[lo..hi] around that pivot, and returns the 
     * pivot's final index p. When lo == hi then we're partitioning one element so just return lo.
     * When hi - lo < 2 we can't do median of three so pick any element of the range as the pivot. 
     * Otherwise, call medianOfThree() to choose a pivot
     *
     * On return the following must be true:
     *  - lo <= p <= hi
     *  - a[p] is the chosen pivot
     *  - a[lo..p..hi] has been partitioned around p
     *  - nothing outside a[lo..hi] has changed.
     *
     * @param a  the array
     * @param lo the first index of the range; requires lo <= hi
     * @param hi the last index of the range
     * @return the final index of the pivot
     */
    static int partition(Key[] a, int lo, int hi) {
        // TODO: choose the pivot, scan and swap elements, put the pivot back into its final place
        if (lo == hi) {
            return lo; // Only one element, return its index
        }
        else if (hi - lo < 2) {
            return lo; // Two elements, choose the first as pivot
        }
        else {
            int pivotIndex = medianOfThree(a, lo, hi);
            Key pivotValue = a[pivotIndex];
            Key.swap(a, pivotIndex, hi); // Move pivot to end
            int storeIndex = lo;

            for (int i = lo; i < hi; i++) {
                if (a[i].compareTo(pivotValue) < 0) {
                    Key.swap(a, i, storeIndex);
                    storeIndex++;
                }
            }
            Key.swap(a, storeIndex, hi); // Move pivot to its final place
            return storeIndex;
        }
    }
}