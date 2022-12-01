import java.util.ArrayList;
import java.util.HashMap;

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
    private String[][] premiumSquares;
    public static final String EMPTY = " ";

    public enum column {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O}
    public static final int WIDTH = column.values().length;
    public static final int HEIGHT = 15;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final String DOUBLE_LETTER_SCORE = "2L";
    public static final String TRIPLE_LETTER_SCORE = "3L";
    public static final String DOUBLE_WORD_SCORE = "2W";
    public static final String TRIPLE_WORD_SCORE = "3W";

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
        premiumSquares = new String[WIDTH][HEIGHT];
    }

    /**
     * Create a Board with default size and all
     * tiles set to empty, and additionally sets
     * premium tiles using the given HashMap, which
     * contains arrays of positions mapped to their
     * respective premium tile name as a string. Use
     * the provided constants, e.g. DOUBLE_WORD_SCORE.
     * @param presets A HashMap of position arrays mapped to premium tile strings
     */
    public Board(HashMap<String[], String> presets) {
        this();
        presets.forEach((posArray, value) -> {
            for (String pos : posArray) {
                int[] tuple = translateCoordinates(pos);
                if (tuple == null) return;
                int col = tuple[0];
                int row = tuple[1];
                premiumSquares[col][row] = value;
            }
        });
    }

    public static HashMap<String[], String> defaultBoardConfiguration() {
        HashMap<String[], String> config = new HashMap<>(4);
        String[] doubleLetterTiles = {
                "D1", "L1", "G3", "I3", "A4", "H4", "O4", "C7", "G7", "I7", "M7", "D8",
                "L8", "C9", "G9", "I9", "M9", "A12", "H12", "O12", "G13", "I13", "D15", "L15"
        };
        String[] tripleLetterTiles = {
                "F2", "J2", "B6", "F6", "J6", "N6", "B10", "F10", "J10", "N10", "F14", "J14"
        };
        String[] doubleWordTiles = {
                "B2", "C3", "D4", "E5", "K5", "L4", "M3", "N2", "H8", "B14", "C13", "D12",
                "E11", "K11", "L12", "M13", "N14"
        };
        String[] tripleWordTiles = {
                "A1", "H1", "O1", "A8", "O8", "A15", "H15", "O15"
        };
        config.put(doubleLetterTiles, DOUBLE_LETTER_SCORE);
        config.put(tripleLetterTiles, TRIPLE_LETTER_SCORE);
        config.put(doubleWordTiles, DOUBLE_WORD_SCORE);
        config.put(tripleWordTiles, TRIPLE_WORD_SCORE);
        return config;
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
     * placed, lowercase if not.
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
     * Return the longest word that would be formed in the
     * given position and direction if the given word
     * were placed. Returns null if the word is 1 letter
     * or smaller, if there are conflicting letters
     * on the board, or if the word does not contain
     * any new letters.
     * @param col             The column to check
     * @param row             The row to check
     * @param dir             The desired direction (VERTICAL or HORIZONTAL)
     * @param wordBeingPlaced The word being placed
     * @param wordPosition    The position of wordBeingPlaced in notation
     * @return The word formed, or null if letters conflict or not a new word
     */
    private String longestWordFormedAt(int col, int row, int dir, String wordBeingPlaced, String wordPosition) {
        StringBuilder combined = new StringBuilder(Math.max(WIDTH, HEIGHT));
        boolean isHorizontal = dir == HORIZONTAL;
        int maxIndex = isHorizontal? WIDTH : HEIGHT;
        int startIndex = isHorizontal? col : row;

        // Look for letters behind the desired placement
        for (int indexBehind = startIndex - 1; indexBehind >= 0; indexBehind--) {
            String currentTile = isHorizontal? board[indexBehind][row] : board[col][indexBehind];
            if (currentTile.equals(EMPTY)) { break; }
            combined.insert(0, currentTile);
        }
        // Look for letters on and ahead of the desired placement (including those to be placed)
        for (int indexAhead = startIndex; indexAhead < maxIndex; indexAhead++) {
            String letterBeingPlaced = isHorizontal?
                    getWordLetterAt(indexAhead, row, wordBeingPlaced, wordPosition) :
                    getWordLetterAt(col, indexAhead, wordBeingPlaced, wordPosition);
            String currentTile = isHorizontal? board[indexAhead][row] : board[col][indexAhead];
            if (currentTile.equals(EMPTY)) {
                if (letterBeingPlaced != null) {
                    combined.append(letterBeingPlaced);  // Use the prospective letter
                } else { break; }
            } else if (letterBeingPlaced == null) {  // Letter not in main word but already placed
                combined.append(currentTile);
            } else if (letterBeingPlaced.equals(currentTile)) {  // Letter in main word and already placed
                combined.append(letterBeingPlaced);
            } else { return null; }  // Letter mismatch
        }

        if (combined.length() > 1 && !combined.toString().toUpperCase().equals(combined.toString())) {
            return combined.toString();
        }
        return null;
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
                String combined = longestWordFormedAt(currentCol, row, VERTICAL, word, position);
                if (combined != null) {
                    combinations.add(combined);
                }
            }

            // Look through all coincident rows (horizontal words)
            for (int currentRow = row; currentRow < row + verticalLength; currentRow++) {
                String combined = longestWordFormedAt(col, currentRow, HORIZONTAL, word, position);
                if (combined != null) {
                    combinations.add(combined);
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
                    premiumSquares[col + i][row] = null;
                }
            } else if (dir == VERTICAL) {
                for (int i = 0; i < word.length(); i++) {
                    board[col][row + i] = word.substring(i, i + 1);
                    premiumSquares[col][row + i] = null;
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

    /**
     * Return the letter at the given position.
     * Returns null if the position is invalid.
     * If the position is an empty premium tile,
     * return that premium tile's name string.
     * @param col The integer column of the board
     * @param row The integer row of the board
     * @return The letter or premium square at the given position, or null if invalid position
     */
    public String letterAt(int col, int row) {
        if (0 <= col && col <= WIDTH && 0 <= row && row <= HEIGHT) {
            return premiumSquares[col][row] == null? board[col][row] : premiumSquares[col][row];
        } else { return null; }
    }

    /**
     * Return an array whose entries contain the signature of any
     * open premium tiles on the board which would benefit the
     * given word. The array is the same size as the word, and
     * each entry corresponds to the letter at the same index.
     * Entries will either contain a constant string value or
     * null if there is no premium tile.
     * If the word is invalid, the returned array contains
     * all null entries.
     * @param word     The not-yet-placed word to check
     * @param position The word's position
     * @return An array containing null entries for ordinary tiles, and
     * strings for premium tiles
     */
    public String[] getWordMultipliers(String word, String position) {
        String[] multipliers = new String[word.length()];

        int[] tuple = translateCoordinates(position);
        if (tuple == null) return multipliers;
        int startingCol = tuple[0];
        int startingRow = tuple[1];
        int dir = tuple[2];
        int startingIndex = (dir == HORIZONTAL)? startingCol : startingRow;
        int wordEnd = startingIndex + word.length();

        for (int index = startingIndex; index < wordEnd; index++) {
            String tile = (dir == HORIZONTAL)? premiumSquares[index][startingRow] : premiumSquares[startingCol][index];
            if (tile != null) {
                multipliers[index - startingIndex] = tile;
            }
        }

        return multipliers;
    }
}
