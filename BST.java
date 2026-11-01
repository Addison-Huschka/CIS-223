/**
 * A binary search tree of int keys, following Chapter 12 of the textbook.
 *
 * Each node has a key and three pointers: left, right, and p (its parent).
 * A missing child or parent is null, which plays the role of the textbook's
 * NIL. The tree's root is null when the tree is empty, and the root is the
 * only node whose parent is null.
 *
 * Keys are distinct: no two nodes have the same key. Every tree built by
 * insert, delete, and buildBalanced satisfies the binary-search-tree
 * property: if y is in the left subtree of x, then y.key < x.key, and if y
 * is in the right subtree of x, then y.key > x.key.
 *
 * Everything down to "Your part" is finished. Your job is to write the five
 * methods below it: minimum, maximum, isBST, delete, and buildBalanced.
 * Look for the TODO comments. minimum, maximum, and delete follow the
 * pseudocode in Chapter 12. The instructions describe isBST and
 * buildBalanced.
 *
 * You may add private helper methods. Don't change the given code, the
 * Node class, or the method headers: the tests call them.
 *
 * This is the only file you submit.
 */
public class BST {

    /**
     * One node of the tree. The fields have no access modifier, so the
     * tests (in the same package) can build trees by hand.
     */
    static class Node {
        int key;
        Node left;    // left child, or null
        Node right;   // right child, or null
        Node p;       // parent, or null for the root

        Node(int key) {
            this.key = key;
        }
    }

    /** The root node, or null if the tree is empty. */
    Node root;

    /** Builds an empty tree. */
    public BST() {
        root = null;
    }

    // ------------------------------------------------------------------
    // Given: insert, search, transplant, and helpers for testing and debugging
    // ------------------------------------------------------------------

