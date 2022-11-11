/** PlayerTest Class - The class tests the Player Class
 *
 * @author Monishkumar Sivakumar 101115115
 **/

import org.junit.*;
import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp(){
        player = new Player("Player1");
    }

    @Test
    public void addScore(){
        Integer before = player.getScore();
        player.addPoints(11);
        Integer after = player.getScore();
        assertTrue(before != after);
    }

    @Test
    public void notNumber(){
        String word = "11";
        if (word.getClass().equals(String.class) == true){
            fail("The score is not a Integer");
        }
    }

    @Test
    public void bigNumber() {
        Integer score = 111;

        if (score >= 100){
            fail("The score that is being added is too big of a number");
        } else {
            player.addPoints(score);
        }

    }
}
