import java.util.*;

/**
 *
 *
 * @authors Abdurahman Jama 1011626333, Monishkumar Sivakumar 101115115
 */

public class AI extends Player{
    private Dictionary dict;
    private ArrayList<String> word;
    private String rack = "";
    private char[] rackTest;
    private ArrayList<String> position;
    private String alphabet;

    public AI(){
        super("AI");
        dict = new Dictionary();
        word = new ArrayList<>();
        alphabet = "ABCDEFGHIJKLMNO";
        placeCombo();
        for(int i = 0; i< this.getRack().size(); i++){
            rack += this.getRack().get(i).toString();
        }
        rackTest = rack.toCharArray();
    }

    public void wordCombo(StringBuilder combos, int n){
        //dict = new Dictionary();
        if (n == combos.length()) {
            if (dict.check(combos.toString()) == true){
                word.add(combos.toString());
                //placeCombo(word);
            }
            return;
        }
        for (char letter : rackTest) {
            combos.setCharAt(n, letter);
            wordCombo(combos, n + 1);
        }
    }

    public void placeCombo(){
        for (int i = 0; i < 15; i++) {
            for (int j = 1; j <= 15; j++) {
                position.add(alphabet.charAt(i) + String.valueOf(j));
            }
        }
    }

    public void findWord(){ //call
        StringBuilder combos = new StringBuilder();
        for (int length = 2; length <= rackTest.length; length++) {
            combos.setLength(length);
            wordCombo(combos, 0);
        }
    }

    public String getPosition() {
        return position.get((int) (Math.random() * (224)));
    }

    public String getWord(int index) {
        return word.get(index);
    }

    public String getPlay(int index) {
        return getPosition() + getWord(index);
    }

}

