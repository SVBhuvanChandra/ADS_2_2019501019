import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import edu.princeton.cs.algs4.Digraph;

/**
 * Class WordNet implementation.
 * @author S.V.Bhuvan Chandra
 */
public class WordNet {
    /**
     * HashMap h1 for Synsets. Nouns as keys and Integer array as values.
     */
    private final HashMap<String, ArrayList<Integer>> h1 = new HashMap<>();
    /**
     * HashMap h2 for Hypernyms. Integer as key and Integer array as values.
     */
    private final HashMap<Integer, ArrayList<Integer>> h2 = new HashMap<>();
    /**
     * Object for diagraph.
     */
    private final Digraph dg;
    /**
     * Field to store the nouns.
     */
    private String[] words;
    /**
     * Field to store the key.
     */
    // private Integer[] id;
    /**
     * Field to store hypernyms.
     */
    private String[] hypernyms;
    /**
     * Object for SAP.
     */
    private final SAP sap1;
    /**
     * Array list to store the synsets list.
     */
    private final ArrayList<String> slist = new ArrayList<String>();
    /**
     * Constructor for Wordnet.
     * @param synset Synsets text file.
     * @param hypernym Hypernyms textfile.
     * @throws IOException input/output exceptions.
     */
    public WordNet(String synset, String hypernym) {
        parsesynsets(synset);
        dg = new Digraph(h1.size());
        parsehypernyms(hypernym);
        sap1 = new SAP(dg);
    }
    /**
     * Method to parse synsets.
     * @param filename Synsets text file.
     * @throws IOException input / output exceptions.
     */
    private void parsesynsets(String filename) {
        /**
         * Reading file by using FileReader.
         */
        // FileReader file1 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + filename);
        File file1 = new File("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + filename);
        try (Scanner sc = new Scanner(file1)) {
            while (sc.hasNextLine()) {
                String line1 = sc.nextLine();
                String[] temp1 = line1.split(",");
                slist.add(Integer.parseInt(temp1[0]), temp1[1]);
                words = temp1[1].split(" ");
                for (String s : words) {
                    /**
                     * Filed to store the integers in a list.
                     */
                    ArrayList<Integer> al;
                    if (h1.containsKey(s)) {
                        al = h1.get(s);
                    } else {
                        al = new ArrayList<Integer>();
                        h1.put(s, al);
                    }
                    al.add(Integer.parseInt(temp1[0]));
                }
            }
        }
        catch (IOException e) {
            System.out.println("File not found");
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of Bounds");
        }
    }
    /**
     * Method to parse hypernyms.
     * @param filename Hypernyms text file.
     * @throws IOException input / output exception.
     */
    private void parsehypernyms(String filename) {
        /**
         * Reading the file using FileReader.
         */
        // FileReader file2 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + filename);
        File file2 = new File("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + filename);
        // BufferedReader br2 = new BufferedReader(file2);

        try (Scanner sc = new Scanner(file2)) {
            ArrayList<Integer> al2;
            while (sc.hasNextLine()) {
                String line2 = sc.nextLine();
                String[] temp2 = line2.split(",", 2);
                if (temp2.length > 1) {
                    hypernyms = temp2[1].split(",");
                    int inti = Integer.parseInt(temp2[0]);
                    for (String i : hypernyms) {
                        if (h2.containsKey(inti)) {
                            al2 = h2.get(inti);
                        } else {
                            al2 = new ArrayList<>();
                            h2.put(inti, al2);
                        }
                        al2.add(Integer.parseInt(i));
                        dg.addEdge(Integer.parseInt(temp2[0]), Integer.parseInt(i));
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("File Not Found");
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Index Out Of Bounds");
        }
}
/**
 * Impelementing Iterabale method for nouns.
 * @return iterable type of string array.
 */
public Iterable<String> nouns() {
    /**
     * Filed to store the names.
     */
    ArrayList<String> names = new ArrayList<>();
    for (String n : h1.keySet()) {
        names.add(n);
    }
    return names;
}
/**
 * Method to check that the given word is noun.
 * @param word input noun
 * @return true / false.
 */
public boolean isNoun(String word) {
    if (word == null) {
        return false;
    }
    else {
        return h1.containsKey(word);
    }
}
/**
 * Method to find the distance between two nouns.
 * @param nounA first noun.
 * @param nounB second noun.
 * @return distance or -1 if words are not connected.
 */
public int distance(String nounA, String nounB) {
    if (isNoun(nounA) && isNoun(nounB)) {
        /**
         * Filed to store the distnace.
         */
        int dist1 = sap1.length(h1.get(nounA), h1.get(nounB));
        return dist1;
    } else {
        return -1;
    }
}
/**
 * Method to give common ancestor.
 * @param nounA first noun.
 * @param nounB second noun.
 * @return ancestor.
 */
public String sap(String nounA, String nounB) {
    if (isNoun(nounA) && isNoun(nounB)) {
        /**
         * Filed to store the id of the ancestor.
         */
        int anc = sap1.ancestor(h1.get(nounA), h1.get(nounB));
        return slist.get(anc);
    }
    return "Not a valid noun";
}
    /**
     * Main method implementation.
     * @param args input arguments.
     * @throws IOException input /output exceptions.
     */
    public static void main(String[] args) {
        /**
         * Creating object for WordNet.
         */
        WordNet wnn = new WordNet("synsets.txt", "hypernyms.txt");
        // for (int v = 0; v < wnn.dg.V(); v++) {
        //     for (int w : wnn.dg.adj(v)){
        //         // System.out.println(v + "->" + w);
        //     }
        // }
        // System.out.println("vertices : " + wnn.dg.V());
        // System.out.println("edges : " + wnn.dg.E());
        System.out.println("The distance : "+wnn.distance("calcium_ion", "casein"));
    }
}