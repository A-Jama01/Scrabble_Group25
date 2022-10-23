import java.util.*;


/**
 * Main class of Scrabble game. Initialize this class to play the game.
 *
 * @author Abdurahman Jama 1011626333
 */
public class Game {
    private Parser parser;
    private ScrabbleDictionary dict;
    private Bag bag;
    private Board board;
    private Word word;
    private ArrayList<Player> players;
    private boolean gameOver;

    public Game() {
        parser = new Parser();
        dict = new ScrabbleDictionary();
        bag = new Bag();
        board = new Board();
        word = new Word();
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        gameOver = false;

        //add players
        players.add(p1);
        players.add(p2);

        //initialize players rack
        for (Player p: players) {
            topUpRack(p);
        }
    }

    /**
     * Starts to play the game
     */
    public void play(){
        board.printBoard();
        int currPlayerIndex = 0;
        while (!gameOver) { //need to know implementation of parser class and board
            playerTurn(currPlayerIndex);
            checkGameState(currPlayerIndex);
            currPlayerIndex ^= 1; //toggle between player1 and player2
        }
        endMsg();
    }

    /**
     * Top up player's rack
     */
    private void topUpRack(Player p) {
        while ((p.rackSize() < 7) && (bag.size() == 0)) {
            p.addTiles(bag.getTile()); //assuming bag has method that will remove tile and return the tile removed
        }
    }

    public void playerTurn(int index) {
        boolean turnDone = false;
        while (!turnDone) {
            String playerRack = players.get(index).getName() + "'s Turn: "; //Print curr player's rack
            playerRack += players.get(index).stringRack();
            System.out.println(playerRack);

            String userInput = parser.getInput();//call parser class to take in user input
            turnDone = handleInput(userInput, index);
        }
    }

    public boolean handleInput(String userInput, int index) {
        if (userInput.equals("pass")) {//
            return true;
        }
        else if (userInput.equals("quit")) {
            gameOver = true;
            return true;
        }
        else if (dict.check(getSecondWord(userInput))) { //check if second word is valid
            board.place(userInput);
            players.get(index).addPoints(word.score(userInput)); //adds points to curr player
            return true;
        }
        System.out.println("Invalid input received try again\n");
        return false;
    }

    public String getSecondWord(String userInput) {
        String secondWord = userInput.split(" ")[1];
        return secondWord;
    }

    public void checkGameState(int index) {
        if (players.get(index).rackSize() == 0) {
            gameOver = true;
        }
    }

    public void endMsg() {
        System.out.println("Game Over\n");
        for (Player p: players) {
            String score = p.getName() + " scored: " + p.getScore() + "\n";
            System.out.println(score);
        }
        if (winnerIndex() == -1) {
            System.out.println("Game was a Tie\n");
        }
        else {
            String winnerMsg = players.get(winnerIndex()).getName() + " Wins\n";
            System.out.println(winnerMsg);
        }
    }

    public int winnerIndex() {
        if (players.get(0).getScore() > players.get(1).getScore()) {
            return 0;
        }
        else if (players.get(0).getScore() == players.get(1).getScore()) {
            return -1;
        }
        return 1;
    }
    public static void main(String args) {
        Game game = new Game();
        game.play();
    }

}

