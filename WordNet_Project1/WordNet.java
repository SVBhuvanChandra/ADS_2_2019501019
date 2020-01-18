import java.util.Scanner;
import java.util.ArrayList;
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.HashMap;
import edu.princeton.cs.algs4.Digraph;
/**
 * WordNet class impelementation.
 * @author S.V. Bhuvan Chandra.
 */
public class WordNet {
    /**
     * Object for Digraph.
     */
    private final Digraph dg;
    /**
     * Object for SAP.
     */
    private final SAP sap;
    /**
     * Hashmap for Synsets.
     */
    private final HashMap<String, ArrayList<Integer>> h1 = new HashMap<String, ArrayList<Integer>>();
    /**
     * Hashmap for Hypernyms.
     */
    private final HashMap<Integer, ArrayList<Integer>> h2 = new HashMap<Integer, ArrayList<Integer>>(); 
    /**
     * Field to store an Array List.
     */
    private final ArrayList<String> str = new ArrayList<String>();
    /**
     * WordNet constructor.
     * @param synsets sysnsets text file.
     * @param hypernyms Hypernyms text file.
     */
    public WordNet(String synsets, String hypernyms) {
        parseSynsets(synsets);
        dg = new Digraph(h1.size());
        parseHypernyms(hypernyms);
        sap = new SAP(dg);
    }
    /**
     * Method to parse the synsets.
     * @param filename sysnsets text file.
     */
    private void parseSynsets(String filename) {
        /**
         * Filed to store the scanned files.
         */
        String[] scanned = scanFile(filename);
        /**
         * Field to store the splitted line.
         */
        String[] temp1;
        /**
         * Field to store the splitted file.
         */
        String[] temp2;
        temp1 = new String[scanned.length];
        for (int i = 0; i < scanned.length; i++) {
            temp1 = scanned[i].split(","); 
            str.add(Integer.parseInt(temp1[0]), temp1[1]);
            temp2 = temp1[1].split(" ");
            for (int j = 0; j < temp2.length; j++) {
                if (h1.containsKey(temp2[j])) {
                    h1.get(temp2[j]).add(Integer.parseInt(temp1[0]));
                } else {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(Integer.parseInt(temp1[0]));
                    h1.put(temp2[j], temp);
                }        
            }
        }
    }
    /**
     * Method to find the distnace between the vertices.
     * @param nounA first noun.
     * @param nounB Second noun.
     * @return
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) { 
        throw new IllegalArgumentException();
    } 
       if (isNoun(nounA) && isNoun(nounB)) {
           if (nounA.equals(nounB)) {
               return 0;
           }
            int ds = sap.length(h1.get(nounA), h1.get(nounB));
            return ds;
        } else {
            return -1;
        }
    }
    /**
     * Method to impelement iterable.
     * @return iterable data type.
     */
    public Iterable<String> nouns() {
        ArrayList<String> temp = new ArrayList<>();
        for (String s : h1.keySet()) {
         temp.add(s);
        }
        return temp;
   }
   /**
    * Method to impement the SAP.
    * @param nounA first noun.
    * @param nounB second noun.
    * @return SAP.
    */
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) { 
            throw new IllegalArgumentException();
        } 
        if (isNoun(nounA) && isNoun(nounB)) {
            int ans = sap.ancestor(h1.get(nounA), h1.get(nounB));
            if (ans != -1) {
                return str.get(ans);
            }
            else {
                return "No common ancestor available";
            }
        }
        else {
            return "Invalid noun";
        }
    }
    /**
     * Method to parse Hypernyms.
     * @param filename input text file.
     */
    private void parseHypernyms(String filename) {
        /**
         * Field to store the scanned file.
         */
        String[] scanned = scanFile(filename);
        /**
         * Filed to store the splitted file.
         */
        String[] temp1;
        /**
         * Field to store the splitted file.
         */
        String[] temp2;
        temp1 = new String[scanned.length];
        for (int i = 0; i < scanned.length; i++) {
            if (!(scanned[i].contains(","))) {
                continue;
            }
            temp1 = scanned[i].split(",", 2); 
            temp2 = temp1[1].split(",");
            for (int j = 0; j < temp2.length; j++) {
                if (h2.containsKey(Integer.parseInt(temp1[0]))) {
                    h2.get(Integer.parseInt(temp1[0])).add(Integer.parseInt(temp2[j]));
                    dg.addEdge(Integer.parseInt(temp1[0]), Integer.parseInt(temp2[j]));
                } else {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(Integer.parseInt(temp2[j]));
                    h2.put(Integer.parseInt(temp1[0]), temp);
                    dg.addEdge(Integer.parseInt(temp1[0]), Integer.parseInt(temp2[j]));
                }
            }
        }
    }
    /**
     * Method to check Nouns.
     * @param word input noun
     * @return true / false.
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        } else {
            return h1.containsKey(word);
        }
    }
    /**
     * A private method implemented to read the file.
     * By using Scanner in Java.lang, this method is implemented.
     * @param filename file.
     * @return array of nouns.
     */
    private static String[] scanFile(final String filename) {
            ArrayList<String> lines = new ArrayList<String>();
            Scanner scan = null;
            try {
                scan = new Scanner(new File(filename));
                while (scan.hasNext()) {
                    lines.add(scan.nextLine());
                }
                String[] linesArray = lines.toArray(new String[lines.size()]);
                return linesArray;
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } finally {
                if (scan != null) {
                    scan.close();
                }
            }
            return null;
        }
    /**
     * Main method impelementation.
     * @param args input arguments.
     */  
    public static void main(String[] args) {
        WordNet ref = new WordNet(args[0], args[1]); 
        // WordNet ref = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(ref.distance("calcium_ion", "casein"));  
    }
}

