import java.util.HashSet;
import java.util.Set;
/**
 * Implementing BoggleSolver class.
 * @author SV Bhuvan Chandra.
 */
public class BoggleSolver {
    /**
     * Object created for TrieSET.
     */
    private final TrieSET ts;
    private int t = 0;
    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     * (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     * @param dictionary input dictionary.
     */
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException();
        }
        ts = new TrieSET();
        for (int i = 0; i < dictionary.length; i++) {
            ts.add(dictionary[i]);
        } 
    }
    /**
     * Returns the set of all valid words in the given Boggle board, as an Iterable.
     * @param board input
     * @return Iterable String.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        boolean[][] isVisited = new boolean[board.rows()][board.cols()];
        Set<String> validWords = new HashSet<String>();
        StringBuffer sb = new StringBuffer();
        for (int x = 0; x < board.rows(); x++) {
            for (int y = 0; y < board.cols(); y++) {
                checkValid(x, y, validWords, board, isVisited, sb);
            }
        }
        return validWords;
    }
    /**
     * Private method implemeted to check the Valid word from the dictionary.
     * @param i integer1
     * @param j integer2
     * @param validWords String Set.
     * @param bb BoogleBoard
     * @param isVisited true / false
     * @param sb String buffer.
     */
    private void checkValid(int i, int j, Set<String> validWords, BoggleBoard bb, boolean[][] isVisited, StringBuffer sb) {
        t++;
        if (i < 0 || j < 0 || i >= bb.rows() || j >= bb.cols()) {
            return;
        }
        if (!isVisited[i][j]) {
            isVisited[i][j] = true;
            if (bb.getLetter(i, j) == 'Q') {
                sb.append(Character.toString(bb.getLetter(i, j)));
                sb.append(Character.toString('U'));
            } else {
                sb.append(Character.toString(bb.getLetter(i, j)));
            }
            if (sb.length() >= 3) {
                if (ts.contains(sb.toString())) {
                    validWords.add(sb.toString());
                }
                if (!ts.hasPrefix(sb.toString())) {
                    
                    if (bb.getLetter(i, j) == 'Q') {
                        sb.delete(sb.length() - 2, sb.length());
                    } else {
                        sb.delete(sb.length() - 1, sb.length());
                    }
                    isVisited[i][j] = false;
                    return;
                }
            }

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    checkValid(i + x, j + y, validWords, bb, isVisited, sb);
                }
            }
            if (bb.getLetter(i, j) == 'Q') {
                sb.delete(sb.length() - 2, sb.length());
            } else {
                sb.delete(sb.length() - 1, sb.length());
            }
            isVisited[i][j] = false;
        }
        return;
    }
    /**
     * Method to implement the score of the word.
     * Returns the score of the given word if it is in the dictionary, zero otherwise.
     * (You can assume the word contains only the uppercase letters A through Z.)
     * @param word input word.
     * @return score.
     */
    public int scoreOf(String word) {
        /**
         * Field to store the score.
         */
        int score = 0;
        if (!(ts.contains(word))) {
            return 0;
        }
        if (word.length() == 3 || word.length() == 4) {
            score = 1;
        } else if (word.length() == 5) {
            score = 2;
        } else if (word.length() == 6) {
            score = 3;
        } else if (word.length() == 7) {
            score = 5;
        } else if (word.length() >= 8) {
            score = 11;
        } 
        return score;    
    }
}