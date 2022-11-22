/**
 * ScrabbleController class defines the behavior for each user mouse input
 * on the GUI.
 *
 * @author Abdurahman Jama 101162633
 */

import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ScrabbleController implements ActionListener {

    private Game game;
    private GameView gameView;
    private JButton selectedTile;
    private ArrayList<JButton> tilesPlaced;

    public ScrabbleController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        this.selectedTile = null;
        tilesPlaced = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /** Assume board button has setActionCommand with "x y" */
        if (boardActionCommand(e)[0].equals("try") && tileSelected()) {
            gameView.placeTile(Integer.parseInt(boardActionCommand(e)[1]), Integer.parseInt(boardActionCommand(e)[2]), selectedTile.getText());
            tilesPlaced.add(selectedTile);
            selectedTile.setEnabled(false);
            selectedTile = null;
        }

        else if (e.getActionCommand().equals("play")) { //play button pressed
            if (game.place(gameView.getPlacedWord()) == true) { //place() maybe takes in arraylist of tilesplaced

                if(game.getCurrPlayerIndex() == 0) {
                    gameView.updateInfo("Player 1 turn made.", "Make your turn now Player 2.");
                }
                else{
                    gameView.updateInfo("Player 2 turn made.", "Make your turn now Player 1.");
                }

                game.removeTiles(stringTilesPlaced(tilesPlaced), game.getCurrPlayerIndex()); //remove tiles of current player
                game.topUpRack(game.getCurrPlayer()); //topup the rack of current player
                game.switchTurn();
                switchPlayerTiles(game.getCurrPlayer().getRack());
                tilesPlaced.removeAll(tilesPlaced);
                gameView.updateBoard();
            }
            else {
                resetBoard(); //print error message
                tilesPlaced.removeAll(tilesPlaced);
                gameView.updateInfo("Invalid word.", "Please pick a new word.");
            }

        }

        else if (e.getActionCommand().equals("skip")) { //skip button pressed
            game.switchTurn();
            switchPlayerTiles(game.getCurrPlayer().getRack());
            if(game.getCurrPlayerIndex() == 0) {
                gameView.updateInfo("Player 1 turn skipped.", "Make your turn now Player 2.");
            }
            else{
                gameView.updateInfo("Player 2 turn skipped.", "Make your turn now Player 1.");
            }
        }

        else if (e.getActionCommand().equals("reset")) {
            resetBoard();
            tilesPlaced.removeAll(tilesPlaced);
        }

        else if (e.getActionCommand().split(" ")[0].equals("pick")) {
            selectedTile = gameView.getButtonArray().get(Integer.parseInt(e.getActionCommand().split(" ")[1]));
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

    public void resetBoard() {
        gameView.updateBoard();
        for (int i = 0; i < 7; i++) {
            gameView.getButtonArray().get(i).setEnabled(true);
        }

    }

    public void switchPlayerTiles(ArrayList<String> rack) {
        for (int i = 0; i < rack.size(); i++) {
            gameView.getButtonArray().get(i).setText(rack.get(i));
            gameView.getButtonArray().get(i).setEnabled(true);
        }
    }

}
