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
        super("AI" + num);
        dict = new Dictionary();
        word = new ArrayList<>();
        position = new ArrayList<>();
        alphabet = "ABCDEFGHIJKLMNO";
        this.board = board;
        this.possiblePlays = new ArrayList<String>();

    }

    /**
     *
     * @return String representation of AI's Rack
     */
    public String getAIRack() {
        stringRack = "";
        for(int i = 0; i< this.getRack().size(); i++){
            stringRack += this.getRack().get(i).toString();
        }
        return stringRack;
    }

    /**
     * Create word combinations from AI's rack
     *
     * @param combos creates letter combinations from rack
     * @param n is the smallest letter combination
     * @param rack ai's current rack
     */
    public void wordCombo(StringBuilder combos, int n, char[] rack){
        //dict = new Dictionary();
        char[] rackTest = rack;
        if (n == combos.length()) {
            if (wordValid(combos.toString())){
                word.add(combos.toString());
            }
            return;
        }
        for (char letter : rackTest) {
            combos.setCharAt(n, letter);
            wordCombo(combos, n + 1, rackTest);
        }
    }

    /**
     * Checks if word can be made from
     *
     * @param input is word to be checked if it can be made from AI's tiles.
     * @return True if input can be made from AI's tiles, return false otherwise.
     */
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

    /**
     * Creates all letter combinations from AI's rack
     */
    public void findWord(){ //call
        getAIRack();
        char[] rackTest = stringRack.toCharArray();
        StringBuilder combos = new StringBuilder();
        for (int length = 2; length <= rackTest.length; length++) {
            combos.setLength(length);
            wordCombo(combos, 0, rackTest);
        }
    }

    /**
     * Get list of word combinations
     *
     * @return ArrayList of all possible words combinations created from the rack
     */
    public ArrayList<String> getWordList() {
        return word;
    }

    /**
     * Remove tiles used to place words.
     *
     * @param words is the word to be removed
     */
    public void removeTilesAI(String words) {
        String[] tilestoRemove = words.split("");
        for (String s : tilestoRemove) {
            if (this.getRack().contains(s)) {
                this.removeTile(s);
            }
        }
        emptyWord();
    }

    /**
     * Remove all word combinations.
     */
    public void emptyWord() {word.clear();}

    /**
     * Checks if there is any word combinations from rack.
     *
     * @return True if there are no word combinations, false otherwise.
     */
    public boolean noWords() {
        if (word.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Retrieve possible legal plays.
     *
     * @return ArrayList of possible plays.
     */
    public ArrayList<String> getPossiblePlays() {
        return possiblePlays;
    }

    /**
     * Remove all possible moves.
     */
    public void clearPossiblePlays() {possiblePlays.clear();}

    /**
     * Create possible legal moves for AI.
     */
    public void createPlays() {
        ArrayList<String> boardRows = new ArrayList<>();    //gets words in each row
        ArrayList<String> boardCols = new ArrayList<>();    //gets words in each col
        TreeMap<Integer, String> rowWords = new TreeMap<Integer, String>(); //holds starting index and word (of each word)
        TreeMap<Integer, String> colWords = new TreeMap<Integer, String>();
        int wordStartIndex = -1;
        for(int i = 0; i < board.HEIGHT; i++) {
            for (int j = 0; j < board.WIDTH; j++) {
                addLetters(boardRows, j, i);
                addLetters(boardCols, i, j);
            }
            getPosWords(boardRows, rowWords, wordStartIndex);
            getPosWords(boardCols, colWords, wordStartIndex);

            getPossiblePlays(rowWords, i, true);
            getPossiblePlays(colWords, i, false);

            boardRows.clear();
            boardCols.clear();
        }
    }

    private void getPossiblePlays(TreeMap<Integer, String> dirWords, int i, boolean isHorizontal) {
        int offset = 2;

        Set s = dirWords.entrySet();
        Iterator iterator = s.iterator();

        while (iterator.hasNext()) { //Checks if play can be made in front
            Map.Entry m = (Map.Entry)iterator.next();
            int nextWordBegIndex;
            int maxWordComboSize;
            int currEndOfWordIndex = (Integer)m.getKey() + dirWords.get((Integer)m.getKey()).length();
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
            addAfterWord(i, m, maxWordComboSize, isHorizontal);
        }
    }

    private void addAfterWord(int i, Map.Entry m, int maxWordComboSize, boolean isHorizontal) {
        ArrayList<String> combosOfRightLength = getWordsUpToNLetters(maxWordComboSize);
        String position = "";
        for (int l = 0; l < combosOfRightLength.size(); l++) {
            String tryWord = (String) m.getValue() + combosOfRightLength.get(l);
            if (!dict.checkSet(tryWord)) {
                continue;
            }
            if (isHorizontal) {
                position = String.valueOf(i + 1) + String.valueOf(Board.column.values()[(Integer) m.getKey()]);

            }
            else if (!isHorizontal) {
                position = String.valueOf(Board.column.values()[i]) + String.valueOf((Integer) m.getKey() + 1);
            }
            String play = position + " " + tryWord;
            possiblePlays.add(play);
        }
    }


    private ArrayList<String> getWordsUpToNLetters(int n) {
        ArrayList<String> upToNLetters = new ArrayList<String>();
        for (String s: this.getRack()) {
            word.add(s);
        }
        for (String s: getWordList()) {
            if (s.length() > n) {
                continue;
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

    /**
     * Create first move that is the longest legal move from rack.
     */
    public void createFirstMove() {
        ArrayList<String> words = new ArrayList<String>();
        words = getWordList();
        Collections.sort(words, (a, b)->Integer.compare(a.length(), b.length()));
        for (int i = words.size() - 1; i >= 0; i--) {
            if (dict.checkSet(words.get(i))) {
                String col = String.valueOf(Board.column.values()[((int) Math.ceil((8 - (words.get(i).length() / 2)))) - 1]);

                String play = "8" + col + " " + words.get(i);
                possiblePlays.add(play);
                break;
            }
        }
    }
}

