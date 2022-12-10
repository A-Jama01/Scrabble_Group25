/** DictionaryTest Class - The class tests the Dictionary Class
 *
 * @author Monishkumar Sivakumar 101115115
 **/

import org.junit.*;
import static org.junit.Assert.*;

public class DictionaryTest {
    private Dictionary dict;

    @Before
    public void setUp(){
        dict = new Dictionary();
    }
    @Test
    public void testDict(){
        assertTrue(dict.check("apple") == true);
    }

    @Test
    public void testEmpty(){
        assertTrue(dict.check("") == false);
    }

    @Test
    public void testWrongWord(){
        assertTrue(dict.check("asdfg") == false);
    }

    @Test
    public void testCheckSetCorrectWord() {assertTrue(dict.checkSet("hello"));}

    @Test
    public void testCheckSetIncorrectWord() {assertFalse(dict.checkSet("KLJDFKL:JFDS"));}

    /**@Test
    public void failingTest(){
        Integer word = 11;
        if (word.getClass().equals(String.class) != true){
            fail("The input is an integer");
        }
    }**/
}
