import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The game frame of the Scrabble game.
 *
 * @author Christina Dang 101141609
 */


public class GameView extends JFrame{
    private JPanel board,rack1, rack2,buttons, texts, scores;
    private JButton playButton,skipButton,quitButton,swapButton;
    private ArrayList<JButton> buttonLetters1;
    private ArrayList<JButton> buttonLetters2;
    private JLabel label1, label2, label3, label4, score1, score2;
    private ScrabbleController controller;
    private Board board1;

    /**
     * The constructor of the GameView class
     */

    public GameView(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
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

        board = new JPanel();
        board.setBorder(BorderFactory.createLineBorder(Color.black));
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.ipady = 500;
        c.ipadx = 500;
        c.gridx = 0;
        c.gridy = 0;
        board.add(new BoardView(board1,controller));
        pane.add(board,c);



        //letter rack player 1
        rack1 = new JPanel();
        rack1.setLayout(new GridLayout(1,7));
        buttonLetters1 = new ArrayList<JButton>();
        for(int i = 0; i<7; i++){
            buttonLetters1.add(new JButton(""));

            String intText = Integer.toString(i);
            buttonLetters1.get(i).setActionCommand(intText);

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
        quitButton = new JButton("Quit");
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(controller);
        buttons.add(quitButton);

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
        scores.setLayout(new GridLayout(2,2));

        label3 = new JLabel("Player 1");
        label4 = new JLabel("Player 2");
        scores.add(label3);
        scores.add(label4);
        score1 = new JLabel("0");
        score2 = new JLabel("0");
        scores.add(score1);
        scores.add(score2);
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


    public static void main(String[] args) {
        new GameView();
    }
}




