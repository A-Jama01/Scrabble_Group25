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
    private ArrayList<String> letterRack = new ArrayList<String>();

    /**
     * The constructor of the Bag class
     */
    public Bag() {}

    /**
     * A method to get the drawn letter rack
     * @return letterRack ArrayList<String>, the rack of letter tiles
     */
    public ArrayList<String> getLetterRack(){ return letterRack;}

    /**
     * A method to draw letter tiles
     */
    public void drawTiles(){
        for(int i=0;i<7;i++){
            int randomValue = (int)Math.floor(Math.random()*letterBag.size() +1);
            letterRack.add(letterBag.get(randomValue));
            letterBag.remove(randomValue);
        }
    }

    /**
     * A method to return letter tiles to the bag
     * @param letter String, the letter
     */
    public void returnTile(String letter){
        letterBag.add(letter);
    }

}