/** WordTest Class - The class tests the Word Class
 *
 * @author Monishkumar Sivakumar 101115115
 **/

import org.junit.*;
import static org.junit.Assert.*;

public class WordTest {
    private Word word;

    @Before
    public void setUp(){
        word = new Word();
    }
    @Test
    public void testWord(){
        assertEquals(4, word.score("test"));
    }

    @Test
    public void testEmpty(){
        assertEquals(0, word.score(""));
    }

    /**@Test
    public void failingTest(){

        Integer testW = 11;
        if (testW.getClass().equals(String.class) != true){
            fail("The input is an integer");
        }
    }**/

}
