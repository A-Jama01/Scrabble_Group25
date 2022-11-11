/** BoardTest Class - The class tests the Board Class
 *
 * @author Monishkumar Sivakumar 101115115
 **/

import org.junit.*;
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
    public void failPlacement(){
        String word = "test";
        String place = "AA";

        try {
            board.place(word,place);
            fail("One of the inputs of word or place is incorrect");
        }catch (Exception e){
            assertTrue(board.place(word,place) == true);
        }
    }
}
