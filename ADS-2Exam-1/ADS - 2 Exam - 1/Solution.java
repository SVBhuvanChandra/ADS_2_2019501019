import java.io.*;
import java.util.*;

public class Solution {
    HashMap<String, String> emailHash = new HashMap<>();
    HashMap<String, ArrayList<String>> logsHash = new HashMap<>();

    public void parseEmail (String filename) throws IOException {
        FileReader file1 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\ADS-2Exam-1\\ADS - 2 Exam - 1\\" + filename);
        BufferedReader br1 = new BufferedReader(file1);
        String line1;
        while ((line1 = br1.readLine()) != null) {
            String[] temp1 = line1.split(";");
            emailHash.put(temp1[0], temp1[1]);
        }
        br1.close();
        System.out.println(emailHash.get("3"));
    }

    public void parseLogs (String filename1) throws IOException {
        FileReader  file2 = new FileReader("E:\\ADS1\\ADS_2_2019501019\\ADS_2_2019501019\\ADS-2Exam-1\\ADS - 2 Exam - 1\\" + filename1);
        BufferedReader br2 = new BufferedReader(file2);
        String line2;
        while ((line2 = br2.readLine()) != null) {
        String[] temp2 = line2.split(" |,| | ");
            ArrayList<String> al = new ArrayList<String>();
            String fromS = temp2[1];
            String toR = temp2[4];
            if (logsHash.containsKey(toR)) {
                logsHash.get(toR);
            } else {
                // al = new ArrayList<String>();
                logsHash.put(toR, al);
            }
            al.add(fromS);
        }
        br2.close();
        System.out.println(logsHash.get("43120"));
        for (String x : logsHash.keySet()) {
            // int y = logsHash.get(x);
            System.out.println(x + " got from" + logsHash.get(x));
        }
    }

    public int lengthFinder(ArrayList<String> a) {
        int len = a.size();
        return len;
    }

    public HashMap<String, Integer> counting(HashMap<String, ArrayList<String>> h) {
        HashMap<String, Integer> counter = new HashMap<>();
        for (String key : h.keySet()) {
            int noOfEmails = lengthFinder(h.get(key));
            counter.put(key, noOfEmails);
        }
        // for (String x : counter.keySet()) {
        //     int y = counter.get(x);
        //     System.out.println(x + " got " + y);
        // }
        return counter;
    } 


    public static void main(String[] args) throws IOException {
        Solution sol = new Solution();
        sol.parseEmail("emails.txt");
        sol.parseLogs("email-logs.txt");
        sol.counting(sol.logsHash);
        }
    }
