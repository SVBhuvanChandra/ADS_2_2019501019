import java.util.*;
import java.text.*;
import java.math.*;

class DijkstraSP {
    Integer[] delay;
    Integer[] source;
    ArrayList<Integer>[] adjecent;
    Integer w[][];
    boolean[] marked;
    final int V;
    final int MAX_VALUE = 2 << 23;
 
    public DijkstraSP(int vertx) {
        this.V = vertx;
        delay = new Integer[V];
        source = new Integer[V];
        adjecent = new ArrayList[V];
        w = new Integer[V][V];
        marked = new boolean[V];
        for (int i = 0; i < V; i++){
            adjecent[i] = new ArrayList<Integer>();
        }
    }
 
    public void addEdge(int a, int b, int W) {
        adjecent[a].add(b);
        w[a][b] = W;
    }
 
    public void firstSource(int s) {
        Arrays.fill(delay, MAX_VALUE);
        Arrays.fill(source, -1);
        delay[s] = 0;
    }
    
    public Integer[] getSP(int a, int b) {
        firstSource(a);
        for (int i = 0; i < V; i++) {
            int min = minimum();
            marked[min] = true;
            relax(min);
        }
 
        return getPath(a, b);
    }
 
    private void relax(int a) {
        for (Integer b : adjecent[a])
            if (delay[b] > delay[a] + w[a][b]) {
                delay[b] =  delay[a] + w[a][b];
                source[b] = a;
            }
    }
 
    private int minimum() {
        int min = -1;
        for (int i = 0; i < V; i++)
            if (!marked[i] && (min == -1 || delay[min] > delay[i]))
                min = i;
        return min;
    }
    
    private Integer[] getPath(int a, int b) {
        Vector<Integer> p = new Vector<Integer>();
 
        for (int current = b; current != a; current = source[current]) {
            p.add(current);
        }
        p.add(a);
        int size = p.size();
        p.add(size);
        p.add(delay[b]);
 
        Collections.reverse(p);
        Integer[] path = new Integer[p.size()];
        p.toArray(path);
        return path;
    }
}

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int V = -1;
        int cas = 1;
        while ((V = in.nextInt()) != 0) {
            DijkstraSP d = new DijkstraSP(V);
            for (int a = 0; a < V; a++) {
                int E = in.nextInt();
                for (int i = 0; i < E; i++) {
                    int b = in.nextInt() - 1;
                    int w = in.nextInt();
                    d.addEdge(a, b, w);
                }
            }
            Integer[] a = d.getSP(in.nextInt() - 1, in.nextInt() - 1);
            String ans = "";
            for (int i = 2; i <= 1 + a[1]; i++){
                int k = a[i] + 1;
                ans += " " + k;
            }
            System.out.printf("Case %d: Path =%s; %d second delay\n", cas++, ans, a[0]);
        }
    }
}
