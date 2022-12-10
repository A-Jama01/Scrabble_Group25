import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The game frame of the Scrabble game.
 *
 * @author Christina Dang 101141609
 */


public class GameView extends JFrame{
    private int playerCount, aiCount;
    private BoardView board;
    private JPanel rack1, rack2,buttons, texts, scores;
    private JButton playButton,skipButton,quitButton,swapButton, saveButton, loadButton, undoButton, redoButton;
    private ArrayList<JButton> buttonLetters1;
    private ArrayList<JButton> buttonLetters2;
    private JLabel label1, label2, label3, label4, label5, label6, score1, score2, score3, score4;
    private ArrayList<JLabel> playerLabels, playerScores;
    private ScrabbleController controller;
    private static GameView self;
    private Game gameClass;


    /**
     * The constructor of the GameView class
     */

    public GameView(Game game){
        gameClass = game;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        game.addGameView(this);
        controller = new ScrabbleController(game, this);
        board = new BoardView(game.getBoard(), controller);
        addComponents(this.getContentPane());
        this.setVisible(true);
    }

    /**
     * A method to set up the components for the GameView frame
     * @param pane Container,the frame
     **/

    public void addComponents(Container pane){


        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //change for boardview when done
        //scrabble board
        //controller = new ScrabbleController(new Game(),this);

        board.setBorder(BorderFactory.createLineBorder(Color.black));
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(board,c);



        //letter rack player 1
        rack1 = new JPanel();
        rack1.setLayout(new GridLayout(1,7));
        buttonLetters1 = new ArrayList<JButton>();
        for(int i = 0; i<7; i++){
            buttonLetters1.add(new JButton(""));

            String intText = Integer.toString(i);
            buttonLetters1.get(i).setActionCommand("pick " + intText);

            buttonLetters1.get(i).addActionListener(controller);
            rack1.add(buttonLetters1.get(i));

        }

        c.fill = GridBagConstraints.CENTER;
        c.weighty = 0.5;
        c.ipady = 10;
        c.ipadx = 10;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(rack1, c);

       /*letter rack player 2
       rack2 = new JPanel();
       rack2.setLayout(new GridLayout(1,8));
       buttonLetters2 = new ArrayList<JButton>();
       for(int i = 0; i<7; i++){
           buttonLetters2.add(new JButton(""));
           //buttonLetters2.get(i).addActionListener(controller);
           rack2.add(buttonLetters2.get(i));
       }
       rack2.add(new JLabel("  Player 2 Rack"));
       c.gridy = 2;
       pane.add(rack2, c);
       */

        //action buttons
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,7));

        playButton = new JButton("Play");
        playButton.setActionCommand("play");
        playButton.addActionListener(controller);

        buttons.add(playButton);
        skipButton = new JButton("Skip");
        skipButton.setActionCommand("skip");
        skipButton.addActionListener(controller);
        buttons.add(skipButton);
        swapButton = new JButton("Swap");
        swapButton.setActionCommand("swap");
        swapButton.addActionListener(controller);
        buttons.add(swapButton);
        quitButton = new JButton("Reset");
        quitButton.setActionCommand("reset");
        quitButton.addActionListener(controller);
        buttons.add(quitButton);
        saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new Save(controller));
        buttons.add(saveButton);
        loadButton = new JButton("Load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(new Load(controller));
        buttons.add(loadButton);
        undoButton = new JButton("Undo");
        undoButton.setActionCommand("undo");
        undoButton.addActionListener(controller);
        buttons.add(undoButton);
        redoButton = new JButton("Redo");
        redoButton.setActionCommand("redo");
        redoButton.addActionListener(controller);
        buttons.add(redoButton);

        c.gridy = 3;
        pane.add(buttons,c);

        //information text
        texts = new JPanel();
        texts.setLayout(new GridLayout(1,2));

        label1 = new JLabel("Welcome to Scrabble!");
        label2 = new JLabel("Player 1. Press a letter from your rack and a location on the board.");
        texts.add(label1);
        texts.add(label2);

        c.gridy = 4;
        pane.add(texts,c);

        //player scores
        scores = new JPanel();
        scores.setLayout(new GridLayout(2,4));

        playerLabels = new ArrayList<JLabel>();
        playerCount = gameClass.getPlayersSize();
        aiCount = gameClass.getAISize();

        label3 = new JLabel("");
        label4 = new JLabel("");
        label5 = new JLabel("");
        label6 = new JLabel("");
        playerLabels.add(label3);
        playerLabels.add(label4);
        playerLabels.add(label5);
        playerLabels.add(label6);

        for(int i = 0; i<playerCount; i++){
            playerLabels.get(i).setText("Player " + (i+1));
            scores.add(playerLabels.get(i));
        }
        for(int i = 0; i<aiCount; i++){
            playerLabels.get(i+playerCount).setText("AI " + (i+1));
            scores.add(playerLabels.get(i+playerCount));
        }

        score1 = new JLabel("0");
        score2 = new JLabel("0");
        score3 = new JLabel("0");
        score4 = new JLabel("0");
        playerScores = new ArrayList<JLabel>();
        playerScores.add(score1);
        playerScores.add(score2);
        playerScores.add(score3);
        playerScores.add(score4);

        for(int i = 0; i<playerCount+aiCount; i++){
            scores.add(playerScores.get(i));
        }
        c.gridy = 0;
        c.gridx = 1;
        pane.add(scores,c);

    }

    /**
     * A method to update the rack buttons to display the letters
     * @param letters ArrayList<String>, the array of letters
     */
    public void updateRack(ArrayList<String> letters){
        for (int i = 0; i < 7; i++) {
            buttonLetters1.get(i).setText(letters.get(i));
        }
    }

    /**
     * A method to update the string texts on the view
     * @param s1 String
     * @param s2 String
     */
    public void updateInfo(String s1, String s2){
        label1.setText(s1);
        label2.setText(s2);
    }

    /**
     * A method to update the scores on the view
     * @param playerNum int, the player of which score to update
     * @param score int, the score
     */

    public void updateScore(int playerNum, int score){
        String scoreText = Integer.toString(score);
        if(playerNum == 1){
            score1.setText(scoreText);
        }
        if(playerNum == 2){
            score2.setText(scoreText);
        }
        if(playerNum == 3){
            score3.setText(scoreText);
        }
        if(playerNum == 4){
            score4.setText(scoreText);
        }
    }

    public void updateScoreAI(int aiNum,int score){
        String scoreText = Integer.toString(score);
        playerScores.get(playerCount+aiNum).setText(scoreText);
    }

    /**
     * A method to return the letter selected
     * @param index int, index of button
     * @return String, letter
     */
    public String getLetter(int index){
        return buttonLetters1.get(index).getText();
    }

    /**
     * A method to return the button array
     * @return buttonLetters1 ArrayList<JButton>
     */
    public ArrayList<JButton> getButtonArray(){
        return buttonLetters1;
    }

    public String placeTile(int column, int row, String letter) {
        return board.setFloatingTile(column, row, letter);
    }

    public String getPlacedWord() {
        return board.getFloatingWord();
    }

    public void updateBoard() {
        board.refresh();
    }

    public static void main(String[] args) {
        Game game = new Game();
        new GameView(game);
        game.play();
    }

    public static String askFile() { return loadFile.getInstance().show_file_dialog(self, "."); }

    public void setBoard(Board board) { this.board.setBoard(board); }

    public void refreshScore(Game newGameClass){
        gameClass = newGameClass;
        scores.removeAll();

        playerCount = gameClass.getPlayersSize();
        aiCount = gameClass.getAISize();

        scores = new JPanel();
        scores.setLayout(new GridLayout(2,4));

        playerLabels = new ArrayList<JLabel>();

        label3 = new JLabel("");
        label4 = new JLabel("");
        label5 = new JLabel("");
        label6 = new JLabel("");
        playerLabels.add(label3);
        playerLabels.add(label4);
        playerLabels.add(label5);
        playerLabels.add(label6);

        for(int i = 0; i<playerCount; i++){
            playerLabels.get(i).setText("Player " + (i+1));
            scores.add(playerLabels.get(i));
        }
        for(int i = 0; i<aiCount; i++){
            playerLabels.get(i+playerCount).setText("AI " + (i+1));
            scores.add(playerLabels.get(i+playerCount));
        }

        score1 = new JLabel("0");
        score2 = new JLabel("0");
        score3 = new JLabel("0");
        score4 = new JLabel("0");
        playerScores = new ArrayList<JLabel>();
        playerScores.add(score1);
        playerScores.add(score2);
        playerScores.add(score3);
        playerScores.add(score4);

        for(int i = 0; i<playerCount+aiCount; i++){
            scores.add(playerScores.get(i));
        }

        for(int i = 0; i<playerCount; i++){
            playerScores.get(i).setText(Integer.toString(gameClass.getPlayers().get(i).getScore()));
        }
        for(int i = 0; i<aiCount; i++){
            playerScores.get(i+playerCount).setText(Integer.toString(gameClass.getAIs().get(i).getScore()));
        }

        this.getContentPane().add(scores);
    }

}




