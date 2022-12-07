/** PlayerTest Class - The class tests the Player Class
 *
 * @author Monishkumar Sivakumar 101115115
 **/

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp(){
        player = new Player("Player1");
    }

    @Test
    public void getName(){
        String name = player.getName();
        assertTrue(name != null);
    }

    @Test
    public void getScore(){
        Integer score = player.getScore();
        assertTrue(score != null);
    }

    @Test
    public void  getRack(){
        ArrayList<String> rack = player.getRack();
        assertTrue(rack != null);
    }

    @Test
    public void addPoints(){
        Integer before = player.getScore();
        player.addPoints(11);
        Integer after = player.getScore();
        assertTrue(before != after);
    }

    @Test
    public void addTile(){
        String tile = "a";
        Integer before = 0;
        before = player.rackSize();
        player.addTile(tile);
        Integer after = 0;
        after = player.rackSize();
        assertTrue(before != after);
    }

    @Test
    public void removeTile(){
        String tile = "a";
        Integer before = 0;
        player.addTile(tile);
        before = player.rackSize();
        player.removeTile(tile);
        Integer after = 0;
        after = player.rackSize();
        assertTrue(before != after);
    }

    @Test
    public void rackSize(){
        Integer size = player.rackSize();
        assertTrue(size != null);
    }

   /** @Test
    public void notNumber(){
        String word = "11";
        if (word.getClass().equals(String.class) == true){
            fail("The score is not a Integer");
        }
    } **/

    /**@Test
    public void bigNumber() {
        Integer score = 111;

        if (score >= 100){
            fail("The score that is being added is too big of a number");
        } else {
            player.addPoints(score);
        }

    }**/
}
