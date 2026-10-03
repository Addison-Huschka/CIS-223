/**
 * One 80-column punched card.
 *
 * Each column holds a single decimal digit from 0 to 9. Columns are numbered
 * 0 to 79 here; the printed card numbers them 1 to 80, so our column 0 is the
 * card's column 1.
 *
 * Cards are immutable: once a card is punched, its digits never change.
 * Sorting a deck rearranges the Card objects in an array; it never changes
 * a card.
 *
 * This file is provided for you. Do not change it, and do not submit it.
 */
public class Card {

    /** The number of columns on a card. */
    public static final int COLUMNS = 80;

    /** The number of different values a column can hold (the digits 0-9). */
    public static final int DIGIT_VALUES = 10;

    /** toString shows the columns in groups of this size. */
    private static final int GROUP_SIZE = 10;

    private final byte[] digits;

    /**
     * Punches a card from a string of digits.
     *
     * The first character goes in column 0, the next in column 1, and so on.
     * If the string is shorter than 80 characters, the remaining columns are
     * filled with zeros.
     *
     * @param digits up to 80 characters, each '0' through '9'
     * @throws IllegalArgumentException if digits is null, longer than 80
     *         characters, or contains a character that is not a digit
     */
    public Card(String digits) {
        if (digits == null) {
            throw new IllegalArgumentException("A card needs a string of digits, not null.");
        }
        if (digits.length() > COLUMNS) {
            throw new IllegalArgumentException("A card has " + COLUMNS + " columns, but "
                    + digits.length() + " digits were given.");
        }
        this.digits = new byte[COLUMNS];
        for (int c = 0; c < digits.length(); c++) {
            char ch = digits.charAt(c);
            if (ch < '0' || ch > '9') {
                throw new IllegalArgumentException("Column " + c + " would hold '" + ch
                        + "', but every column must hold a digit 0-9.");
            }
            this.digits[c] = (byte) (ch - '0');
        }
    }

    /**
     * Returns the digit punched in one column.
     *
     * @param c a column number, 0 to 79
     * @return the digit in column c, 0 to 9
     * @throws IndexOutOfBoundsException if c is not a valid column number
     */
    public int getColumn(int c) {
        if (c < 0 || c >= COLUMNS) {
            throw new IndexOutOfBoundsException("Column " + c + " does not exist. Columns are numbered 0 to "
                    + (COLUMNS - 1) + ".");
        }
        return digits[c];
    }

    /**
     * Reads the columns from firstColumn through lastColumn (inclusive) as a
     * decimal number, with firstColumn as the most significant digit. For
     * example, if columns 5, 6, and 7 hold 0, 4, and 2, then getField(5, 7)
     * returns 42.
     *
     * The field can be at most 9 columns wide so that the result fits in an int.
     *
     * @param firstColumn the leftmost (most significant) column of the field
     * @param lastColumn the rightmost (least significant) column of the field
     * @return the field's value
     * @throws IndexOutOfBoundsException if either column does not exist
     * @throws IllegalArgumentException if firstColumn is after lastColumn, or
     *         the field is more than 9 columns wide
     */
    public int getField(int firstColumn, int lastColumn) {
        if (firstColumn > lastColumn) {
            throw new IllegalArgumentException("firstColumn (" + firstColumn
                    + ") must not be after lastColumn (" + lastColumn + ").");
        }
        if (lastColumn - firstColumn + 1 > 9) {
            throw new IllegalArgumentException("getField can read at most 9 columns, but columns "
                    + firstColumn + "-" + lastColumn + " are " + (lastColumn - firstColumn + 1) + " columns.");
        }
        int value = 0;
        for (int c = firstColumn; c <= lastColumn; c++) {
            value = value * 10 + getColumn(c);
        }
        return value;
    }

    /**
     * Returns the card's 80 digits in groups of ten columns (0-9, 10-19, and
     * so on). Consecutive groups that are all zeros are abbreviated, so a
     * census card looks like
     * {@code 2705303411 2000000000 [cols 20-69 all 0] 0000000017}.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int group = 0;
        while (group < COLUMNS) {
            if (result.length() > 0) {
                result.append(' ');
            }
            int zerosEnd = group;
            while (zerosEnd < COLUMNS && allZeros(zerosEnd, zerosEnd + GROUP_SIZE)) {
                zerosEnd += GROUP_SIZE;
            }
            if (zerosEnd > group) {
                result.append("[cols ").append(group).append('-').append(zerosEnd - 1).append(" all 0]");
                group = zerosEnd;
            } else {
                for (int c = group; c < group + GROUP_SIZE; c++) {
                    result.append(digits[c]);
                }
                group += GROUP_SIZE;
            }
        }
        return result.toString();
    }

    private boolean allZeros(int from, int to) {
        for (int c = from; c < to; c++) {
            if (digits[c] != 0) {
                return false;
            }
        }
        return true;
    }
}
