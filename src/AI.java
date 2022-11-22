import java.util.*;

/**
 *
 *
 * @authors Abdurahman Jama 1011626333, Monishkumar Sivakumar 101115115
 */

public class AI extends Player{
    private Dictionary dict;
    private String word = "";
    private String rack = "";
    private char[] rackTest;

    private boolean check = false;

    public AI(){
        super("AI");
        for(int i = 0; i< this.getRack().size(); i++){
            rack += this.getRack().get(i).toString();
        }
        rackTest = rack.toCharArray();
    }

    public void wordCombo(StringBuilder combos, int n){
        dict = new Dictionary();
        if (n == combos.length()) {
            if (dict.check(combos.toString()) == true){
                word = combos.toString();
                placeCombo(word);
            }
            return;
        }
        for (char letter : rackTest) {
            combos.setCharAt(n, letter);
            wordCombo(combos, n + 1);
        }
    }

    public void placeCombo(String word){
        check = false;
    }

    public void findWord(){
        StringBuilder combos = new StringBuilder();
        for (int length = 2; length <= rackTest.length; length++) {
            combos.setLength(length);
            wordCombo(combos, 0);
            if (check == true){
                break;
            }

        }
    }

}

