/**
 * ScrabbleController class defines the behavior for each user mouse input
 * on the GUI.
 *
 * @author Abdurahman Jama 101162633
 */

import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.*;

public class ScrabbleController implements ActionListener {

    private Game game;
    private String selectedTile;
    private ArrayList<String> tilesPlaced;

    public ScrabbleController(Game game) {
        this.game = game;
        this.selectedTile = "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /** Assume rack button has setActionCommand with "x" */
        for(int i = 0; i < 7; i++) { //rack button pressed Player 1
            if (e.getSource() == rackButton[i]) {
                this.selectedTile = e.getActionCommand();
            }
        }

        /** Assume board button has setActionCommand with "x y" */
        for (int i = 0; i < 15; i++) { //Board button pressed
            for (int j = 0; j < 15; j++) {
                if (e.getSource() == boardButton[i][j] && tileSelected()) {
                    boardButton[i][j].setText(getTileLetter(selectedTile));
                    boardButton[i][j].setFocusable(false);
                    tilesPlaced.add(e.getActionCommand());
                    rackButton[Integer.parseInt(selectedTile)].setFocusable(false);
                    selectedTile = "";
                }
            }
        }

        if (e.getSource() == playButton) { //play button pressed
            if (game.place() == true) { //place() maybe takes in arraylist of tilesplaced
                game.removeTiles(tilesToRemove(tilesPlaced)); //remove tiles of current play
                //game.topUpRack() topup the rack of current player
                //update gui to reflect changes
                tilesPlaced.removeAll(tilesPlaced);
            }
            else {
                resetBoard(); //print error message as well
            }

        }

        if (e.getSource() == skipButton) { //skip button pressed
            resetBoard();
            switchPlayerTiles();
            game.passTurn();
        }
    }

    public boolean tileSelected() {
        if (selectedTile.equals("")) {
            return true;
        }
        return false;
    }

    public String getTileLetter(String tileIndex) {
        return rackButton[Integer.parseInt(tileIndex)].getText();
    }

    public ArrayList<String> tilesToRemove(ArrayList<String> tilesPlaced) {
        ArrayList<String> removeTiles = new ArrayList<>();
        for (String s: tilesPlaced) {
            String[] input = s.split(" ");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);
            removeTiles.add(boardButton[x][y].getText());
        }
        return removeTiles;
    }

    public void resetBoard() {

    }

    public void switchPlayerTiles() {

    }
}
