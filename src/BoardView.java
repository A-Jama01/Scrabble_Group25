import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Henry Lin
 */

public class BoardView extends JFrame {

    private JButton[][] buttons;

    private ArrayList<JButton> floating;

    public BoardView() {  // add the controller as a parameter
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
                JButton tile = new JButton(Integer.toString(col) + "," + Integer.toString(row));
                tile.setActionCommand("try " + Board.column.values()[col].toString() + col);
                buttons[col][row] = tile;
                add(tile);
            }
        }
    }

    public static void main(String[] args) {
        BoardView bv = new BoardView();
        bv.setDefaultCloseOperation(EXIT_ON_CLOSE);
        bv.setSize(500, 500);
        bv.setVisible(true);
    }
}
