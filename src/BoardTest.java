/** BoardTest Class - The class tests the Board Class
 *
 * @author Monishkumar Sivakumar 101115115
 **/

import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BoardTest {
    private Board board;

    @Before
    public void setUp(){
        board = new Board();
    }

    @Test
    public void testPlacement(){
        String word = "test";
        String place = "A5";

        assertTrue(board.place(word,place) == true);
    }

    @Test
    public void defaultBoardConfiguration(){
        HashMap<String[], String> config = new HashMap<>(4);
        config = board.defaultBoardConfiguration();
        assertTrue(config != null);
    }

    @Test
    public void getCombinationsWith(){
        ArrayList<String> combinations = new ArrayList<>();
        String word = "hey";
        String pos = "H8";
        combinations = board.getCombinationsWith(word, pos);
        assertTrue(combinations != null);
    }

    @Test
    public void checkLetters(){
        String word = "hey";
        String pos = "H8";
        String val = board.checkLetters(word, pos);
        assertTrue(val != null);
    }
    @Test
    public void wordCrossesCentre(){
        String word = "hey";
        String pos = "H8";
        assertTrue(board.wordCrossesCentre(word, pos));
    }

    @Test
    public void wordIsAttached(){
        String word = "hey";
        String pos = "H8";
        assertTrue(board.wordIsAttached(word, pos));
    }

    @Test
    public void letterAt(){
        int col = 1;
        int row = 1;
        String val = board.letterAt(col, row);
        assertTrue(val != null);
    }

    @Test
    public void getWordMultiplers(){
        String word = "hey";
        String pos = "H8";
        String[] multipliers = new String[word.length()];
        multipliers = board.getWordMultipliers(word, pos);
        assertTrue(multipliers != null);
    }

    /**@Test
    public void failPlacement(){
        String word = "test";
        String place = "AA";

        try {
            board.place(word,place);
            fail("One of the inputs of word or place is incorrect");
        }catch (Exception e){
            assertTrue(board.place(word,place) == true);
        }
    }**/
}
