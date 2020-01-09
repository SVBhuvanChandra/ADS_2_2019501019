import edu.princeton.cs.algs4.StdOut;

import java.io.*;
import java.util.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;

public class WordNet1 {
    HashMap<String, ArrayList<Integer>> h1 = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> h2 = new HashMap<>();
    Digraph dg;
    String[] words;
    Integer[] id;
    String[] hypernyms;

    public void parsesynsets(String filename) throws IOException {
        FileReader file1 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + filename);
        BufferedReader br1 = new BufferedReader(file1);
        String line1;
        while ((line1 = br1.readLine()) != null) {
            String[] temp1 = line1.split(",");
            words = temp1[1].split(" ");
            ArrayList al;
            for (String s : words) {
                if (h1.containsKey(s)) {
                    al = h1.get(s);
                } else {
                    al = new ArrayList<>();
                    h1.put(s, al);
                }
                al.add(temp1[0]);
            }
        }
    }

    public void parsehypernyms(String filename) throws IOException {
        FileReader file2 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + filename);
        BufferedReader br2 = new BufferedReader(file2);
        String line2;
        ArrayList<Integer> al2;
        while ((line2 = br2.readLine()) != null) {
            String[] temp2 = line2.split(",",2);
            if (temp2.length != 1) {
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
    }

    public static void main(String[] args) throws IOException {
        WordNet1 wnn = new WordNet1();
        wnn.parsesynsets("synsets.txt");
        wnn.dg = new Digraph(wnn.h1.size());
        wnn.parsehypernyms("hypernyms.txt");

        for (int v = 0; v < wnn.dg.V(); v++) {
            for (int w : wnn.dg.adj(v)){
                System.out.println(v + "->" + w);
            }
        }
    }
}