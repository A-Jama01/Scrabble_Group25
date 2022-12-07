import java.util.*;
import java.io.Serializable;
/**
 *
 *
 * @authors Abdurahman Jama 1011626333, Monishkumar Sivakumar 101115115
 */

public class AI extends Player implements Serializable{
    private Dictionary dict;
    private static final long serialVersionUID = 1;
    private ArrayList<String> word;
    private String stringRack = "";
    private char[] rackTest;
    private ArrayList<String> position;
    private String alphabet;

    public AI(int num){
        super("AI" + num);
        dict = new Dictionary();
        word = new ArrayList<>();
        position = new ArrayList<>();
        alphabet = "ABCDEFGHIJKLMNO";
        placeCombo();
        //getAIRack();
        //rackTest = rack.toCharArray();
    }

    public String getAIRack() {
        stringRack = "";
        for(int i = 0; i< this.getRack().size(); i++){
            stringRack += this.getRack().get(i).toString();
        }
        return stringRack;
    }


    public void wordCombo(StringBuilder combos, int n, char[] rack){
        //dict = new Dictionary();
        char[] rackTest = rack;
        if (n == combos.length()) {
            if (dict.check(combos.toString()) == true && wordValid(combos.toString())){
                word.add(combos.toString());
                //placeCombo(word);
            }
            return;
        }
        for (char letter : rackTest) {
            combos.setCharAt(n, letter);
            wordCombo(combos, n + 1, rackTest);
            if (word.size() >= 10) {
                break;
            }
        }
    }

    public boolean wordValid(String input) {
        String rack = "";
        for (String s: this.getRack()) {
            rack += s;
        }

        for (char c: input.toCharArray()) {
            if (rack.indexOf(c) == - 1) { //if letter not in rack
                return false;
            }
            rack = rack.replaceFirst(Character.toString(c), "");
        }
        return true;
    }

    public void placeCombo(){
        for (int i = 0; i < 15; i++) {
            for (int j = 1; j <= 15; j++) {
                position.add(alphabet.charAt(i) + String.valueOf(j));
            }
        }
    }

    public void findWord(){ //call
        getAIRack();
        char[] rackTest = stringRack.toCharArray();
        StringBuilder combos = new StringBuilder();
        for (int length = 2; length <= rackTest.length; length++) {
            combos.setLength(length);
            wordCombo(combos, 0, rackTest);
            if (word.size() >= 10) {
                break;
            }
        }
    }

    public String getPosition() {
        return position.get((int) (Math.random() * (224)));
    }

    public String getWord(int index) {
        return word.get(index);
    }

    public String getPlay(int index) {
        return getPosition() + " " + getWord(index);
    }

    public void removeTilesAI(String words) {
        String[] tilestoRemove = words.split("");
        for (String s : tilestoRemove) {
            if (this.getRack().contains(s)) {
                this.removeTile(s);
            }
        }
        emptyWord();
    }

    public void emptyWord() {word.clear();}

}

