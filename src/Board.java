import java.util.ArrayList;

/**
 * Models the board in Scrabble.
 * Stores the state of the board and provides methods to
 * place a new word, get all words that are created by
 * placing a new word, and return a string visualization
 * of the board.
 * The board is labeled with letters for columns (A-O),
 * and numbers for rows (1-15).
 *
 * @author Henry Lin
 */

public class Board {
    private String[][] board;
    public static final String EMPTY = " ";

    public enum column {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O}
    public static final int WIDTH = column.values().length;
    public static final int HEIGHT = 15;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    /**
     * Create a Board object with default size and
     * all tiles set to empty.
     */
    public Board() {
        board = new String[WIDTH][HEIGHT];
        for (int col = 0; col < WIDTH; col++) {
            for (int row = 0; row < HEIGHT; row++) {
                board[col][row] = EMPTY;
            }
        }
    }

    /**
     * Return an array containing information about the
     * given position in scrabble notation in the following
     * format:
     * {column, row, direction},
     * where direction is 0 if horizontal or 1 if vertical,
     * and column and row are integer indices of the board
     * suitable for a 2D array.
     * If coordinate is not valid, returns null.
     *
     * @param position A position in scrabble notation
     * @return An array containing the column, row, direction, or
     * null if position is invalid
     */
    private static int[] translateCoordinates(String position) {
        try {
            int col;
            int row;
            int dir;
            if (Character.isAlphabetic(position.charAt(0))) {
                // Vertical position
                col = column.valueOf(position.substring(0, 1)).ordinal();
                row = Integer.parseInt(position.substring(1)) - 1;
                dir = VERTICAL;
            } else if (position.length() == 2) {
                // Horizontal position (single digit)
                row = Integer.parseInt(position.substring(0, 1)) - 1;
                col = column.valueOf(position.substring(1, 2)).ordinal();
                dir = HORIZONTAL;
            } else if (position.length() == 3) {
                // Horizontal position (two digit)
                row = Integer.parseInt(position.substring(0, 2)) - 1;
                col = column.valueOf(position.substring(2, 3)).ordinal();
                dir = HORIZONTAL;
            } else { return null; }
            if (HEIGHT < row + 1) { return null; }
            return new int[]{col, row, dir};
        } catch (StringIndexOutOfBoundsException | IllegalArgumentException | NullPointerException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Return the letter of the specified word which should
     * lie on the specified tile. The word does not have to
     * be already placed on the board. If the tile
     * does not belong to the word, return null.
     * The letter is uppercase if the tile is already
     * occupied, lowercase if it is empty.
     *
     * @param col     The column to check
     * @param row     The row to check
     * @param word    The word
     * @param wordPos The position of the word
     * @return The letter on the tile, or null if not part of word
     */
    private String getWordLetterAt(int col, int row, String word, String wordPos) {
        int[] tuple = translateCoordinates(wordPos);
        if (tuple == null) { return null; }
        int startCol = tuple[0];
        int startRow = tuple[1];
        int dir = tuple[2];

        String letter = null;

        if (dir == HORIZONTAL) {
            if (row == startRow && startCol <= col && col < startCol + word.length()) {
                int index = col - startCol;
                letter = word.substring(index, index + 1);
            }
        } else {  // dir == VERTICAL
            if (col == startCol && startRow <= row && row < startRow + word.length()) {
                int index = row - startRow;
                letter = word.substring(index, index + 1);
            }
        }
        if (letter != null && board[col][row].equals(EMPTY)) { letter = letter.toLowerCase(); }

        return letter;
    }

    /**
     * Return all the words which would be created by the
     * placement of the given word at the specified position
     * (in scrabble notation), regardless of legality.
     * If the placement is not possible due to conflicting
     * letters already placed on the board or insufficient
     * space, returns null.
     * Returned words will have uppercase letters where
     * the letter already exists on the board, and
     * lowercase letters where they are yet to be placed.
     *
     * @param word     The word to be placed, in uppercase
     * @param position The position to place the word in scrabble notation
     * @return An ArrayList of Strings containing all combinations, or
     * null if the placement is not possible
     */
    public ArrayList<String> getCombinationsWith(String word, String position) {
        ArrayList<String> combinations = new ArrayList<>();

        int[] tuple = translateCoordinates(position);
        if (tuple != null) {
            int col = tuple[0];
            int row = tuple[1];
            int dir = tuple[2];
            int horizontalLength;
            int verticalLength;
            if (dir == HORIZONTAL && word.length() <= WIDTH - col) {
                // There is space in the row
                horizontalLength = word.length();
                verticalLength = 1;
            } else if (dir == VERTICAL && word.length() <= HEIGHT - row) {
                // There is space in the column
                horizontalLength = 1;
                verticalLength = word.length();
            } else { return null; }

            // Look through all coincident columns (vertical words)
            for (int currentCol = col; currentCol < col + horizontalLength; currentCol++) {
                String combined = "";
                // Look for letters above the desired placement
                for (int upRow = row - 1; upRow >= 0
                        && !board[currentCol][upRow].equals(EMPTY); upRow--) {
                    combined = board[currentCol][upRow] + combined;
                }
                // Look for letters below the desired placement (including those to be placed)
                for (int downRow = row; downRow < HEIGHT; downRow++) {
                    String currentLetter = getWordLetterAt(currentCol, downRow, word, position);
                    if (board[currentCol][downRow].equals(EMPTY)) {
                        if (currentLetter != null) {
                            combined += currentLetter;  // Use the prospective letter
                        } else { break; }
                    } else if (currentLetter == null) {  // Letter not in main word
                        combined += board[currentCol][downRow];
                    } else if (currentLetter.equals(board[currentCol][downRow])) {  // Letter in main word
                        combined += currentLetter;
                    } else { return null; }  // Letter mismatch
                }

                if (combined.length() > 1 && !combined.toUpperCase().equals(combined)) {
                    combinations.add(combined);  // Only add words that coincide with new tiles
                }
            }

            // Look through all coincident rows (horizontal words)
            for (int currentRow = row; currentRow < row + verticalLength; currentRow++) {
                String combined = "";
                // Look for letters left of the desired placement
                for (int leftCol = col - 1; leftCol >= 0
                        && !board[leftCol][currentRow].equals(EMPTY); leftCol--) {
                    combined = board[leftCol][currentRow] + combined;
                }
                // Look for letters right of the desired placement (including those to be placed)
                for (int rightCol = col; rightCol < WIDTH; rightCol++) {
                    String currentLetter = getWordLetterAt(rightCol, currentRow, word, position);
                    if (board[rightCol][currentRow].equals(EMPTY)) {
                        if (currentLetter != null) {
                            combined += currentLetter;  // Use the prospective letter
                        } else { break; }
                    } else if (currentLetter == null) {  // Letter not in main word
                        combined += board[rightCol][currentRow];
                    } else if (currentLetter.equals(board[rightCol][currentRow])) {  // Letter in main word
                        combined += currentLetter;
                    } else { return null; }  // Letter mismatch
                }

                if (combined.length() > 1 && !combined.toUpperCase().equals(combined)) {
                    combinations.add(combined);  // Only add words that coincide with new tiles
                }
            }

            return combinations;
        }
        return null;
    }

    /**
     * Take a word and a position (in scrabble notation)
     * and attempt to place it on the board. If placement
     * is successful, returns true. Returns false otherwise.
     * If the position is specified with row first, then
     * the word is placed horizontally. If column is first,
     * then the word is placed vertically.
     *
     * @param word     The word to be placed
     * @param position The position of the word in scrabble notation
     * @return true if placement is successful, false otherwise
     */
    public boolean place(String word, String position) {
        int[] tuple = translateCoordinates(position);
        if (tuple == null) { return false; }
        int col = tuple[0];
        int row = tuple[1];
        int dir = tuple[2];

        if (getCombinationsWith(word, position) != null) {
            if (dir == HORIZONTAL) {
                for (int i = 0; i < word.length(); i++) {
                    board[col + i][row] = word.substring(i, i + 1);
                }
            } else if (dir == VERTICAL) {
                for (int i = 0; i < word.length(); i++) {
                    board[col][row + i] = word.substring(i, i + 1);
                }
            }
            return true;
        } else { return false; }
    }

    /**
     * Check which letters of a word are already on the board,
     * and return the word with lowercase letters where the
     * letters are not yet placed.
     *
     * @param word     The word, in uppercase
     * @param position The word's position
     * @return The word with non-placed letters in lowercase,
     * or null if placed letters conflict with the word
     */
    public String checkLetters(String word, String position) {
        ArrayList<String> combinations = getCombinationsWith(word, position);
        if (combinations != null) {
            for (String combo : combinations) {
                if (combo.equalsIgnoreCase(word)) { return combo; }
            }
        }
        return null;
    }

    /**
     * Return whether a given word crosses the centre square.
     * @param word     The word
     * @param position The word's position
     * @return true if the word crosses centre, false otherwise
     */
    public boolean wordCrossesCentre(String word, String position) {
        return getWordLetterAt(Math.ceilDiv(WIDTH, 2) - 1, Math.ceilDiv(HEIGHT, 2) - 1, word, position) != null;
    }

    /**
     * Return whether a given word is attached to other existing words.
     * Will also return false if the word cannot be placed.
     * @param word     The word
     * @param position The word's position
     * @return true if the word is attached to at least one other word,
     * false otherwise
     */
    public boolean wordIsAttached(String word, String position) {
        ArrayList<String> combinations = getCombinationsWith(word, position);
        if (combinations == null) { return false; }
        return !(combinations.size() == 1 && combinations.get(0).equals(combinations.get(0).toLowerCase()));
    }

    /**
     * Return a string representation of the board. Will
     * only render properly if printed in a monospace font.
     *
     * @return A string representation of the board
     */
    public String toString() {
        String s = "";
        for (column c : column.values()) {
            s += " " + c.toString().toLowerCase();
        }
        for (int row = 0 ; row < board[0].length ; row++) {
            s += "\n ";
            for (int col = 0; col < board.length ; col++) {
                s += board[col][row] + " ";
            }
            s += row + 1;
        }
        return s;
    }
}
