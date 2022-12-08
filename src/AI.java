import java.util.*;

/**
 *
 *
 * @authors Abdurahman Jama 1011626333, Monishkumar Sivakumar 101115115
 */

public class AI extends Player{
    private Dictionary dict;
    private ArrayList<String> word;
    private String stringRack = "";
    private char[] rackTest;
    private ArrayList<String> position;
    private String alphabet;
    private Board board;
    private ArrayList<String> possiblePlays;

    public AI(int num, Board board){
        super("AI" + num, 1);
        dict = new Dictionary();
        word = new ArrayList<>();
        position = new ArrayList<>();
        alphabet = "ABCDEFGHIJKLMNO";
        this.board = board;
        this.possiblePlays = new ArrayList<String>();
        for (String s: this.getRack()) {
            word.add(s);
        }
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
            if (/*dict.checkSet(combos.toString()) && */wordValid(combos.toString())){
                word.add(combos.toString());
                //placeCombo(word);
            }
            return;
        }
        for (char letter : rackTest) {
            combos.setCharAt(n, letter);
            wordCombo(combos, n + 1, rackTest);
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
                position.add(String.valueOf(j) + alphabet.charAt(i));
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
            /**
            if (word.size() >= 10) {
                break;
            }
             */
        }
    }

    public String getPosition(int index) {
        return position.get(index);
    }

    public String getWord(int index) {
        return word.get(index);
    }

    public ArrayList<String> getWordList() {
        return word;
    }

    public String getPlay(int postionIndex, int wordIndex) {
        return getPosition(postionIndex) + " " + getWord(wordIndex);
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

    public boolean noWords() {
        if (word.size() == 0) {
            return true;
        }
        return false;
    }

    public int numOfWords() {
        return word.size();
    }

    public int numOfPositions() {
        return position.size();
    }

    public ArrayList<String> getWordsOnBoard() {
        ArrayList<String> wordList = new ArrayList<>();

        return wordList;
    }

    public ArrayList<String> getPossiblePlays() {
        return possiblePlays;
    }

    public void clearPossiblePlays() {possiblePlays.clear();}

    public void createPlays() {
        ArrayList<String> boardRows = new ArrayList<>();
        TreeMap<Integer, String> rowWords = new TreeMap<Integer, String>();
        int wordStartIndex = -1;
        for(int i = 0; i < board.HEIGHT; i++) {
            for (int j = 0; j < board.WIDTH; j++) {
                addLetters(boardRows, j, i);
            }
            getPosWords(boardRows, rowWords, wordStartIndex);

            int offset = 2;

            Set s = rowWords.entrySet();
            Iterator iterator = s.iterator();

            while (iterator.hasNext()) { //Checks if play can be made in front
                Map.Entry m = (Map.Entry)iterator.next();
                int nextWordBegIndex;
                int maxWordComboSize;
                int currEndOfWordIndex = (Integer)m.getKey() + rowWords.get((Integer)m.getKey()).length();
                if (iterator.hasNext()) {
                    nextWordBegIndex = (Integer)((Map.Entry)iterator.next()).getKey();
                }
                else {
                    nextWordBegIndex = board.WIDTH;
                }
                maxWordComboSize = nextWordBegIndex - (currEndOfWordIndex + offset);

                if (maxWordComboSize <= 0) {//go to next word if not enough space to place new tiles
                    continue;
                }
                ArrayList<String> combosOfRightLength = getWordsUpToNLetters(maxWordComboSize);

                for (int l = 0; l < combosOfRightLength.size(); l++) {
                    String tryWord = (String)m.getValue() + combosOfRightLength.get(l);
                    if (!dict.checkSet(tryWord)) {
                        continue;
                    }
                    String position = String.valueOf(i + 1) + String.valueOf(Board.column.values()[(Integer)m.getKey()]);
                    String play = position + " " + tryWord;
                    possiblePlays.add(play);
                }

            }
            boardRows.clear();
        }
    }


    private ArrayList<String> getWordsUpToNLetters(int n) {
        ArrayList<String> upToNLetters = new ArrayList<String>();
        for (String s: getWordList()) {
            if (s.length() > n) {
                break;
            }
            upToNLetters.add(s);
        }
        return upToNLetters;
    }

    private void getPosWords(ArrayList<String> boardRows, TreeMap<Integer, String> posWords, int wordStartIndex) {
        String word = "";
        for (int k = 0; k < boardRows.size(); k++) {
            if (!boardRows.get(k).equals(" ")) {
                word += boardRows.get(k);
                if (word.length() == 1) {
                    wordStartIndex = k;
                }
            }
            else if (!word.equals("") || ((k == boardRows.size() - 1) && !word.equals(""))) {
                posWords.put(wordStartIndex, word);
                word = "";
            }
        }
    }

    private void addLetters(ArrayList<String> boardRows, int i, int j) {
        if (isEmptyPosition(board.letterAt(i, j))) {
            boardRows.add(" ");
        }
        else {
            boardRows.add(board.letterAt(i, j));
        }
    }

    public boolean isEmptyPosition(String letter) {
        if (letter == null) {
            return true;
        }
        if (letter.equals(board.DOUBLE_LETTER_SCORE)) {
            return true;
        }
        if (letter.equals(board.TRIPLE_LETTER_SCORE)) {
            return true;
        }
        if (letter.equals(board.DOUBLE_WORD_SCORE)) {
            return true;
        }
        if (letter.equals(board.TRIPLE_WORD_SCORE)) {
            return true;
        }
        return false;
    }

}

