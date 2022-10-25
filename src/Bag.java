import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Bag class implementing a bag of letters for the Scrabble Game
 *
 * @author Christina Dang 101141609
 */

public class Bag {
    private ArrayList<String> letterBag = new ArrayList<String>(
            Arrays.asList("E","E","E","E","E","E","E","E","E","E","E","E","X",
                    "A","A","A","A","A","A","A","A","A","K","I","I","I","I","I","I","I","I","I","J",
                    "O","O","O","O","O","O","O","O","N","N","N","N","N","N","R","R","R","R","R","R",
                    "T","T","T","T","T","T","L","L","L","L","S","S","S","S","U","U","U","U","Q",
                    "D","D","D","D","G","G","G","Z","B","B","C","C","M","M","P","P","F","F","H","H",
                    "V","V","W","W","Y","Y"));

    /**
     * The constructor of the Bag class
     */
    public Bag() {}

    /**
     * A method to draw letter tiles
     * @return letter string, the drawn leter tile
     */
    public String drawTile(){
            int randomValue = (int)Math.floor(Math.random()*letterBag.size());
            String letter = letterBag.get(randomValue);
            letterBag.remove(randomValue);
            return letter;
    }

    /**
     * A method to return bag size
     * @return size int, the size of the bag array
     */
    public int getSize(){
        return letterBag.size();
    }


    /**
     * A method to return letter tiles to the bag
     * @param letter String, the letter
     */
    public void returnTile(String letter){
        letterBag.add(letter);
    }

}