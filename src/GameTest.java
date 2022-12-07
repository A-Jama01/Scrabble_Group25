/** GameTest Class - The class tests the Game Class
 *
 * @author Monishkumar Sivakumar 101115115
 **/

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;

    @Before
    public void setUp(){
        game = new Game();
    }

    @Test
    public void testPlaceWord(){
        String word = "H8 HEY"; //test if the word is in dictionary
        assertTrue(game.place(word));
        String word2 = "H8 nwr"; //test if the word is not in dictionary
        assertFalse(game.place(word2));

    }

    @Test
    public void topUpRack(){
        Player p1 = new Player("one");
        Integer before = p1.rackSize();
        game.topUpRack(p1);
        Integer after = p1.rackSize();
        assertTrue(before != after);
    }

    @Test
    public void getCurrPlayer() {
        Player player = game.getCurrPlayer();
        assertTrue(player != null);

    }

    @Test
    public void getCurrPlayerIndex() {
        Integer curr = game.getCurrPlayerIndex();
        assertTrue(curr != null);
    }

    @Test
    public void handleInput(){
        Integer input = 1;
        String pass = "pass";
        assertTrue(game.handleInput(pass, input));
        String quit = "quit";
        assertTrue(game.handleInput(quit, input));
        String noInput = "";
        assertFalse(game.handleInput(noInput, input));
    }

    @Test
    public void getPos(){
        String input = "H8 HEY";
        String after = game.getPos(input);
        assertTrue(input != after);
    }

    @Test
    public void getSecondWord(){
        String input = "H8 HEY";
        String val = game.getSecondWord(input);
        assertTrue(val != null);
    }

    @Test
    public void tilesInRack(){
        ArrayList<String> tile = new ArrayList<String>();
        tile.add("a");
        tile.add("b");
        Integer index = 1;
        assertFalse(game.tilesInRack(tile, index));
    }

    @Test
    public void tilesNeeded(){
        String input = "H8 HEY";
        Integer index = 1;
        ArrayList<String> combo = new ArrayList<String>();
        combo = game.tilesNeeded(input, index);
        assertTrue(combo != null);
    }

    @Test
    public void wordCombos(){
        String input = "H8 HEY";
        ArrayList<String> combo = new ArrayList<String>();
        combo = game.wordCombos(input);
        assertTrue(combo != null);
    }

    @Test
    public void legalPlacement(){
        String input = "H8 HEY";
        assertFalse(game.legalPlacement(input));
    }

    @Test
    public void tallyPoints(){
        String input = "H8 HEY";
        int score = 0;
        score = game.tallyPoints(input);
        assertTrue(score != 0);
    }


    @Test
    public void winnerIndex(){
        Integer score = game.winnerIndex();
        assertTrue(score == -1);

    }

    @Test
    public void getBoard(){
        Board board = game.getBoard();
        assertTrue(board != null);
    }
}
