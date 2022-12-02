/** BoardTest Class - The class tests the Board Class
 *
 * @author Monishkumar Sivakumar 101115115
 **/

import com.sun.source.tree.AssertTree;
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
    public void testMultipleCombinations() {
        ArrayList<String> combinations;
        String word = "HELLO";
        String pos = "1A";
        assertTrue(board.place(word, pos));
        String secondWord = "SCRABBLE";
        String secondPos = "2A";
        combinations = board.getCombinationsWith(secondWord, secondPos);
        assertTrue(combinations.contains("Hs"));
        assertTrue(combinations.contains("Ec"));
        assertTrue(combinations.contains("Lr"));
        assertTrue(combinations.contains("La"));
        assertTrue(combinations.contains("Ob"));
        assertTrue(combinations.contains("scrabble"));
        assertEquals(6, combinations.size());
    }

    @Test
    public void testThreeWordsCombining() {
        ArrayList<String> combinations;
        assertTrue(board.place("HELLO", "H8"));
        assertTrue(board.place("WORLD", "J8"));
        combinations = board.getCombinationsWith("DO", "I10");
        assertEquals(3, combinations.size());
        assertTrue(combinations.contains("LdR"));
        assertTrue(combinations.contains("LoL"));
        assertTrue(combinations.contains("do"));
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
