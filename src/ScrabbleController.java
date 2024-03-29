/**
 * ScrabbleController class defines the behavior for each user mouse input
 * on the GUI.
 *
 * @author Abdurahman Jama 101162633
 */

import java.awt.event.*;
import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.Serializable;

public class ScrabbleController implements ActionListener, Serializable {
    private static final long serialVersionUID = 1;
    private Game game;
    private GameView gameView;
    private JButton selectedTile;
    private ArrayList<JButton> tilesPlaced;

    private int placeCount;
    private int totalCount;

    public ScrabbleController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        this.selectedTile = null;
        tilesPlaced = new ArrayList<>();
        this.placeCount = 0;
        this.totalCount = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /** Assume board button has setActionCommand with "x y" */
        if (boardActionCommand(e)[0].equals("try") && tileSelected()) {
            String placeTile = gameView.placeTile(Integer.parseInt(boardActionCommand(e)[1]), Integer.parseInt(boardActionCommand(e)[2]), selectedTile.getText());
            if (placeTile == null) { // Don't place tile if placement is incorrect
                return;
            }
            if (tilesPlaced.size() >= 1 && isTilePlaced(placeTile)) { //swap letter case
                tilesPlaced.get(getPlacedTile(placeTile)).setEnabled(true);
                tilesPlaced.remove(getPlacedTile(placeTile)); //remove swapped tile from list of placed tiles
            }
            tilesPlaced.add(selectedTile);
            selectedTile.setEnabled(false);
            selectedTile = null;
        }

        else if (e.getActionCommand().equals("play")) { //play button pressed
            //game.handleInput(gameView.getPlacedWord(), game.getCurrPlayerIndex());
            if (game.handleInput(gameView.getPlacedWord(), game.getCurrPlayerIndex())) { //place() maybe takes in arraylist of tilesplaced
                if(game.getCurrPlayerIndex() == 0) {
                    gameView.updateInfo("Player 1 turn made.", "Make your turn now Player 2.");
                }
                else if(game.getCurrPlayerIndex() == 1 && (game.getPlayersSize()>2)){
                    gameView.updateInfo("Player 2 turn made.", "Make your turn now Player 3.");
                }
                else if(game.getCurrPlayerIndex() == 2 && (game.getPlayersSize()>3)){
                    gameView.updateInfo("Player 3 turn made.", "Make your turn now Player 4.");
                }
                else if(game.getCurrPlayerIndex() == 3){
                    gameView.updateInfo("Player 4 turn made.", "Make your turn now Player 1.");
                }else{
                    gameView.updateInfo("AI turn made.", "Make your turn now Player 1.");
                }

                gameView.updateScore((game.getCurrPlayerIndex()+1),game.getCurrPlayer().getScore());


                game.removeTiles(stringTilesPlaced(tilesPlaced), game.getCurrPlayerIndex()); //remove tiles of current player
                game.topUpRack(game.getCurrPlayer()); //topup the rack of current player
                //saveMoves = new SaveMoves(this);
                game.switchTurn();
                switchPlayerTiles(game.getCurrPlayer().getRack());
                tilesPlaced.removeAll(tilesPlaced);
                gameView.updateBoard();
                SaveMoves saveMoves = new  SaveMoves(this);
                saveMoves.save(placeCount);
                placeCount++;
                totalCount++;
            }
            else {
                resetBoard(); //print error message
                tilesPlaced.removeAll(tilesPlaced);
                gameView.updateInfo("Invalid word.", "Please pick a new word.");
            }

        }

        else if (e.getActionCommand().equals("skip")) { //skip button pressed
            game.switchTurn();
            gameView.updateBoard();
            switchPlayerTiles(game.getCurrPlayer().getRack());
            if(game.getCurrPlayerIndex()-1 == 0) {
                gameView.updateInfo("Player 1 turn skipped.", "Make your turn now Player 2.");
            }
            else if(game.getCurrPlayerIndex()-1 == 1  && (game.getPlayersSize()>2)){
                gameView.updateInfo("Player 2 turn skipped.", "Make your turn now Player 3.");
            }
            else if(game.getCurrPlayerIndex()-1 == 2  && (game.getPlayersSize()>3)){
                gameView.updateInfo("Player 3 turn skipped.", "Make your turn now Player 4.");
            }
            else if(game.getCurrPlayerIndex()-1 == 3){
                gameView.updateInfo("Player 4 turn skipped.", "Make your turn now Player 1.");
            }
            else{
                gameView.updateInfo("Player " + game.getPlayersSize() +" turn skipped.", "Make your turn now Player 1.");
            }
            SaveMoves saveMoves = new  SaveMoves(this);
            saveMoves.save(placeCount);
            placeCount++;
            totalCount++;
        }

