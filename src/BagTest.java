/** BagTest Class - The class tests the Bag Class
 *
 * @author Monishkumar Sivakumar 101115115
 **/

import org.junit.*;
import static org.junit.Assert.*;

public class BagTest {
    private Bag bag;

    @Before
    public void setUp() {
        bag = new Bag();
    }

    @Test
    public void testBag() {
        assertTrue(bag.drawTile().matches("[a-zA-Z]+") == true);
    }

    @Test
    public void failBag() {
        String letter = "11";
        if(letter.matches("[a-zA-Z]+") == false) {
            fail("The string returned is not a letter");
        }
    }

    /**@Test
    public void tooManyLetters() {
        String letter = "as";
        if(letter.length() > 1) {
            fail("The string has more than one letter");
        }
    }**/


}
