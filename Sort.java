/**
 * Sorting a deck of punched cards the way a card sorter does.
 *
 * A card sorter reads one column of each card and drops the card into one of
 * ten pockets, one for each digit 0-9. Picking the pockets up in order gives a
 * deck sorted by that column, and cards that land in the same pocket stay in
 * the order they were fed in. That is a stable counting sort.
 *
 * To sort by a field that spans several columns, the operator runs the deck
 * through the sorter once per column, starting with the rightmost column.
 * That is LSD radix sort, and it works only because each pass is stable.
 *
 * You implement both sorts in this file. This is the only file you submit.
 */
public class Sort {

    /**
     * Sorts the deck by the digit in one column, using counting sort.
     *
     * The sort rearranges the Card objects in the cards array itself: when
     * it returns, the same array holds the same cards, ordered so that the
     * digits in the given column never decrease from one card to the next.
     *
     * The sort must be stable: cards with the same digit in this column stay
     * in the same order relative to each other that they had before.
     *
     * The sort must work for an empty deck and for a deck of one card.
     *
     * @param cards the deck to sort; not null
     * @param column the column to sort by, 0 to Card.COLUMNS - 1
     */
    public static void countingSort(Card[] cards, int column) {
        throw new UnsupportedOperationException("countingSort is not implemented yet");
    }

    /**
     * Sorts the deck by the field that spans firstColumn through lastColumn
     * (inclusive), using LSD radix sort. The field is read left to right, so
     * firstColumn is the most significant digit.
     *
     * The sort calls countingSort once for each column in the field, starting
     * with lastColumn and working left to firstColumn. Like countingSort, it
     * rearranges the Card objects in the cards array itself, and it must be
     * stable: cards with equal fields keep their relative order.
     *
     * The sort must work for an empty deck and for a deck of one card.
     *
     * @param cards the deck to sort; not null
     * @param firstColumn the leftmost (most significant) column of the field
     * @param lastColumn the rightmost (least significant) column of the field;
     *        firstColumn &lt;= lastColumn
     */
    public static void radixSort(Card[] cards, int firstColumn, int lastColumn) {
        throw new UnsupportedOperationException("radixSort is not implemented yet");
    }
}
