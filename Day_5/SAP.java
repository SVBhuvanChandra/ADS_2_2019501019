import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
// import java.util.ArrayList;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
/**
 * SAP class impelmentation.
 * @author S.V. Bhuvan Chandra
 */
public class SAP {
    /**
     * Object for Digraph.
     */
    private final Digraph d;
    /**
     * field to store the path length.
     */
    private int path;
    /**
     * Filed to store the ancestor.
     */
    private int anci;
    /**
     * Field to store the distance.
     */
    private int dist;

    /**
     * Object for Breadth_First_Directed_Paths.
     */
    private BreadthFirstDirectedPaths bfs1;
    /**
     * Object for Breadth_First_Directed_Paths.
     */
    private BreadthFirstDirectedPaths bfs2;

    /**
     * Constructor for SAP.
     * @param G
     */
    public SAP(Digraph G) {
        d = G;
        path = -1;
        anci = -1;
    }
    /**
     * Method to find the length of a vertex from its ancestor.
     * @param v source vertex id.
     * @param w destination vertex id.
     * @return length.
     */
    public int length(int v, int w) {
        ancestor(v, w);
        return path;
    }
 
    /**
     * Method to find the ancestor of the vertices.
     * @param v source vertex id.
     * @param w destination vertex id.
     * @return ancestor id.
     */
    public int ancestor(int v, int w) {
        if (v == w) {
            path = 0;
            return v;
        }
        /**
         * Filed to store a maximum distance value.
         */
        dist = Integer.MAX_VALUE;

        if ((v < 0 && v > d.V()) || (w < 0 && w > d.V())) {
            return -1;
        }
        try {
            /**
             * Object of the Breadth first Directed paths.
             */
            bfs1 = new BreadthFirstDirectedPaths(d, v);
            /**
             * Object of the Breadth first Directed Paths.
             */
            bfs2 = new BreadthFirstDirectedPaths(d, w);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return -1;
        }
        for (int i = 0; i < d.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                path = bfs1.distTo(i) + bfs2.distTo(i);
                if (dist >= path) {
                    dist = path;
                    anci = i;
                }
            }
            if (dist != Integer.MAX_VALUE) {
                path = dist;
            }
        }
        return anci;
    }
    /**
     * Method to find the length of the vertex from its vertex.
     * @param v Source vertex id's ArrayList
     * @param w Destination vertex id's ArrayList.
     * @return Length.
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        ancestor(v, w);
        // if (v == w) {
        //     return 0;
        // }
        return path;
    }
 
    /**
     * Method to find the ancestor of the given vertex.
     * @param v Source vertex id's ArrayList
     * @param w Destination vertex id's ArrayList.
     * @return Ancestor ID.
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        /**
         * Intializing distnace to max value.
         */
        dist = Integer.MAX_VALUE;
        /** 
         * Filed to store Ancestor ID.
         */
        int aa = -1;
        try {
            if (v == null || w == null) throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {}
        /**
         * Object for BreadthFirstDirectedPaths.
         */
        bfs1 = new BreadthFirstDirectedPaths(d, v);
        /**
         * Object for BreadthFirstDirectedPaths.
         */
        bfs2 = new BreadthFirstDirectedPaths(d, w);
        for (int x : v) {
            for (int y : w) {
                for (int i = 0; i < d.V(); i++) {
                    if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                        path = bfs1.distTo(i) + bfs2.distTo(i);
                        if (dist >= path) {
                            dist = path;
                            anci = i;
                        }
                    }
                    if (dist != Integer.MAX_VALUE) {
                        path = dist;
                    }
                }
            }
        }
        return aa;
    }
    /**
     * Main method for SAP.
     * @param args input arguments.
     */
    public static void main(String[] args) {
        /**
         * Object for Input.
         */
        In in = new In(args[0]);
        /**
         * Object for Digraph.
         */
        Digraph dg1 = new Digraph(in);
        /**
         * Object for SAP.
         */
        SAP sap = new SAP(dg1);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("Length = %d, Ancestor = %d\n", length, ancestor);
        }
    }
 }