import java.io.*;
import java.util.*;
/**
 * Class Wordnet to perform two methods for splitting files.
 * @author S.V.Bhuvan Chandra
 */
public class WordNet {
    /**
     * Method to split synsets from the text file given.
     * @param s1 file name
     * @return splitted keys, values
     * @throws IOException throws exceptions
     */
    public HashMap<Integer, String[]> parsesynset (String s1) throws IOException {
        /**
         * implementation for reading synset files.
         */
        FileReader file1 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\"+s1);
        BufferedReader br1 = new BufferedReader(file1);
        String str1;
        HashMap<Integer, String[]> h1 = new HashMap<Integer, String[]>();
        while((str1 = br1.readLine()) != null) {
            String[] sa1 = str1.split(",",2);
            // String[] val1 = sa1[1].split(",");
            String[] val1 = Arrays.copyOfRange(sa1, 1, sa1.length);
            h1.put(Integer.parseInt(sa1[0]),val1);
        }
        for(Integer val:h1.keySet()) {
            String key = val.toString();
            String value = Arrays.toString(h1.get(val));
            System.out.println(key+" "+value);
        }
        br1.close();
        return h1;
    }
    /**
     * Method to split hypernyms from the text file given.
     * @param s2 file name
     * @return splitted keys, values
     * @throws IOException throws exceptions
     */
    public HashMap<Integer, String[]> parsehypernym (String s2) throws IOException {
        /**
         * Implementation for hypernymns file reading.
         */
        FileReader file2 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\Day_1\\"+s2);
        BufferedReader br2 = new BufferedReader(file2);
        String str2;
        HashMap<Integer, String[]> h2 = new HashMap<Integer, String[]>();
        while((str2 = br2.readLine()) != null) {
            String[] sa2 = str2.split(",",2);
            // String[] val2 = sa2[1].split(",");
            String[] val2 = Arrays.copyOfRange(sa2, 1, sa2.length);
            h2.put(Integer.parseInt(sa2[0]),val2);
        }
        for (Integer val:h2.keySet()) {
            String key = val.toString();
            String value = Arrays.toString(h2.get(val));
            System.out.println(key+" "+value);
        }
        br2.close();
        return h2;
    }
    /**
     * Main method impelmentation.
     * @param args input arguments
     * @throws IOException throws any exception.
     */
    public static void main(String[] args) throws IOException {
        WordNet wn = new WordNet();
        wn.parsesynset("synsets11.txt");
        wn.parsehypernym("hypernyms15Path.txt");
    } 
}

