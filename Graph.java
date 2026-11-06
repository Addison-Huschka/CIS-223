import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A graph stored as adjacency lists, following Chapter 20 of the textbook.
 *
 * Each vertex has a name, such as "s" or "u", and an adjacency list, adj:
 * the vertices it has an edge to, in the order the edges were added. Edges
 * can be directed (addEdge) or undirected (addUndirectedEdge). G.V, the set
 * of vertices, is kept in the order the vertices were added.
 *
 * Each vertex also has the attributes that BFS and DFS fill in: color, d,
 * f, and pi (the textbook's pi symbol). null plays the role of the textbook's NIL,
 * and INFINITY plays the role of infinity.
 *
 * Everything down to "Your part" is finished. Your job is to write the four
 * methods below it: bfs, path, dfs, and dfsVisit. Look for the TODO
 * comments. All four follow the pseudocode in Sections 20.2 and 20.3.
 *
 * You may add private helper methods. Don't change the given code, the
 * Vertex class, or the method headers: the tests call them.
 *
 * This is the only file you submit.
 */
public class Graph {

    /** The colors BFS and DFS give vertices. */
    enum Color { WHITE, GRAY, BLACK }

    /** Stands for infinity, the distance to a vertex that BFS doesn't reach. */
    static final int INFINITY = Integer.MAX_VALUE;

    /**
     * One vertex of the graph. The fields have no access modifier, so the
     * tests (in the same package) can read them.
     */
    static class Vertex {
        final String name;
        final ArrayList<Vertex> adj = new ArrayList<>();   // G.Adj[u], in the order edges were added

        Color color = Color.WHITE;
        int d = INFINITY;  // BFS: distance from the source.  DFS: discovery time.
        int f;             // DFS: finish time
        Vertex pi;         // predecessor (the textbook's pi symbol), or null for NIL

        Vertex(String name) {
            this.name = name;
        }

        /** Returns the vertex's name, so lists of vertices print as [s, r, w]. */
        @Override
        public String toString() {
            return name;
        }
    }

    /** G.V: every vertex, by name, in the order the vertices were added. */
    final LinkedHashMap<String, Vertex> vertices = new LinkedHashMap<>();

    /** The clock for DFS: the textbook's global variable time. */
    int time;

    /** Builds a graph with no vertices. */
    public Graph() {
    }

    // ------------------------------------------------------------------
    // Given: building the graph, and helpers for testing and debugging
    // ------------------------------------------------------------------

    /**
     * Adds a new vertex with the given name, with no edges.
     *
     * @return the new vertex
     * @throws IllegalArgumentException if there is already a vertex with that name
     */
    public Vertex addVertex(String name) {
        if (vertices.containsKey(name)) {
            throw new IllegalArgumentException("there is already a vertex named " + name);
        }
        Vertex u = new Vertex(name);
        vertices.put(name, u);
        return u;
    }

    /** Adds a new vertex for each name, in order. */
    public void addVertices(String... names) {
        for (String name : names) {
            addVertex(name);
        }
    }

    /**
     * Returns the vertex with the given name.
     *
     * @throws IllegalArgumentException if there is no vertex with that name
     */
    public Vertex vertex(String name) {
        Vertex u = vertices.get(name);
        if (u == null) {
            throw new IllegalArgumentException("there is no vertex named " + name);
        }
        return u;
    }

    /** G.V: every vertex, in the order the vertices were added. */
    public Collection<Vertex> vertices() {
        return vertices.values();
    }

    /**
     * Adds the directed edge (u, v): appends v to the end of u's adjacency
     * list. u and v may be the same vertex (a self-loop).
     *
     * @throws IllegalArgumentException if a vertex is missing or the edge is already there
     */
    public void addEdge(String u, String v) {
        Vertex from = vertex(u);
        Vertex to = vertex(v);
        if (from.adj.contains(to)) {
            throw new IllegalArgumentException("the edge (" + u + ", " + v + ") is already in the graph");
        }
        from.adj.add(to);
    }

    /**
     * Adds the undirected edge (u, v): appends v to the end of u's
     * adjacency list, and u to the end of v's. A self-loop (u, u) goes in
     * u's list only once.
     *
     * @throws IllegalArgumentException if a vertex is missing or the edge is already there
     */
    public void addUndirectedEdge(String u, String v) {
        addEdge(u, v);
        if (!u.equals(v)) {
            addEdge(v, u);
        }
    }

    /**
     * Returns the adjacency lists, one vertex per line, in the form
     * "u: v, x". A vertex with no edges prints as "u:".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex u : vertices()) {
            sb.append(u.name).append(':');
            for (int i = 0; i < u.adj.size(); i++) {
                sb.append(i == 0 ? " " : ", ").append(u.adj.get(i).name);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Returns each vertex's color, d, f, and pi, one vertex per line, for
     * debugging. INFINITY prints as "inf" and NIL prints as "-".
     */
    public String attributes() {
        StringBuilder sb = new StringBuilder();
        for (Vertex u : vertices()) {
            sb.append(String.format("%-4s color=%-5s d=%-4s f=%-4s pi=%s%n", u.name, u.color,
                    u.d == INFINITY ? "inf" : String.valueOf(u.d), String.valueOf(u.f),
                    u.pi == null ? "-" : u.pi.name));
        }
        return sb.toString();
    }

    // ------------------------------------------------------------------
    // Your part
    // ------------------------------------------------------------------

    /**
     * BFS(G, s): breadth-first search from s. Afterward, for every vertex v,
     * v.d is the smallest number of edges on a path from s to v (INFINITY
     * if there is no path), and v.pi is v's predecessor on such a path (null
     * for s and for vertices that s can't reach).
     *
     * @param s a vertex of this graph; not null
     */
    public void bfs(Vertex s) {
        for (Vertex u : vertices()) {
            u.color = Color.WHITE;
            u.d = INFINITY;
            u.pi = null;
        }
        s.color = Color.GRAY;
        s.d = 0;
        s.pi = null;
        ArrayDeque<Vertex> Q = new ArrayDeque<>();
        Q.add(s);
        while (!Q.isEmpty()) {
            Vertex u = Q.remove();
            for (Vertex v : u.adj) {
                if (v.color == Color.WHITE) {
                    v.color = Color.GRAY;
                    v.d = u.d + 1;
                    v.pi = u;
                    Q.add(v);
                }
            }
            u.color = Color.BLACK;
        }
    }

    /**
     * Returns the vertices on a shortest path from s to v, starting with s
     * and ending with v. This runs bfs(s) itself first. If v == s, the path
     * is just [s]. If there is no path from s to v, it returns an empty list.
     *
     * @param s a vertex of this graph; not null
     * @param v a vertex of this graph; not null
     */
    public List<Vertex> path(Vertex s, Vertex v) {
        // TODO: Call bfs(s), then follow the pi pointers back from v, the
        //       way PRINT-PATH in Section 20.2 does. PRINT-PATH prints the
        //       path; you add each vertex to a list instead. PRINT-PATH's
        //       recursion prints s first and v last, which is the order this
        //       list needs. Return an empty list where PRINT-PATH prints
        //       "no path from s to v exists". A private recursive helper
        //       that adds to the end of an ArrayList works well.
        bfs(s);
        if (v == s) {
            List<Vertex> result = new ArrayList<>();
            result.add(s);
            return result;
        }

        else if (v.pi == null) {
            return new ArrayList<>();
        }
        else {
            List<Vertex> result = path(s, v.pi);
            result.add(v);
            return result;
        }
    }

    /**
     * DFS(G): depth-first search of the whole graph. Afterward, every vertex
     * is BLACK, v.d and v.f are its discovery and finish times (from 1 to
     * 2|V|), and v.pi is its parent in the depth-first forest (null for the
     * root of each depth-first tree).
     */
    public void dfs() {
        // TODO: Write DFS(G) from Section 20.3. Both loops go through
        //       vertices() in order. The textbook's global variable time is
        //       the field time.
        time = 0;
        for (Vertex u : vertices()) {
            u.color = Color.WHITE;
            u.d = INFINITY;
            u.f = 0;
            u.pi = null;
        }
        for (Vertex u : vertices()) {
            if (u.color == Color.WHITE) {
                dfsVisit(u);
            }
        }
    }

    /**
     * DFS-VISIT(G, u): discovers u, visits every white vertex reachable from
     * u through white vertices, and finishes u.
     *
     * @param u a white vertex of this graph; not null
     */
    public void dfsVisit(Vertex u) {
        // TODO: Write DFS-VISIT(G, u) from Section 20.3, line by line. Go
        //       through u.adj in order with for (Vertex v : u.adj).
        time++;
        u.d = time;
        u.color = Color.GRAY;
        for (Vertex v : u.adj) {
            if (v.color == Color.WHITE) {
                v.pi = u;
                dfsVisit(v);
            }
        }
        time++;
        u.f = time;
        u.color = Color.BLACK;
    }
}