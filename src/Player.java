import java.util.*;
import java.io.Serializable;

/**
 * Player of the scrabble game. Each player stores its score,
 * name, and rack of tiles.
 *
 * @author Abdurahman Jama 101162633
 */
public class Player implements Serializable{
    private String name;
    private int score;
    private ArrayList<String> rack;
    private static final long serialVersionUID = 1;
    private int ai;

    /**
     * Create a player
     *
     * @param name Player's name
     */
    public Player(String name, int ai) {
        this.name = name;
        this.score = 0;
        this.rack = new ArrayList<>();
        this.ai = ai;
    }

    /**
     * Getter for player's name
     *
     * @return String representing player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for player's ai
     *
     * @return int representing whether a player is an ai or not
     */
    public int getAI() {
        return this.ai;
    }

    /**
     * Getter for player's score
     *
     * @return Player's score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Getter for player's tile Rack
     *
     * @return Player's rack
     */
    public ArrayList<String> getRack() {
        return this.rack;
    }

    /**
     * Add points to player score
     */
    public void addPoints(int points) {
        this.score += points;
    }

    /**
     * Add tile to player's rack
     *
     * @param tile  Tile to be added
     */
    public void addTile(String tile) {
        this.rack.add(tile);
    }

    /**
     * Remove tile from player's rack
     *
     * @param tile Tile to be removed
     */
    public void removeTile(String tile) {
        this.rack.remove(tile);
    }

    /**
     * Return the number of tiles in the players rack
     *
     * @return int representing rack size
     */
    public int rackSize() {
        return this.rack.size();
    }

    /**
     * String representation of Player's current rack
     *
     * @return String representation of rack
     */
    public String stringRack() {
        String stringRack = "[";
        for (String s: rack) {
            stringRack += " " + s;
        }
        stringRack += " ]\n";
        return stringRack;
    }

}
