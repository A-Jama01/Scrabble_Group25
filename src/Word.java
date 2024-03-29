/** Word Class - The class calculates the score of the word
* 
* @author Monishkumar Sivakumar 101115115
**/
import java.util.HashMap;
import java.io.Serializable;
public class Word implements Serializable{
    private static final long serialVersionUID = 1;
    private HashMap<String, Integer> letters = new HashMap<String, Integer>();

    //This function initializes the values for the Scrabble Letters
    public Word(){
        this.letters.put("A", 1);
        this.letters.put("B", 3);
        this.letters.put("C", 3);
        this.letters.put("D", 2);
        this.letters.put("E", 1);
        this.letters.put("F", 4);
        this.letters.put("G", 2);
        this.letters.put("H", 4);
        this.letters.put("I", 1);
        this.letters.put("J", 8);
        this.letters.put("K", 5);
        this.letters.put("L", 1);
        this.letters.put("M", 3);
        this.letters.put("N", 1);
        this.letters.put("O", 1);
        this.letters.put("P", 3);
        this.letters.put("Q", 10);
        this.letters.put("R", 1);
        this.letters.put("S", 1);
        this.letters.put("T", 1);
        this.letters.put("U", 1);
        this.letters.put("V", 4);
        this.letters.put("W", 4);
        this.letters.put("X", 8);
        this.letters.put("Y", 4);
        this.letters.put("Z", 10);
        this.letters.put(Bag.BLANK_TILE, 0);
    }

    //This function will check the score of the word inputted
    public int score(String word) {
        int sum = 0;
        String[] letter = word.toUpperCase().split("");
        for(int i = 0; i < word.length();i++) {
            sum += this.letters.get(letter[i]);
        }
        return sum;

    }

    public int scoreWithPremiums(String word, String[] premiums) {
        int wordValue = 1;
        int sum = 0;
        String[] letter = word.toUpperCase().split("");

        for (int i = 0; i < word.length(); i++) {
            int letterValue = 1;
            if (premiums[i] != null) {
                if (premiums[i].equals(Board.DOUBLE_LETTER_SCORE)) {
                    letterValue = 2;
                } else if (premiums[i].equals(Board.TRIPLE_LETTER_SCORE)) {
                    letterValue = 3;
                } else if (premiums[i].equals(Board.DOUBLE_WORD_SCORE)) {
                    wordValue = 2;
                } else if (premiums[i].equals(Board.TRIPLE_WORD_SCORE)) {
                    wordValue = 3;
                } else if (premiums[i].equals(Bag.BLANK_TILE)) {
                    letterValue = 0;
                }
                sum += this.letters.get(letter[i]) * letterValue;
            }
            else{
                letterValue = 1;
                sum += this.letters.get(letter[i]) * letterValue;
            }
        }
        return sum * wordValue;
    }

}
