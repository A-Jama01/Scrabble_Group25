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
    private JButton selectedTile;
    private ArrayList<JButton> tilesPlaced;

    public ScrabbleController(Game game, GameView gameView, BoardView boardView) {
        this.game = game;
        this.boardView = boardView;
        this.gameView = gameView;
        this.selectedTile = null;
        tilesPlaced = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();

        if (button.getText().length() == 1) { //select tile from rack
            this.selectedTile = button;

        }

        /** Assume board button has setActionCommand with "x y" */
        if (boardActionCommand(e)[0].equals("try") && tileSelected()) {
            boardView.setFloatingTile(Integer.parseInt(boardActionCommand(e)[1]), Integer.parseInt(boardActionCommand(e)[2]), selectedTile.getText());
            tilesPlaced.add(selectedTile);
            selectedTile.setFocusable(false);
            selectedTile = null;
        }

        if (e.getActionCommand().equals("play")) { //play button pressed
            if (game.place(boardView.getFloatingWord()) == true) { //place() maybe takes in arraylist of tilesplaced
                game.removeTiles(stringTilesPlaced(tilesPlaced), game.getCurrPlayerIndex()); //remove tiles of current player
                game.topUpRack(game.getCurrPlayer()); //topup the rack of current player
                game.switchTurn();
                switchPlayerTiles(game.getCurrPlayer().getRack());
                tilesPlaced.removeAll(tilesPlaced);
                boardView.refresh();
            }
            else {
                resetBoard(); //print error message
                tilesPlaced.removeAll(tilesPlaced);
            }

        }

        if (e.getActionCommand().equals("skip")) { //skip button pressed
            game.switchTurn();
            switchPlayerTiles(game.getCurrPlayer().getRack());
        }

        if (e.getActionCommand().equals("reset")) {
            resetBoard();
            tilesPlaced.removeAll(tilesPlaced);
        }
    }

    public boolean tileSelected() {
        if (selectedTile != null) {
            return true;
        }
        return false;
    }

    public ArrayList<String> stringTilesPlaced(ArrayList<JButton> tilesPlaced) {
        ArrayList<String> stringTilesPlaced = new ArrayList<>();
        for (JButton j: tilesPlaced) {
            stringTilesPlaced.add(j.getText());
        }
        return stringTilesPlaced;
    }
    /**
    public String getTileLetter(String tileIndex) {
        return rackButton[Integer.parseInt(tileIndex)].getText();
    }
    */
    public String[] boardActionCommand(ActionEvent e) {
        return e.getActionCommand().split(" ");
    }
    /**
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
    */

    public void resetBoard() {
        boardView.refresh();
        for (int i = 0; i < 7; i++) {
            gameView.getButtonArray().get(i).setFocusable(true);
        }

    }

    public void switchPlayerTiles(ArrayList<String> rack) {
        for (int i = 0; i < rack.size(); i++) {
            gameView.getButtonArray().get(i).setText(rack.get(i));
            gameView.getButtonArray().get(i).setFocusable(true);
        }
    }
}
