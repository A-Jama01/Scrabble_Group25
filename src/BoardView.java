import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Henry Lin
 */

public class BoardView extends JPanel {

    private JButton[][] buttons;

    /**
     * Floating tiles are letters that a player
     * is attempting to place on the board. They
     * are displayed visually on the BoardView,
     * but do not affect the actual state of the Board.
     */
    private ArrayList<JButton> floating;
    private int floatingCol;
    private int floatingRow;
    private int floatingDir;

    private Board board;

    public BoardView(Board board) {  // add the controller as a parameter
        this.board = board;
        floating = new ArrayList<>();
        setLayout(new GridLayout(Board.WIDTH + 1, Board.HEIGHT + 1));

        buttons = new JButton[Board.WIDTH][Board.HEIGHT];
        add(new JLabel());
        for (int i = 0; i < Board.WIDTH; i++) {
            JLabel label = new JLabel(Board.column.values()[i].toString());
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
        }
        for (int row = 0; row < Board.HEIGHT; row++) {
            JLabel label = new JLabel(Integer.toString(row + 1));
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            for (int col = 0; col < Board.HEIGHT; col++) {
                JButton tile = new JButton();
                tile.setActionCommand("try " + col + " " + row);
                // addActionListener(controller);
                buttons[col][row] = tile;
                add(tile);
            }
        }
    }

    /**
     * Update the BoardView to reflect the current state of
     * the Board, removing any floating tiles. Disable
     * buttons for placed letters.
     */
    public void refresh() {
        for (int col = 0; col < Board.WIDTH; col++) {
            for (int row = 0; row < Board.HEIGHT; row++) {
                buttons[col][row].setText(board.letterAt(col, row));
                buttons[col][row].setEnabled(board.letterAt(col, row).equals(Board.EMPTY));
            }
        }
        floating.clear();
    }

    /**
     * Place a floating letter on the board. If there is
     * already a floating letter there, returns that letter
     * and replaces it with the given letter.
     * @param col    The column on the grid
     * @param row    The row on the grid
     * @param letter The letter to place
     * @return A letter that was swapped out, or null if none
     */
    public String setFloatingTile(int col, int row, String letter) {
        switch (floating.size()) {
            case 0:
                floatingCol = col;
                floatingRow = row;
                break;
            case 1:
                if (col == floatingCol) {
                    floatingDir = Board.VERTICAL;
                } else if (row == floatingRow) {
                    floatingDir = Board.HORIZONTAL;
                } else { return null; }
                break;
            default:
                if (floatingDir == Board.VERTICAL && col != floatingCol) {
                    return null;
                } else if (floatingDir == Board.HORIZONTAL && row != floatingRow) {
                    return null;
                }
        }
        JButton target = buttons[col][row];
        floating.add(target);
        if (target.getText().equals(Board.EMPTY)) {
            target.setText(letter);
            return null;
        } else {
            String swapped = target.getText();
            target.setText(letter);
            return swapped;
        }
    }

    /**
     * Return the main word created by the floating tiles and
     * existing tiles currently on the BoardView.
     * Returns null if there are no floating tiles or if there
     * are disconnected floating tiles.
     * @return The word created by floating tiles, or null if no word
     */
    public String getFloatingWord() {
        if (floating.size() == 0) { return null; }
        if (floating.size() == 1 && floatingDir != Board.VERTICAL) { floatingDir = Board.HORIZONTAL; }
        /* If there is only one floating letter, the direction is not certain.
        Try horizontal first. The other direction will be tried if a one-letter word is formed. */

        StringBuilder word = new StringBuilder(Math.max(Board.WIDTH, Board.HEIGHT));
        int lettersUsed = 0;
        int upperLimit = (floatingDir == Board.HORIZONTAL)? Board.WIDTH : Board.HEIGHT;
        int startingLetterIndex = (floatingDir == Board.HORIZONTAL)? floatingCol : floatingRow;
        for (int index = startingLetterIndex - 1; 0 <= index; index--) {
            JButton tile = (floatingDir == Board.HORIZONTAL)?
                    buttons[index][floatingRow] : buttons[floatingCol][index];
            if (tile.getText().equals(Board.EMPTY)) {
                break;
            } else if (floating.contains(tile)) {
                lettersUsed++;
            }
            word.insert(0, tile.getText());
        }
        for (int index = startingLetterIndex; index < upperLimit; index++) {
            JButton tile = (floatingDir == Board.HORIZONTAL)?
                    buttons[index][floatingRow] : buttons[floatingCol][index];
            if (tile.getText().equals(Board.EMPTY)) {
                break;
            } else if (floating.contains(tile)) {
                lettersUsed++;
            }
            word.append(tile.getText());
        }
        if (lettersUsed == floating.size()) {
            if (floating.size() == 1 && word.length() == 1 && floatingDir != Board.VERTICAL) {
                // A one-letter word was formed and the vertical has not been tried;
                floatingDir = Board.VERTICAL;
                return getFloatingWord();
            }
            return word.toString();
        }
        return null;
    }
}
