//Monishkumar Sivakumar - 101115115
import java.util.HashMap;

public class Word {

    static HashMap<String, Integer> letters = new HashMap<String, Integer>();;

    public static int score(String word) {
        letters.put("A", 1);
        letters.put("B", 3);
        letters.put("C", 3);
        letters.put("D", 2);
        letters.put("E", 1);
        letters.put("F", 4);
        letters.put("G", 2);
        letters.put("H", 4);
        letters.put("I", 1);
        letters.put("J", 8);
        letters.put("K", 5);
        letters.put("L", 1);
        letters.put("M", 3);
        letters.put("N", 1);
        letters.put("O", 1);
        letters.put("P", 3);
        letters.put("Q", 10);
        letters.put("R", 1);
        letters.put("S", 1);
        letters.put("T", 1);
        letters.put("U", 1);
        letters.put("V", 4);
        letters.put("W", 4);
        letters.put("X", 8);
        letters.put("Y", 4);
        letters.put("Z", 10);

        int sum = 0;
        String[] letter = word.split("");
        for(int i = 0; i < word.length();i++) {
            sum += letters.get(letter[i]);
        }
        return sum;

    }

}
