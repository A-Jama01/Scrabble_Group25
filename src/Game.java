import java.util.*;


/**
 * Main class of Scrabble game. Initialize this class and call the play
 * method to play game.
 *
 * This class creates and initializes all other classes. It takes in
 * user inputs from parser, checks word validity using the
 * dictionary class, places word on the board if it's a legal position,
 * calculates the score using the word Class and adds the score to the
 * player. The player draws tiles from the bag class after each play.
 *
 * @author Abdurahman Jama 1011626333
 */
public class Game {
    private Parser parser;
    private Dictionary dict;
    private Bag bag;
    private Board board;
    private Word word;
    private ArrayList<Player> players;
    private boolean gameOver;

    /**
     * Create the game and initialise all other classes needed to play the game.
     */
    public Game() {
        parser = new Parser();
        dict = new Dictionary();
        bag = new Bag();
        board = new Board();
        word = new Word();
        players = new ArrayList<Player>();
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
     * Starts the game. Gameplay loops until game is over.
     */
    public void play(){
        int currPlayerIndex = 0;
        while (!gameOver) { //need to know implementation of parser class and board
            System.out.println(board); //Print Board for each player's turn
            playerTurn(currPlayerIndex);
            topUpRack(players.get(currPlayerIndex));
            checkGameState(currPlayerIndex);
            currPlayerIndex ^= 1; //toggle between player1 and player2
        }
        endMsg();
    }

    /**
     * Top up player's rack to 7. As long as there are tiles left in the bag.
     *
     * @param p Player in Scrabble game
     */
    public void topUpRack(Player p) {
        while ((p.rackSize() < 7) && (bag.getSize() != 0)) {
            p.addTile(bag.drawTile()); //assuming bag has method that will remove tile and return the tile removed
        }
    }

    /**
     * Loops player's turn until its over.
     *
     * @param index The index of the player whose turn it is.
     */
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

    /**
     * Handles the user's input. Passes player's turn if user writes pass, ends game
     * if user write quit, places users word on the board if it's a valid word that
     * can be legally placed and, returns an error message if user input couldn't be
     * processed.
     *
     * @param userInput The string inputted by the user
     * @param index The index of the current player
     * @return True if user inputs pass, quit and a valid & legal word
     */
    public boolean handleInput(String userInput, int index) {
        if (userInput.equals("pass")) {//
            return true;
        }
        else if (userInput.equals("quit")) {
            gameOver = true;
            return true;
        }
        else if (tilesInRack(userInput, index)) {
            if (legalPlacement(userInput)) {
                players.get(index).addPoints(tallyPoints(userInput)); //adds points to curr player
                removeTiles(userInput, index);
                return true;
            }
        }

        System.out.println("Invalid input received try again");
        return false;
    }

    /**
     * Get the word's position from user's input
     *
     * @param userInput The string inputted from the user
     * @return String representing a word's position
     */
    public String getPos(String userInput) {
        String position = userInput.split(" ")[0];
        return position;
    }

    /**
     * Get the word from user's input
     *
     * @param userInput The string inputted from the user
     * @return String representing a word
     */
    public String getSecondWord(String userInput) {
        String secondWord = userInput.split(" ")[1];
        return secondWord;
    }

    /**
     * Checks if tiles to form word are in the player's rack
     *
     * @param userInput String inputted by the user
     * @param index Index of the current player
     * @return True if tiles are in rack and false otherwise
     */
    public boolean tilesInRack(String userInput, int index) {
        ArrayList<String> tilesNeeded = tilesNeeded(userInput, index);
        ArrayList<String> playerRack = players.get(index).getRack();

        for (String s: tilesNeeded) {
            if (!(playerRack.contains(s))) { //check if tiles needed are in player's rack
                return false;
            }
            playerRack.remove(s);
        }
        return true;
    }

    /**
     * Get the tiles needed to be placed to make a word
     *
     * @param userInput String inputted by the user
     * @param index Current player's index
     * @return ArrayList<String> representing tiles needed
     */
    public ArrayList<String> tilesNeeded(String userInput, int index) {
        String stringTiles = board.checkLetters(getSecondWord(userInput), getPos(userInput));
        String listTiles[] = stringTiles.split("");
        ArrayList<String> tilesNeeded = new ArrayList<>();
        ArrayList<String> playerRack = players.get(index).getRack();

        for(String s: listTiles) { //add lowerCase letters to tilesNeeded
            if (s.equals(s.toLowerCase())) {
                tilesNeeded.add(s.toUpperCase());//Tiles are in uppercase
            }
        }
        return tilesNeeded;
    }

    /**
     * Get a list of the words created if a word is placed on the board
     *
     * @param userInput The string inputted from the user
     * @return ArrayList<String> of words created
     */
    public ArrayList<String> wordCombos(String userInput) {
        return board.getCombinationsWith(getSecondWord(userInput), getPos(userInput));
    }

    /**
     * Check if user's input contains valid words and can be legally placed on the board
     *
     * @param userInput The string inputted by the user
     * @return True if userInput contains a word that is in dictionary and can be placed on board
     */
    public boolean legalPlacement(String userInput) {
        ArrayList<String> wordCombos = wordCombos(userInput);
        for (String s: wordCombos) {
            if (!dict.check(s)) { //return false if a word combination is false
                return false;
            }
        }
        if (!board.place(getSecondWord(userInput), getPos(userInput))) { //return false if tile couldn't be placed
            return false;
        }
        return true;
    }

    /**
     * Remove the tiles used to make word from the players rack
     *
     * @param userInput String inputted by the user
     * @param index Index of the current player
     */
    public void removeTiles(String userInput, int index) {
        ArrayList<String> tilesNeeded = tilesNeeded(userInput, index);

        for(String s: tilesNeeded) {
            players.get(index).removeTile(s);
        }
    }

    /**
     * Tally total points scored from placing word
     *
     * @param userInput String inputted from user
     * @return  Int representing total points scored
     */
    public int tallyPoints(String userInput) {
        ArrayList<String> wordCombos = wordCombos(userInput);
        int score = 0;
        for (String s: wordCombos) {
            score += word.score(s);
        }
        return score;
    }

    /**
     * Checks the game state if the player runs out of tiles the game ends.
     *
     * @param index Current player's index
     */
    public void checkGameState(int index) {
        if (players.get(index).rackSize() == 0) {
            gameOver = true;
        }
    }

    /**
     * Ending message when game is over
     */
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

    /**
     * Gets the index of the player with the highest score or a negative in case of tie
     * @return  Index of winner
     */
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

