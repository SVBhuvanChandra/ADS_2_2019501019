import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
/**
 * Class to implemet SAP (Shortest Ancestor Path).
 * @author S.V. Bhuvan Chandra
 */
public class SAP {
    /**
     * Object created for BFS first vertex.
     */
    private BreadthFirstDirectedPaths bfs1;
    /**
     * Object for BFS second vertex.
     */
    private BreadthFirstDirectedPaths bfs2;
    /**
     * Object for Digraph.
     */
    private final Digraph Dig;
    /**
     * Field to store the distance.
     */
    private int dist;
    /**
     * Field to store the path.
     */
    private int path;
    /**
     * Field to store the ancecestor.
     */
    private int anci;
    /**
     * Constructor for SAP.
     */
    public SAP(Digraph G) {
        Dig = G;
        path = -1;
        anci = -1;
    }
    /**
     * Method to implement the length.
     */
    public int length(int v, int w) {
        ancestor(v, w);
        return path; 
    }
    /**
     * Method to implement ancestor.
     */
    public int ancestor(int v, int w) {
        if (v == w) {
            path = 0;
            return v;
        }
        anci = -1;
        path = -1;
        dist = Integer.MAX_VALUE;
        if ((v < 0 && v > Dig.V()) || (w < 0 && w > Dig.V())) {
            return -1;
        }
        try {
            bfs1 = new BreadthFirstDirectedPaths(Dig, v);
            bfs2 = new BreadthFirstDirectedPaths(Dig, w);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return -1;
        }  
        for (int i = 0; i < Dig.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                path = bfs1.distTo(i) + bfs2.distTo(i);    
                if (dist >= path) {
                    dist = path;
                    anci = i;
                }
            }
        }
        if (dist != Integer.MAX_VALUE) {
            path = dist;
        } return anci;
    }
    /**
     * Method to implement Length (Interable type).
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        ancestor(v, w);
        return path;
    }
    /**
     * Method to implement ancestor (Iterable type).
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        dist = Integer.MAX_VALUE;
        try {
            if (v == null || w == null) throw new IllegalArgumentException(); 
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument");
        }
        anci = -1;
        path = -1;
        bfs1 = new BreadthFirstDirectedPaths(Dig, v);
        bfs2 = new BreadthFirstDirectedPaths(Dig, w);
        for (int k : v) {
            for (int m : w) {
                if (((k < 0 && k < Dig.V()) || k > 0 && k > Dig.V())) {
                    return -1;
                }
                for (int i = 0; i < Dig.V(); i++) {
                    if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                        path = bfs1.distTo(i) + bfs2.distTo(i);
                        if (dist >= path) {
                            dist = path;
                            anci = i;
                        }
                    }
                }
            }
        }
        if (dist != Integer.MAX_VALUE) {
            path = dist;
        }
        return anci;
    }
/**
 * Main Method implementation.
 */
public static void main(String[] args) {
    System.out.println("Main method");
    }
}