    /**
     * TREE-INSERT: makes a new node with key k and adds it to the tree as a
     * leaf.
     *
     * @return the new node
     * @throws IllegalArgumentException if k is already in the tree
     */
    public Node insert(int k) {
        Node z = new Node(k);
        Node x = root;       // node being compared with z
        Node y = null;       // y will be parent of z
        while (x != null) {  // descend until reaching a leaf
            y = x;
            if (z.key == x.key) {
                throw new IllegalArgumentException("key " + k + " is already in the tree");
            } else if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;             // found the location: insert z with parent y
        if (y == null) {
            root = z;        // the tree was empty
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
        return z;
    }

    /**
     * ITERATIVE-TREE-SEARCH from the root.
     *
     * @return a node with key k, or null if there is none
     */
    public Node search(int k) {
        Node x = root;
        while (x != null && k != x.key) {
            if (k < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    /**
     * TRANSPLANT: replaces the subtree rooted at u with the subtree rooted
     * at v. Afterward, u's parent (or the tree's root, if u was the root)
     * points to v, and v's parent is u's old parent. v may be null. This
     * doesn't change v.left or v.right.
     *
     * @param u a node of this tree; not null
     * @param v a node, or null
     */
    public void transplant(Node u, Node v) {
        if (u.p == null) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        if (v != null) {
            v.p = u.p;
        }
    }

    /** Returns the keys in the order of an inorder tree walk. */
    public int[] inorder() {
        int[] keys = new int[size()];
        inorder(root, keys, 0);
        return keys;
    }

    /** Stores the keys of x's subtree in keys[next...], and returns the next free index. */
    private static int inorder(Node x, int[] keys, int next) {
        if (x == null) {
            return next;
        }
        next = inorder(x.left, keys, next);
        keys[next] = x.key;
        return inorder(x.right, keys, next + 1);
    }

    /** Returns the number of nodes in the tree. */
    public int size() {
        return size(root);
    }

    private static int size(Node x) {
        return x == null ? 0 : 1 + size(x.left) + size(x.right);
    }

    /**
     * Returns the height of the tree: the number of edges on the longest
     * path from the root down to a leaf. A one-node tree has height 0, and
     * the empty tree has height -1.
     */
    public int height() {
        return height(root);
    }

    private static int height(Node x) {
        return x == null ? -1 : 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns the tree's shape on one line. A leaf is just its key. Any other
     * node is key(left, right), with - for a missing child. For example, the
     * tree with root 15, left child 6, and right child 18, where 6 has a
     * right child 7, prints as 15(6(-, 7), 18). The empty tree prints as
     * "empty".
     */
    @Override
    public String toString() {
        return root == null ? "empty" : shape(root);
    }

    private static String shape(Node x) {
        if (x == null) {
            return "-";
        }
        if (x.left == null && x.right == null) {
            return String.valueOf(x.key);
        }
        return x.key + "(" + shape(x.left) + ", " + shape(x.right) + ")";
    }

    // ------------------------------------------------------------------
    // Your part
    // ------------------------------------------------------------------

    /**
     * TREE-MINIMUM: returns the node with the smallest key in the subtree
     * rooted at x, by following left pointers down from x.
     *
     * @param x a node of this tree; not null
     */
    public Node minimum(Node x) {
        // TODO: This returns x itself, which is right only when x has no
        //       left child. Follow left pointers down from x until you reach
        //       a node with no left child, and return that node.
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    /**
     * TREE-MAXIMUM: returns the node with the largest key in the subtree
     * rooted at x, by following right pointers down from x.
     *
     * @param x a node of this tree; not null
     */
    public Node maximum(Node x) {
        // TODO: Like minimum, but follow right pointers.
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    /**
     * Returns whether the tree satisfies the binary-search-tree property:
     * every key in a node's left subtree is less than the node's key, and
     * every key in its right subtree is greater than the node's key. So a
     * tree with two equal keys is not a binary search tree. The empty tree
     * is a binary search tree. This method doesn't change the tree.
     */
    public boolean isBST() {
        return isBST(root);
    }

    /**
     * Returns whether x's subtree is a binary search tree.
     *
     * TODO: This is the tempting version, and it's wrong. It checks each node
     *       against its own children only. But the property is about every
     *       node in each subtree, not just the children. In the tree
     *       10(5(-, 12), 15), each node is fine compared with its children,
     *       yet 12 is in the left subtree of 10 and is larger than 10, so the
     *       tree is not a BST. This version returns true for it.
     *
     *       Fix it. One way: pass down the range of keys that are allowed in
     *       x's subtree, and narrow the range at each step. Another way: an
     *       inorder walk of a BST visits the keys in increasing order. Keys
     *       must be distinct, so a key equal to an ancestor's key is not
     *       allowed on either side.
     */
    private static boolean isBST(Node x) {
        if (x == null) {
            return true;
        }
        if (x.left != null && x.left.key >= x.key) {
            return false;
        }
        if (x.right != null && x.right.key <= x.key) {
            return false;
        }
        return isBST(x.left) && isBST(x.right);
    }

    /**
     * TREE-DELETE: removes node z from the tree, using transplant. If z has
     * two children, its successor y = minimum(z.right) moves into z's place.
     * Nodes move; keys never move from one node to another. Every node
     * except z stays in the tree.
     *
     * @param z a node of this tree; not null
     */
    public void delete(Node z) {
        // TODO: Write TREE-DELETE, following the pseudocode in Section 12.3
        //       and the four cases in Figure 12.4. Use the given transplant
        //       and your minimum. Move nodes; don't copy keys from one node
        //       to another.
        throw new UnsupportedOperationException("delete isn't written yet");
    }

    /**
     * Builds a tree of minimum height holding the given keys. The root holds
     * the middle key, keys[(n - 1) / 2], and the keys before and after it
     * become the root's left and right subtrees, built the same way. When
     * a part has an even number of keys, its middle is the lower of the two
     * middle keys. Every node's parent pointer is set.
     *
     * @param keys distinct keys in increasing order; not changed by this method
     * @return a new tree with height floor(lg n), where n = keys.length
     */
    public static BST buildBalanced(int[] keys) {
        // TODO: This builds a tree that holds the right keys, but inserting
        //       sorted keys one at a time makes every node a right child: a
        //       path of height n - 1, the worst case for every BST operation.
        //       Build the balanced tree instead, as described above. A
        //       recursive helper that builds the subtree for keys[low..high]
        //       works well, adding Nodes directly instead of calling insert(). 
        BST t = new BST();
        for (int k : keys) {
            t.insert(k);
        }
        return t;
    }
}
