/**
 * ScrabbleController class defines the behavior for each user mouse input
 * on the GUI.
 *
 * @author Abdurahman Jama 101162633
 */

import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

public class ScrabbleController implements ActionListener {

    private Game game;
    private BoardView boardView;
    private GameView gameView;
    private String selectedTile;
    private ArrayList<String> tilesPlaced;

    public ScrabbleController(Game game, GameView gameView, BoardView boardView) {
        this.game = game;
        this.boardView = boardView;
        this.gameView = gameView;
        this.selectedTile = "";
        tilesPlaced = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();

        if (button.getText().length() == 1) { //select tile from rack
            this.selectedTile = button.getText();
        }

        /** Assume board button has setActionCommand with "x y" */
        if (boardActionCommand(e)[0].equals("try") && tileSelected()) {
            boardView.setFloatingTile(Integer.parseInt(boardActionCommand(e)[1]), Integer.parseInt(boardActionCommand(e)[2]), selectedTile);
            tilesPlaced.add(e.getSource().getText());
            buttonLetters1[Integer.parseInt(selectedTile)].setFocusable(false);
            selectedTile = "";
        }

        if (e.getActionCommand().equals("play")) { //play button pressed
            if (game.place() == true) { //place() maybe takes in arraylist of tilesplaced
                game.removeTiles(tilesPlaced, game.getCurrPlayer()); //remove tiles of current player
                //game.topUpRack(); //topup the rack of current player
                //game.switchTurn();
                switchPlayerTiles(getCurrPlayer().getRack());
                tilesPlaced.removeAll(tilesPlaced);
                boardView.refresh();
            }
            else {
                resetBoard(); //print error message
            }

        }

        if (e.getActionCommand().equals("skip")) { //skip button pressed
            game.switchTurn();
            switchPlayerTiles(getCurrPlayer().getRack());
        }

        if (e.getActionCommand().equals("reset")) {
            resetBoard();
            tilesPlaced.removeAll(tilesPlaced);
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

    public String[] boardActionCommand(ActionEvent e) {
        return e.getActionCommand().split(" ");
    }

    public ArrayList<String> tilesToRemove(ArrayList<String> tilesPlaced) {//r
        ArrayList<String> removeTiles = new ArrayList<>();
        for (String s: tilesPlaced) {
            String[] input = s.split(" ");
            int x = Integer.parseInt(input[2]);
            int y = Integer.parseInt(input[1]);
            removeTiles.add(boardButton[x][y].getText());
        }
        return removeTiles;
    }

    public void resetBoard() {
        boardView.refresh();
        for (int i = 0; i < 7; i++) {
            rackButton[i].setFocusable(true);
        }

    }

    public void switchPlayerTiles(ArrayList<String> rack) {
        for (int i = 0; i < rack.size(); i++) {
            rackButton[i].setText(rack.get(i));
            rackButton[i].setFocusable(true);
        }
    }
}
