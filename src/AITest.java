import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AITest {
    private AI ai;
    private Board board;

    @Before
    public void setUp() {
        board = new Board();
        ai = new AI(1, board);
    }

    @Test
    public void testGetAIRack() {
        ai.addTile("A");
        ai.addTile("B");
        ai.addTile("C");
        ai.addTile("D");
        ai.addTile("E");
        ai.addTile("F");
        ai.addTile("G");
        assertEquals("ABCDEFG", ai.getAIRack());
    }

    @Test
    public void testWordCombo() {
        StringBuilder combos = new StringBuilder();
        char[] Rack = {'A', 'B', 'C', 'O'};
        ai.wordCombo(combos, 0, Rack);
        assertFalse(ai.getWordList().size() == 0);
    }

    @Test
    public void testWordValid() {
        ai.addTile("A");
        ai.addTile("B");
        ai.addTile("C");
        ai.addTile("D");
        ai.addTile("E");
        ai.addTile("F");
        ai.addTile("G");
        assertTrue(ai.wordValid("ABC"));
        assertFalse(ai.wordValid("AAAAAC"));
    }

    @Test
    public void findWord() {
        ai.addTile("A");
        ai.addTile("B");
        ai.addTile("C");
        ai.addTile("D");
        ai.addTile("E");
        ai.addTile("F");
        ai.addTile("G");
        ai.findWord();
        assertFalse(ai.getWordList().size() == 0);
    }

    @Test
    public void testGetWordList() {
        ArrayList<String> word = new ArrayList<>();
        word.add("k");
        ai.getWordList().add("k");
        assertEquals(word, ai.getWordList());
    }

    @Test
    public void removeTilesAI() {
        ai.addTile("A");
        ai.addTile("B");
        ai.addTile("C");
        assertEquals(3, ai.rackSize());

        ai.removeTilesAI("ABC");
        assertEquals(0, ai.rackSize());
    }

    @Test
    public void testEmptyWord() {
        ai.getWordList().add("A");
        ai.getWordList().add("A");
        ai.getWordList().add("A");
        ai.getWordList().add("A");
        assertEquals(4, ai.getWordList().size());
        ai.emptyWord();
        assertEquals(0, ai.getWordList().size());
    }

    @Test
    public void testNoWords() {
        assertTrue(ai.noWords());
        ai.getWordList().add("Hello");
        assertFalse(ai.noWords());
    }

    @Test
    public void testGetPossiblePlays() {
        ai.createFirstMove();
        assertEquals(0, ai.getPossiblePlays().size());
    }

    @Test
    public void testClearPossiblePlays() {
        ai.clearPossiblePlays();
        assertEquals(0, ai.getPossiblePlays().size());
    }

    @Test
    public void testCreatePlays() {
        board.place("MONKEY", "H8");
        ai.addTile("O");
        ai.addTile("A");
        ai.addTile("C");
        ai.addTile("L");
        ai.addTile("E");
        ai.addTile("Y");
        ai.createPlays();
        assertFalse(ai.getPossiblePlays().size() == 0);
    }

    @Test
    public void testIsEmptyPosition() {
        assertTrue(ai.isEmptyPosition(board.letterAt(5,5)));
    }

    @Test
    public void testCreateFirstMove() {
        ai.addTile("A");
        ai.addTile("B");
        ai.addTile("U");
        ai.addTile("D");
        ai.addTile("E");
        ai.addTile("F");
        ai.addTile("G");
        ai.findWord();
        ai.createFirstMove();
        assertFalse(ai.getPossiblePlays().size() == 0);
    }
}