        else if (e.getActionCommand().equals("undo")){
            if (placeCount > 0){
                String file = "states/scrabble_" + (placeCount - 2) + ".bin";
                try{
                    byte[] file_bytes = Files.readAllBytes(Paths.get(file));
                    Game new_game = (Game)Serialization.read_base64(new String(file_bytes));
                    game = new_game;
                    gameView.setBoard(game.getBoard());
                    gameView.updateBoard();
                    System.out.println(game.getPlayerRack());
                    gameView.updateRack(game.getPlayerRack());
                    gameView.refreshScore(game);
                    gameView.updateInfo("Turn is undone", "Player " + (game.getCurrPlayerIndex() + 1) +"'s turn");
                    //game.play();
                }
                catch (Exception ex){
                    ex.printStackTrace(System.err);
                }
                placeCount--;
            } else{
                gameView.updateInfo("Cannot undo anymore", "Player " + (game.getCurrPlayerIndex() + 1) +"'s turn");
            }
        }
        else if (e.getActionCommand().equals("redo")){
            if (placeCount  < totalCount){
                String file = "states/scrabble_" + (placeCount) + ".bin";
                try{
                    byte[] file_bytes = Files.readAllBytes(Paths.get(file));
                    Game new_game = (Game)Serialization.read_base64(new String(file_bytes));
                    game = new_game;
                    gameView.setBoard(game.getBoard());
                    gameView.updateBoard();
                    System.out.println(game.getPlayerRack());
                    gameView.updateRack(game.getPlayerRack());
                    gameView.refreshScore(game);
                    gameView.updateInfo("Turn is redone", "Player " + (game.getCurrPlayerIndex() + 1) +"'s turn");
                    //game.play();
                }
                catch (Exception ex){
                    ex.printStackTrace(System.err);
                }
                placeCount++;
            }else{
                gameView.updateInfo("Cannot redo anymore", "Player " + (game.getCurrPlayerIndex() + 1) +"'s turn");
            }
        }

        else if (e.getActionCommand().equals("reset")) {
            resetBoard();
            tilesPlaced.removeAll(tilesPlaced);
        }

        else if (e.getActionCommand().split(" ")[0].equals("pick")) {
            selectedTile = gameView.getButtonArray().get(Integer.parseInt(e.getActionCommand().split(" ")[1]));
        }

        else if (e.getActionCommand().equals("swap")) {
            ArrayList<String> dummyRack = new ArrayList<>(game.getPlayerRack());
            ArrayList<String> tilesToSwap = new ArrayList<>();
            String nextTile;
            do {
                nextTile = (String)JOptionPane.showInputDialog(null, "Select a tile to swap",
                        "Swap Tiles", JOptionPane.QUESTION_MESSAGE, null, dummyRack.toArray(), dummyRack.get(0));
                if (nextTile != null) {
                    tilesToSwap.add(nextTile);
                    dummyRack.remove(nextTile);
                }
            } while (nextTile != null && dummyRack.size() > 0);
            if (tilesToSwap.size() > 0) {
                game.swapTiles(tilesToSwap);
                gameView.updateRack(game.getPlayerRack());

                game.switchTurn();
                gameView.updateBoard();
                switchPlayerTiles(game.getCurrPlayer().getRack());
                gameView.updateInfo("Tiles swapped", "Make your turn now Player " + (game.getCurrPlayerIndex() + 1));
            }
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

    public boolean isTilePlaced(String letter) {
        for (JButton b: tilesPlaced) {
            if (b.getText().equals(letter)) {
                return true;
            }
        }
        return false;
    }

    public int getPlacedTile(String letter) {
        int index = 0;
        for (int i = 0; i < tilesPlaced.size(); i++) {
            if (tilesPlaced.get(i).getText().equals(letter)) {
                index = i;
            }
        }
        return index;
    }

    public Game getGame(){
        return game;
    }

    public void loadGame() {
        String file = GameView.askFile();
        if(file != null){
            try{
                byte[] file_bytes = Files.readAllBytes(Paths.get(file));
                Game new_game = (Game)Serialization.read_base64(new String(file_bytes));
                game = new_game;
                gameView.setBoard(game.getBoard());
                gameView.updateBoard();
                System.out.println(game.getPlayerRack());
                gameView.updateRack(game.getPlayerRack());
                gameView.refreshScore(game);
                gameView.updateInfo("Board is loaded", "Player " + (game.getCurrPlayerIndex() + 1) +"'s turn");
                //game.play();
            }
            catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
    }

}
