import java.util.*;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    Digraph d;
    int path;
    int anci;
    int dist = Integer.MAX_VALUE;

    BreadthFirstDirectedPaths bfs1;
    BreadthFirstDirectedPaths bfs2;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        d = G;
        path = -1;
        anci = -1;
    }
 
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        ancestor(v, w);
        return path;
    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        bfs1 = new BreadthFirstDirectedPaths(d, v);
        bfs2 = new BreadthFirstDirectedPaths(d, w);

        for (int i = 0; i < d.V(); i++) {

            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                path = bfs1.distTo(i) + bfs2.distTo(i);

                if (dist >= path) {
                    dist = path;
                    anci = i;
                }
            }
        }
        return anci;
    }
 
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        ancestor(v, w);
        return path;

    }
 
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        for (int x : v ){
            System.out.println("excuting1.....1 ");
            for (int y : w) {

                for (int i = 0; i < d.V(); i++) {
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
        return anci;
    }
 
    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph dg1 = new Digraph(in);
        SAP sap = new SAP(dg1);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v,w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("Length = %d, Ancestor = %d\n", length, ancestor);
        }
    }
 }