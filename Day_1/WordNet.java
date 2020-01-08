import java.io.*;
import java.util.*;

/**
 * Class Wordnet to perform two methods for splitting files.
 * 
 * @author S.V.Bhuvan Chandra
 */
public class WordNet {
    /**
     * Method to split synsets from the text file given.
     * 
     * @param s1 file name
     * @return splitted keys, values
     * @throws IOException throws exceptions
     */
    public HashMap<Integer, String[]> parsesynset(String s1) throws IOException {
        /**
         * implementation for reading synset files.
         */
        FileReader file1 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + s1);
        BufferedReader br1 = new BufferedReader(file1);
        String str1;
        HashMap<Integer, String[]> h1 = new HashMap<Integer, String[]>();
        while ((str1 = br1.readLine()) != null) {
            String[] sa1 = str1.split(",", 2);
            // String[] val1 = sa1[1].split(",");
            String[] val1 = Arrays.copyOfRange(sa1, 1, sa1.length);
            h1.put(Integer.parseInt(sa1[0]), val1);
        }
        for (Integer val : h1.keySet()) {
            String key = val.toString();
            String value = Arrays.toString(h1.get(val));
            System.out.println(key + " " + value);
        }
        br1.close();
        return h1;
    }

    /**
     * Method to split hypernyms from the text file given.
     * 
     * @param s2 file name
     * @return splitted keys, values
     * @throws IOException throws exceptions
     */
    public HashMap<Integer, String[]> parsehypernym(String s2) throws IOException {
        /**
         * Implementation for hypernymns file reading.
         */
        FileReader file2 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\Textfiles\\" + s2);
        BufferedReader br2 = new BufferedReader(file2);
        String str2;
        // int count = 0;
        HashMap<Integer, String[]> h2 = new HashMap<Integer, String[]>();
        while ((str2 = br2.readLine()) != null) {
            String[] sa2 = str2.split(",", 2);
            if (sa2.length == 1) {
                h2.put(Integer.parseInt(sa2[0]), null);
            } else {
                String[] val2 = sa2[1].split(",");
                h2.put(Integer.parseInt(sa2[0]), val2);
            }
        }
        for (Integer val : h2.keySet()) {
            String key = val.toString();
            String value = Arrays.toString(h2.get(val));

            // System.out.println(key + " " + value);
            // count++;
        }
        // System.out.println(count);
        br2.close();
        return h2;
    }

    /**
     * Main method impelmentation.
     * 
     * @param args input arguments
     * @throws IOException throws any exception.
     */
    public static void main(String[] args) throws IOException {
        int edge = 0;
        int vertex = 0;
        WordNet wn = new WordNet();
        // wn.parsesynset("synsets15.txt");
        HashMap<Integer, String[]> h = wn.parsehypernym("hypernyms.txt");
        Digraph dg = new Digraph(h.size());
        for (Integer val : h.keySet()) {
            vertex++;
            String[] hashMapValues = h.get(val);
            if (hashMapValues != null) {
                for (int i = 0; i < hashMapValues.length; i++) {
                    dg.addEdge(val, Integer.parseInt(hashMapValues[i]));
                    edge++;
                }
            }
        }
        System.out.println("\nNo of vertices : "+vertex + " ; No of Edges : "+ edge);
        // dg.toString()/;

    }
}
