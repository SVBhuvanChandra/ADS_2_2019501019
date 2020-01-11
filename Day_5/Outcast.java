import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdIn;
import java.io.IOException;
import edu.princeton.cs.algs4.In;

/**
 * Implementation of Outcast class.
 * @author S.V. Bhuvan Chandra
 */
public class Outcast {
    /**
     * Creating object for Word.
     */
    private final WordNet wordn;
    /**
     * Constructor for WordNet.
    */
    public Outcast(WordNet wordnet) {
        wordn = wordnet;
    }
    /**
     * Method to find the outcasted noun.
     */
    public String outcast(String[] nouns) {
        int far = 0;
        String word = "";
        for (String word1 : nouns) {
            int currentDist = 0;
            for (String word2 : nouns) {
                currentDist = currentDist + wordn.distance(word1, word2);
            }
            if (currentDist > far) {
                far = currentDist;
                word = word1;
            }
        }
        return word;
    }
    /**
     * Main Method impelmentation.
     */
    public static void main(String[] args) {

        // /**
        //  * Object creation for WordNet.
        //  */
        // WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        // /** 
        //  * Object creation for Outcast.
        // */
        // Outcast outcast = new Outcast(wordnet);
        // for (int t = 0; t < args.length; t++) {
        //     In in = new In(args[t]);
        //     String[] nouns = in.readAllStrings();
        //     StdOut.println(args[t] + ": " + outcast.Outcast(nouns));
        // }

        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        for (int t = 0; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
    }
}