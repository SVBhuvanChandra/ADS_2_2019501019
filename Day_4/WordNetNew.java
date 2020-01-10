import edu.princeton.cs.algs4.StdOut;

import java.io.*;
import java.util.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;

public class WordNetNew {

    public WordNetNew() {

    }

    HashMap<String, ArrayList<Integer>> h1 = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> h2 = new HashMap<>();
    Digraph dg;
    String[] words;
    Integer[] id;
    String[] hypernyms;
    SAP sap1;
    ArrayList<String> slist = new ArrayList<String>();

    public void parsesynsets(String filename) throws IOException {
        FileReader file1 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + filename);
        BufferedReader br1 = new BufferedReader(file1);
        String line1;
        String l;
        while ((line1 = br1.readLine()) != null) {
            String[] temp1 = line1.split(",");
            slist.add(Integer.parseInt(temp1[0]),temp1[1]);
            words = temp1[1].split(" ");
            for (String s : words) {
                ArrayList al;
                if (h1.containsKey(s)) {
                    al = h1.get(s);
                } else {
                    al = new ArrayList<>();
                    h1.put(s, al);
                }
                al.add(temp1[0]);
            }
        }
        System.out.println(h1.get("calcium"));
        System.out.println(h1.get("casein"));
    }

    public void parsehypernyms(String filename) throws IOException {
        FileReader file2 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + filename);
        BufferedReader br2 = new BufferedReader(file2);
        String line2;
        ArrayList<Integer> al2;
        while ((line2 = br2.readLine()) != null) {
            String[] temp2 = line2.split(",",2);
            if (temp2.length > 1) {
                hypernyms = temp2[1].split(",");
                int inti = Integer.parseInt(temp2[0]);
                for (String i : hypernyms) {
                    if (h2.containsKey(inti)) {
                        al2 = h2.get(inti);
                    } else {
                        al2 = new ArrayList<>();
                        h2.put(inti,al2);
                    }
                    al2.add(Integer.parseInt(i));
                    dg.addEdge(Integer.parseInt(temp2[0]), Integer.parseInt(i));
                }
            }
    }
    // System.out.println(h2.get(34));
}

//Need to IMPLEMENT
// public Iterable<String> nouns() {
    
// }

public boolean isNoun(String word) {
    if (word == null) {
        return false;
    }
    else {
        return h1.containsKey(word);
    }
}

public int distance(String nounA, String nounB) {
    if (isNoun(nounA) && isNoun(nounB)) {
        sap1 = new SAP(dg);
        int dist1 = sap1.length(h1.get(nounA), h1.get(nounB));
        return dist1;
    } else {
        return -1;
    }
}

public String sap(String nounA, String nounB) {
    if (isNoun(nounA) && isNoun(nounB)) {
        sap1 = new SAP(dg);
        // System.out.println(h1.get(nounB)+"hghgahfgakf");
        int anc = sap1.ancestor(h1.get(nounA), h1.get(nounB));
        return slist.get(anc);

    }
    return "Not a valid noun";
}
    public static void main(String[] args) throws IOException {
        WordNetNew wnn = new WordNetNew();
        wnn.parsesynsets("synsets.txt");
        wnn.dg = new Digraph(wnn.h1.size());
        wnn.parsehypernyms("hypernyms.txt");
        for (int v = 0; v < wnn.dg.V(); v++) {
            for (int w : wnn.dg.adj(v)){
                // System.out.println(v + "->" + w);
            }
        }
        // System.out.println("vertices : " + wnn.dg.V());
        // System.out.println("edges : " + wnn.dg.E());
        System.out.println(wnn.distance("calcium_ion", "casein"));

    }
}